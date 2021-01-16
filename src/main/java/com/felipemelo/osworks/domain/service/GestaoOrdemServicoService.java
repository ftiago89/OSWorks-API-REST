package com.felipemelo.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.felipemelo.osworks.domain.exception.DomainException;
import com.felipemelo.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.felipemelo.osworks.domain.model.Cliente;
import com.felipemelo.osworks.domain.model.Comentario;
import com.felipemelo.osworks.domain.model.OrdemServico;
import com.felipemelo.osworks.domain.model.StatusOrdemServico;
import com.felipemelo.osworks.domain.repository.ClienteRepository;
import com.felipemelo.osworks.domain.repository.ComentarioRepository;
import com.felipemelo.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico save(OrdemServico ordemServico) {
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new DomainException("Cliente não cadastrado."));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscarOrdemServico(ordemServicoId);
		
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}
	
	public Comentario addComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscarOrdemServico(ordemServicoId);
		
		Comentario comentario = new Comentario();
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
	}
	
	private OrdemServico buscarOrdemServico(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
	}
	
}
