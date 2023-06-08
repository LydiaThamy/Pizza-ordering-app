package revision.pizza.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Pizza {
    
    @NotNull(message = "Select your pizza")
    private String pizza;
    
    @NotNull(message = "Select your size")
    private String size;

    @Min(value = 1, message = "Order at least 1 pizza")
    @Max(value = 10, message = "Order only up to 10 pizzas")
    private int quantity;

    // getters and setters
    public String getPizza() {
        return pizza;
    }

    public void setPizza(String pizza) {
        this.pizza = pizza;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // consstructor
    public Pizza() {
    }

    // toString
    @Override
    public String toString() {
        return "Pizza [pizza=" + pizza + ", size=" + size + ", quantity=" + quantity + "]";
    }
    
}
