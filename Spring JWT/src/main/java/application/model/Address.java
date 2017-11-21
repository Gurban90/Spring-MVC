/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Gerben
 */
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressID;

    @NotNull
    @Min(0)
    private int housenumber;

    private String houseNumberAddition;

    @NotNull
    @Size(min = 3, max = 15)
    private String streetname;

    @NotNull
    @Size(min = 6, max = 7, message = "PostalCode should be this form: 0000AA.")
    private String postalCode;

    @NotNull
    @Size(min = 3, max = 30)
    private String city;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "clientID", referencedColumnName = "clientID")
    private Client client;
    
    @ManyToOne
    @JoinColumn(name="addressTypeID", referencedColumnName="addressTypeID")
    private AddressType addresstype;

    public Address() {
    }

    public Address(String Streetname, Integer HouseNumber, String HouseNumberAddition,
            String PostalCode, String City) {
        this.streetname = Streetname;
        this.housenumber = HouseNumber;
        this.houseNumberAddition = HouseNumberAddition;
        this.postalCode = PostalCode;
        this.city = City;

    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public int getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(int housenumber) {
        this.housenumber = housenumber;
    }

    public String getStreetname() {
        return streetname;
    }

    public void setStreetname(String streetname) {
        this.streetname = streetname;
    }

    public String getHouseNumberAddition() {
        return houseNumberAddition;
    }

    public void setHouseNumberAddition(String houseNumberAddition) {
        this.houseNumberAddition = houseNumberAddition;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public AddressType getAddresstype() {
        return addresstype;
    }

    public void setAddresstype(AddressType addresstype) {
        this.addresstype = addresstype;
    }
    
    

}
