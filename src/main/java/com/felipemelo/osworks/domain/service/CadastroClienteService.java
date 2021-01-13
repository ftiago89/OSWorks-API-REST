package com.felipemelo.osworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipemelo.osworks.domain.exception.DomainException;
import com.felipemelo.osworks.domain.model.Cliente;
import com.felipemelo.osworks.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente save(Cliente cliente) {
		
		Cliente existente = clienteRepository.findByEmail(cliente.getEmail());
		
		if(existente != null && !existente.equals(cliente)) {
			throw new DomainException("Já existe um cliente cadastrado com esse e-mail");
		}
		
		return clienteRepository.save(cliente);
	}
	
	public void delete(Long id) {
		clienteRepository.deleteById(id);
	}
	
}
