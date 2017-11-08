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
import javax.persistence.OneToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Gerben
 */
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountID;

    @NotNull
    @Size(min = 3, max = 15)
    private String password;

    @NotNull
    @Min(0)
    private int accountStatus;
    
    /*
    @OneToOne
    @JoinColumn(name = "clientID", referencedColumnName = "clientID")
    private Client client;
    */
    public Account() {
    }

    public Account(String password, Integer accountStatus) {
        this.password = password;
        this.accountStatus = accountStatus;
    }
    /*
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    */
    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    

    public int getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(int accountStatus) {
        this.accountStatus = accountStatus;
    }

}
