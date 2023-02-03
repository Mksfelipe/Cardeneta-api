package com.br.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Payment {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	private LocalDateTime created;
	
	private BigDecimal amountReceived;

	private BigDecimal amount;
	
	private BigDecimal thing;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;
	
	@JsonIgnore
	@OneToMany(mappedBy = "payment", orphanRemoval = true, cascade = CascadeType.REMOVE)
	@OrderBy("created ASC")
	private List<Purchase> purchaselist;
}	
