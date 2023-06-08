package revision.pizza.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import revision.pizza.service.PizzaService;

@RestController
@RequestMapping("/order/")
public class PizzaRestController {

    @Autowired
    PizzaService service;
    
    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<String> showOrder(@PathVariable(name="id") String id) {

        // retrieve json string based on user id
        String order = service.getOrder(id);

        if (order == null) {
            JsonObject error = Json.createObjectBuilder()
                .add("message", "Order " + id + " not found")
                .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error.toString());
        }

        return ResponseEntity.ok(order);
    }
    
}
