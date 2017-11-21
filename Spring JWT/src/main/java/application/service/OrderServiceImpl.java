/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.service;

import application.model.Client;
import application.model.Orders;
import application.model.repository.ClientRepository;
import application.model.repository.OrdersRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gerben
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrdersRepository orderDao;

    @Autowired
    private ClientRepository clientDao;

    public void addOrder(int clientID, Orders newOrder) {
        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setProcessedDate(LocalDateTime.now().plusDays(7));
        newOrder.setTotalPrice(BigDecimal.ZERO);
        Client cat = clientDao.findOne(clientID);
        newOrder.setClient(cat);
        orderDao.save(newOrder);
    }

}
