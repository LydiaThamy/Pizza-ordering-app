package revision.pizza.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import revision.pizza.model.Order;
import revision.pizza.model.Pizza;
import revision.pizza.repository.PizzaRepository;

@Service
public class PizzaService {
    
    @Autowired
    private PizzaRepository repository;

    public static final String[] PIZZA_VALUES = {"bella", "margherita", "marinara", "spianatacalabrese", "trioformaggio"};
    public static final String[] SIZE_VALUES = {"sm", "md", "lg"};
    
    private final List<String> validPizza = new ArrayList<>(Arrays.asList(PIZZA_VALUES));
    private final List<String> validSize = new ArrayList<>(Arrays.asList(SIZE_VALUES));

    public List<ObjectError> extraErrors(Pizza pizza) {
        
        List<ObjectError> errors = new ArrayList<>();

        if (!validPizza.contains(pizza.getPizza())) {
            System.out.println(pizza.getPizza());
            ObjectError error = new ObjectError("globalError", "The pizza you have chosen is not available");
            errors.add(error);
        }

        if (!validSize.contains(pizza.getSize())) {
            System.out.println(pizza.getSize());
            ObjectError error = new ObjectError("globalError", "The size you have chosen is not available");
            errors.add(error);
        }

        return errors;
    }

    public double calculateCost(Pizza pizza, Order order) {
        
        double cost = 0;

        switch (pizza.getPizza()) {

            case "bella":
            case "marinara":
            case "spianatacalabrese":
                cost += 30;
                break;

            case "margherita":
                cost += 22;
                break;

            case "trioformaggio":
                cost += 25;
                break;            
        }

        switch (pizza.getSize()) {

            case "md":
                cost *= 1.2;
                break;

            case "lg":
                cost *= 1.5;
                break;

        }

        cost *= pizza.getQuantity();

        if (order.isRush()) {
            cost += 2;
        }

        System.out.println(cost);

        return cost;
    }

    public double calculateTotal(double cost, boolean rush) {
        if (rush == true)
            return cost += 2;
        return cost;
    }

    public void persistOrder(Pizza pizza, Order order) {

        JsonObject json = Json.createObjectBuilder()
            .add("orderId", order.getId())
            .add("name", order.getName())
            .add("address", order.getAddress())
            .add("phone", order.getPhone())
            .add("rush", order.isRush())
            .add("comments", order.getComments())
            .add("pizza", pizza.getPizza())
            .add("size", pizza.getSize())
            .add("quantity", pizza.getQuantity())
            .add("total", order.getTotal())
            .build();

        repository.persistOrder(json, order.getId());
    }

    public String getOrder(String id) {
        return repository.getOrder(id);
    }

}
