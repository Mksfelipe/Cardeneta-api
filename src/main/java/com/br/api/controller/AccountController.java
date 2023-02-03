package com.br.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.domain.model.Account;
import com.br.domain.model.Purchase;
import com.br.domain.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping("/{accountId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Account findBy(@PathVariable Long accountId) {
		Account account = accountService.findById(accountId);
		return account;
	}

	
	@GetMapping("/{accountId}/purchase")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Page<Purchase> findByPurchase(@PathVariable long accountId, @PageableDefault(size = 5) Pageable pageable) {
		Account account = accountService.findById(accountId);
		Page<Purchase> listPurchasePage = accountService.listOfPurchased(account, pageable);
		return listPurchasePage;
	}
	
}
