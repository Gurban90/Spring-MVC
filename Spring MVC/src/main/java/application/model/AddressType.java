/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.stereotype.Component;

/**
 *
 * @author Gerben
 */
@Entity
public class AddressType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressTypeID;

    @NotNull
    @Size(min = 3, max = 30)
    private String addressTypeName;

    @OneToMany(mappedBy = "addresstype")
    private List<Address> addresses = new ArrayList<Address>();

    public AddressType() {};

    public AddressType(String addressType) {
        this.addressTypeName = addressType;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public int getAddressTypeID() {
        return addressTypeID;
    }

    public void setAddressTypeID(int addressTypeID) {
        this.addressTypeID = addressTypeID;
    }

    public String getAddressTypeName() {
        return addressTypeName;
    }

    public void setAddressTypeName(String addressTypeName) {
        this.addressTypeName = addressTypeName;
    }

}
