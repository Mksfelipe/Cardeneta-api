package com.br.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.domain.exception.NegocioException;
import com.br.domain.model.Purchase;
import com.br.domain.service.PurchaseService;

@RestController
@RequestMapping("/account/{accountId}/purchase")
public class PurchaseController {

	@Autowired
	private PurchaseService purchaseService;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping
	public Purchase save(@PathVariable Long accountId, @RequestBody @Validated Purchase purchase) {
		try {
			purchaseService.save(purchase, accountId);
			return purchase;
		} catch (NegocioException e) {
			throw new NegocioException(e.getMessage());
		}
	}

}
