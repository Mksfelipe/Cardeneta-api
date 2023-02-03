package com.br.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.domain.model.Account;
import com.br.domain.model.Payment;
import com.br.domain.service.AccountService;
import com.br.domain.service.PurchaseService;

@RestController
@RequestMapping("/account/{accountId}/payment")
public class PaymentController {

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private PurchaseService purchaseService;
	
	@PostMapping
	public void payment(@PathVariable Long accountId, @RequestBody Payment payment) {
		Account account = accountService.findById(accountId);		
		purchaseService.scheduledPayment(account, payment.getAmountReceived());
		
	}
	
}
