package com.br.domain.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.br.domain.model.Account;
import com.br.domain.model.Purchase;

public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query("select p from Purchase p inner join p.account a where a = :account")
	public Page<Purchase> findByPurcheadForAccount(@Param("account") Account account, Pageable pageable);

}