package com.hmsapp.controller;

import com.hmsapp.entity.Account;
import com.hmsapp.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AccountCntroller {

    private AccountRepository accountRepository;

    public AccountCntroller(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @PostMapping("/account")
    public String getAccount(
            @RequestBody Account account
            ){
        accountRepository.save(account);
        return "booked";
    }

    @PutMapping("/account")
    public String updatedAccount(
            @RequestParam long id
    ){
        Account account = accountRepository.findById(id).get();
        account.setName("new name");
        accountRepository.save(account);
        return "updated successfully";
    }

}
