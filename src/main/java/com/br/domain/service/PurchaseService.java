package com.br.domain.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.domain.exception.NegocioException;
import com.br.domain.model.Account;
import com.br.domain.model.AccountStatus;
import com.br.domain.model.Payment;
import com.br.domain.model.Purchase;
import com.br.domain.model.PurchaseStatus;
import com.br.domain.repository.AccountRepository;
import com.br.domain.repository.PaymentRepository;

@Service
public class PurchaseService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PaymentRepository paymentRepository;
	
	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	public Purchase save(Purchase purchase, Long accountId) {
		Account account = accountRepository.findById(accountId).get();
		if (!validatedAccount(account, purchase)) {
			throw new NegocioException("não é possivel ajustar, onde o valor total ficara negativo. valor total atual: " + account.getAmount());
		}

		if (!validatedPurchase(purchase)) {
			throw new NegocioException("descrição é obrigatoria");
		}

		if (validatedStatusAccount(account)) {
			throw new NegocioException("Conta desativada ou suspensa. contate o administrador do sistema");
		}

		purchase.setAccount(account);
		account.getPurchaselist().add(purchase);
		account.calculeTotalAmount();
		entityManager.persist(purchase);
		return purchase;
	}

	@Transactional
	public void scheduledPayment(Account account, BigDecimal amountReceived) {
		
		Payment payment = new Payment();
		payment.setAccount(account);
		paymentRepository.save(payment);
		
		payment.setAmount(account.getAmount());
		payment.setAmountReceived(amountReceived);
		payment.setThing(amountReceived.subtract(account.getAmount()));
		
		account.getPurchaselist().stream().forEachOrdered(purchased -> purchased.setStatus(PurchaseStatus.PAID));
		account.getPurchaselist().stream().forEachOrdered(purchased -> purchased.setPayment(payment));
		account.setAmount(BigDecimal.ZERO);
	
		
	}
	
	public List<Purchase> listAll(Long accountId) {
		Account account = accountRepository.findById(accountId).get();
		return account.getPurchaselist();
	}
	
	private boolean validatedAccount(Account account, Purchase purchase) {

		if (purchase.getPrice().compareTo(BigDecimal.ZERO) < 0) {
			if (account.getAmount().compareTo(BigDecimal.ZERO) < 0
					|| account.getAmount().compareTo(BigDecimal.ZERO) == 0) {
				return false;
			}
			
			if (!(account.getAmount().subtract(purchase.getPrice()).compareTo(BigDecimal.ZERO) < 0)) {
				return false;
			}
		}

		return true;
	}

	private boolean validatedPurchase(Purchase purchase) {
		if (purchase.getPrice().compareTo(BigDecimal.ZERO) < 0) {
			if (purchase.getDescription() == null || purchase.getDescription().isEmpty()) {
				return false;
			}
		}

		return true;
	}

	private boolean validatedStatusAccount(Account account) {
		account.setDaysWithoutPaying(ChronoUnit.DAYS.between(account.getLastDataPayment(), LocalDateTime.now()));
		if (account.getAccountStatus().equals(AccountStatus.ACTIVE)) {
			return false;
		}
		return true;
	}

}
