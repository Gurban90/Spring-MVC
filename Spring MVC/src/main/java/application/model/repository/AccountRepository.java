/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.model.repository;

import application.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Gerben
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
    Account findByUsername(String username);
    
}
