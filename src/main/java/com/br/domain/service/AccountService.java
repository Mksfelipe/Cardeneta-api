package com.br.domain.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.br.domain.model.Account;
import com.br.domain.model.Purchase;
import com.br.domain.repository.AccountRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Transactional
	public Account findById(Long accountId) {
		Account account = accountRepository.findById(accountId).get();
		account.setDaysWithoutPaying(ChronoUnit.DAYS.between(account.getLastDataPayment(), LocalDateTime.now()));
		account.getAccountStatus();
		return account;
	}

	public Page<Purchase> listOfPurchased(Account account, Pageable pageable) {
		return accountRepository.findByPurcheadForAccount(account, pageable);
	}

	
	
}
