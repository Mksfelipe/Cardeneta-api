package com.br.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Account {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	private LocalDateTime created;

	@UpdateTimestamp
	private LocalDateTime updated;

	@CreationTimestamp
	private LocalDateTime lastDataPayment;

	@Enumerated(EnumType.STRING)
	private AccountStatus accountStatus = AccountStatus.ACTIVE;

	private BigDecimal amount = BigDecimal.ZERO;

	private Long daysWithoutPaying;

	@JsonIgnore
	@OneToMany(mappedBy = "account", orphanRemoval = true, cascade = CascadeType.REMOVE)
	@OrderBy("created ASC")
	private List<Purchase> purchaselist;

	public void calculeTotalAmount() {

		if (!this.getPurchaselist().isEmpty()) {
			this.amount = purchaselist.stream().filter(purched -> purched.getStatus().equals(PurchaseStatus.UNPAID))
					.map(Purchase::getPrice).reduce(BigDecimal::add).get();
		}
	}

	public AccountStatus getAccountStatus() {
		
		if (daysWithoutPaying > 91) {
			return this.accountStatus = AccountStatus.DISABLED;
		} else if (daysWithoutPaying > 30) {
			return this.accountStatus = AccountStatus.SUSPENDED;
		} else {
			return this.accountStatus = AccountStatus.ACTIVE;
		}
	}

}
