package com.br.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.br.api.assembler.ClientModelAssembler;
import com.br.api.model.ClientModel;
import com.br.domain.model.Client;
import com.br.domain.service.ClientService;

@RestController
@RequestMapping("/client")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@Autowired
	private ClientModelAssembler clientModelAssembler;

	@Autowired
	private BCryptPasswordEncoder encode;

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ClientModel save(@RequestBody Client client) {
		client.setPassword(encode.encode(client.getPassword()));
		return clientModelAssembler.toModel(clientService.save(client));
	}

	@GetMapping("/search-by-name")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Page<ClientModel> findByName(@RequestParam String firstName, @RequestParam(required = false) String lastName,
			@PageableDefault(size = 10) Pageable pageable) {
		Page<Client> listClientPage = clientService.findByName(firstName, lastName, pageable);
		List<ClientModel> listClient = clientModelAssembler.toCollect(listClientPage.getContent());

		Page<ClientModel> cozinhasModelPage = new PageImpl<>(listClient, pageable, listClientPage.getTotalElements());
		return cozinhasModelPage;
	}

	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Page<ClientModel> findAll(@PageableDefault(size = 10) Pageable pageable) {
		Page<Client> listClientPage = clientService.list(pageable);
		List<ClientModel> listClient = clientModelAssembler.toCollect(listClientPage.getContent());

		Page<ClientModel> cozinhasModelPage = new PageImpl<>(listClient, pageable, listClientPage.getTotalElements());
		return cozinhasModelPage;
	}

	@PutMapping("/{clientId}/disable")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void clientDisable(@PathVariable Long clientId) {
		clientService.clienteDisable(clientId);
	}

	@PutMapping("/{clientId}/enable")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void clientEnable(@PathVariable Long clientId) {
		clientService.clienteEnable(clientId);
	}

	@PutMapping("/{clientId}/")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void clientEnable(@PathVariable Long clientId, @RequestBody Client client) {
		clientService.update(client, clientId);
	}
}
