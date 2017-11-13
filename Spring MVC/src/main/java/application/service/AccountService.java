/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.service;

import application.model.Account;
import application.model.repository.AccountRepository;
import com.lambdaworks.crypto.SCryptUtil;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private Logger log = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    AccountRepository accountDao;

   

    public Account getAccount(long id) {
        Account account = accountDao.findOne(id);
        return account;
    }

    public Account getAccount(String username) {
        Account account = accountDao.findByUsername(username);
        if (account != null) {
            log.debug("Account found with id " + account.getAccountID());
        } else {
            log.error("No account found");
        }
       
        return account;
    }

    public boolean matchUsernameAndPassword(String username, String password) {
        Account account = accountDao.findByUsername(username);
        if (account != null) {
            return SCryptUtil.check(password, account.getPassword());
        }
        return false;
    }

  

}
