/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.service;

import application.model.OrderDetail;

/**
 *
 * @author Gerben
 */
public interface OrderDetailService {

    public void addOrderDetail(int orderID, int cheeseID, OrderDetail newOrderDetail);

    public void removeOrderDetail(int orderDetailID, int quantity);

    public void editOrderDetail(OrderDetail editOrderDetail);

}
