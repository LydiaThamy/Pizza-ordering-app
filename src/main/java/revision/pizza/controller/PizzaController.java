package revision.pizza.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import revision.pizza.model.Order;
import revision.pizza.model.Pizza;
import revision.pizza.service.PizzaService;

@Controller
public class PizzaController {
    
    @Autowired
    private PizzaService service;
    
    @GetMapping(path = "/")
    public String showLanding(Model m, HttpSession session){

        // clear session
        session.invalidate();

        // add pizza object for view
        m.addAttribute("pizza", new Pizza());
        
        return "index";
    }

    @PostMapping(path = "/pizza", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String nextPage(@ModelAttribute("pizza") @Valid Pizza pizza, BindingResult result, Model m, HttpSession session) {

        // test
        System.out.println(pizza);
        System.out.println(result.toString());

        // validation errors - null, quanity invalid
        if (result.hasErrors()) {
            // m.addAttribute("pizza", new Pizza());
            return "index";
        }

        // validate errors - name & size invalid
        // call service to get list<ObjectError> of other errors
        List<ObjectError> globalErrors = new ArrayList<>(service.extraErrors(pizza));
        if (!globalErrors.isEmpty()) {
            globalErrors.stream()
                .forEach(e -> result.addError(e));
            return "index";
        }

        // add pizza object to session
        session.setAttribute("pizza", pizza);

        // add order object for view
        m.addAttribute("order", new Order());

        return "delivery";
    }

    @PostMapping(path = "/pizza/order", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String placeOrder(@ModelAttribute("order") @Valid Order order, BindingResult result, Model m, HttpSession session){

        // validate order
        if (result.hasErrors()) {
            return "delivery";
        }
        
        // generate random ID
        order.setId();
        
        // calculate order cost & return order object
        Pizza pizza = (Pizza) session.getAttribute("pizza");
        
        System.out.println(pizza);

        if (pizza == null) {
            m.addAttribute("timeout", "Your session has timed out");
            return showLanding(m, session);
        }

        double cost = service.calculateCost(pizza, order);
        m.addAttribute("cost", cost);

        double totalCost = service.calculateTotal(cost, order.isRush());
        order.setTotal(totalCost);
        
        System.out.println(order);

        // persist order
        service.persistOrder(pizza, order);

        // add order object to session
        session.setAttribute("order", order);

        // add order object to view
        m.addAttribute(order);

        return "order-confirmation";
    }
}
