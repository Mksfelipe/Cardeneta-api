package com.br.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientModel {

	private String firstName;
	private String lastName;
	private String cpf;
	private Boolean enable;
	private Long accountId;
	
}
	