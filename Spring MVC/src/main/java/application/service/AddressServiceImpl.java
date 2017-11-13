/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.service;

import application.model.Address;
import application.model.AddressType;
import application.model.Client;
import application.model.repository.AddressRepository;
import application.model.repository.AddressTypeRepository;
import application.model.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gerben
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressDao;

    @Autowired
    private ClientRepository clientDao;

    @Autowired
    private AddressTypeRepository addressTypeDao;

    private static int clientID;
    private static int addressTypeID;

    public void addAddress(int clientID, int addressTypeID, Address newAddress) {
        Client cat = clientDao.findOne(clientID);
        AddressType type = addressTypeDao.findOne(addressTypeID);
        newAddress.setClient(cat);
        newAddress.setAddresstype(type);
        addressDao.save(newAddress);
    }

    public void editAddress(Address editAddress) {
        Client cat = clientDao.findOne(this.clientID);
        AddressType type = addressTypeDao.findOne(this.addressTypeID);
        editAddress.setClient(cat);
        editAddress.setAddresstype(type);
        addressDao.save(editAddress);
    }

    public void setClientAddressType(int clientID, int addressTypeID) {
        this.clientID = clientID;
        this.addressTypeID = addressTypeID;
    }
}
