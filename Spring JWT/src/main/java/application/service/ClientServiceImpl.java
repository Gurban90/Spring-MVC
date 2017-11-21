/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.service;

import application.model.Account;
import application.model.Client;
import application.model.repository.AccountRepository;
import application.model.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gerben
 */

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private AccountRepository accountDao;

    @Autowired
    private ClientRepository clientDao;

    public void addClient(long accountID, Client newClient) {

        Account acc = accountDao.findOne(accountID);
        acc.setClient(newClient);
        newClient.setAccount(acc);
        newClient.setAddresses(null);
        clientDao.save(newClient);
    }

}
