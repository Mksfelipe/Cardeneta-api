package com.br.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.br.api.model.ClientModel;
import com.br.domain.model.Client;

@Component
public class ClientModelAssembler {

	@Autowired
	private ModelMapper mapper;
	
	public ClientModel toModel(Client client) {
		return mapper.map(client, ClientModel.class);
	}
	
	public List<ClientModel> toCollect(List<Client> clients) {
		return clients.stream().map(client -> toModel(client)).collect(Collectors.toList());
	}
}
