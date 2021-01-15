package com.felipemelo.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipemelo.osworks.domain.exception.DomainException;
import com.felipemelo.osworks.domain.model.Cliente;
import com.felipemelo.osworks.domain.model.OrdemServico;
import com.felipemelo.osworks.domain.model.StatusOrdemServico;
import com.felipemelo.osworks.domain.repository.ClienteRepository;
import com.felipemelo.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public OrdemServico save(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new DomainException("Cliente não cadastrado."));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	
}
