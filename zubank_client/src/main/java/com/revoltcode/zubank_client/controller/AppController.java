package com.revoltcode.zubank_client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AppController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String auth(Model m) {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getDashboard(Model m) {
        return "index";
    }

    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public String getCustomer(Model m) {
        return "customers";
    }

    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public String getAccounts(Model m) {
        return "accounts";
    }

    @RequestMapping(value = "/accountDetail", method = RequestMethod.GET)
    public String getAccountDetails(Model m) {
        return "accountDetail";
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public String getTransactions(Model m) {
        return "transactions";
    }
}
