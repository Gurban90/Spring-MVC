/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.service;

import application.model.Orders;

/**
 *
 * @author Gerben
 */
public interface OrderService {
    
     public void addOrder(int clientID, Orders newOrder);
}
