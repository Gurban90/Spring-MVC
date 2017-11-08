/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Gerben
 */
@Entity
public class Cheese {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cheeseID;
    
    @NotNull
    @Size(min = 3, max = 15)
    private String cheeseName;
    
    @NotNull
    @Digits(integer=6, fraction=2)
    private BigDecimal price;
    
    @NotNull
    @Min(0)    
    private int stock;

     @OneToMany(mappedBy = "cheese")
    private List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
    
    public Cheese() {
    }

    public Cheese(String cheeseName, BigDecimal price, Integer stock) {
        this.cheeseName = cheeseName;
        this.price = price;
        this.stock = stock;
    }

    public int getCheeseID() {
        return cheeseID;
    }

    public void setCheeseID(int cheeseID) {
        this.cheeseID = cheeseID;
    }

    public String getCheeseName() {
        return cheeseName;
    }

    public void setCheeseName(String cheeseName) {
        this.cheeseName = cheeseName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    
    

}

