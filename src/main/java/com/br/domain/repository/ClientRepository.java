package com.br.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.br.domain.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

	public Page<Client> findAll(Pageable pageable);
	
	public Optional<Client> findByCpf(String cpf);

	@Query(value="SELECT c FROM Client c WHERE c.firstName LIKE CONCAT('%', ?1, '%') OR c.lastName LIKE CONCAT('%', ?2, '%')")
	public Page<Client> findByIgnoreCaseFistNameAndLastName(String firstName, String lastName, Pageable pageable);
	
}
