package com.br.domain.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.br.domain.exception.ClientNotFoundException;
import com.br.domain.exception.NegocioException;
import com.br.domain.model.Account;
import com.br.domain.model.Client;
import com.br.domain.model.Role;
import com.br.domain.repository.ClientRepository;
import com.br.domain.repository.RoleRepository;

@Service
public class ClientService {

	private static String MSG_CONTA_NAO_ENCONTRADA = "Não existe um cadastro de conta com código %d";

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ClientRepository clientRepository;

	public Page<Client> list(Pageable pageable) {
		return clientRepository.findAll(pageable);
	}

	public Page<Client> findByName(String firstName, String lastName, Pageable pageable) {
		return clientRepository.findByIgnoreCaseFistNameAndLastName(firstName, lastName, pageable);
	}

	@Transactional
	public Client save(Client client) {

		Optional<Client> clientOptional = clientRepository.findByCpf(client.getCpf());

		if (clientOptional.isPresent()) {
			throw new NegocioException("Cpf já cadastrado");
		}

		client.setAccount(new Account());
		Role role = roleRepository.findById(2L).get();
		client.getRoles().add(role);
		return clientRepository.save(client);
	}

	@Transactional
	public Client update(Client client, Long clientId) {
		Client clienteAtual = clientRepository.findById(clientId).get();
		BeanUtils.copyProperties(client, clienteAtual, "id", "cpf", "account", "password");
		return clientRepository.save(clienteAtual);
	}

	@Transactional
	public void clienteDisable(Long clientId) {
		Client client = buscarOuFalhar(clientId);
		client.disable();
	}

	@Transactional
	public void clienteEnable(Long clientId) {
		Client client = buscarOuFalhar(clientId);
		client.enable();
	}

	public Client buscarOuFalhar(Long id) {
		return clientRepository.findById(id)
				.orElseThrow(() -> new ClientNotFoundException(String.format(MSG_CONTA_NAO_ENCONTRADA, id)));
	}

}
