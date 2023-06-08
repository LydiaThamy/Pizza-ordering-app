package revision.pizza.model;

import java.util.UUID;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Order {

    private String id;

    @NotBlank(message = "Fill in your name")
    @Size(min = 3, message = "Your name must have at least 3 characters")
    private String name;

    @NotBlank(message = "Fill in your address")
    private String address;
    
    @NotNull(message = "Fill in your phone number")
    @Size(min = 8, max = 8, message = "Your phone number must have 8 digits")
    private String phone;

    private boolean rush;
    
    private String comments;

    private double total;

    // getters and setters
    public String getId() {
        return id;
    }
    public void setId() {
        this.id = UUID.randomUUID().toString().substring(0, 8);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isRush() {
        return rush;
    }
    public void setRush(boolean rush) {
        this.rush = rush;
    }

    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

    // constructor
    public Order() {
    }
    
    // toString method
    @Override
    public String toString() {
        return "Order [id=" + id + ", name=" + name + ", address=" + address + ", phone=" + phone + ", rush=" + rush
                + ", comments=" + comments + ", total=" + total + "]";
    }
}
