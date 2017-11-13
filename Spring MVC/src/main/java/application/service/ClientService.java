/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.service;

import application.model.Client;

/**
 *
 * @author Gerben
 */
public interface ClientService {
    
    public void addClient(int accountID, Client newClient);
}
