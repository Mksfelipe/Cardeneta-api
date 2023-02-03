package com.br.domain.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.domain.model.Client;
import com.br.domain.repository.ClientRepository;

@Service(value = "userService")
public class UserService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Client client = clientRepository.findByCpf(cpf).get();
        if(client == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(client.getCpf(), client.getPassword(), getAuthority(client));
    }

    private Set<SimpleGrantedAuthority> getAuthority(Client client) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        client.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }


}
