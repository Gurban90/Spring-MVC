/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
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
    private long accountID;
    @Column(unique = true)
    @NotNull(message = "Username allready exists!")
    private String username;
    private String password;
    private String theRole;

    @OneToOne
    @JoinColumn(name = "clientID", referencedColumnName = "clientID")
    private Client client;
     
    public Account() {
    }

    public Account(String username, String password) {
        this.password = password;
        this.username = username;
    }

   
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
     
    public long getAccountID() {
        return accountID;
    }

    public void setAccountID(long accountID) {
        this.accountID = accountID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
   
    public String getTheRole() {
        return theRole;
    }

    public void setTheRole(String theRole) {
        this.theRole = theRole;
    }
    

}
