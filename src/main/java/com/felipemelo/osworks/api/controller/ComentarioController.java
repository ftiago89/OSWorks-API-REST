package com.felipemelo.osworks.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.felipemelo.osworks.api.model.ComentarioDTO;
import com.felipemelo.osworks.api.model.ComentarioInput;
import com.felipemelo.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.felipemelo.osworks.domain.model.Comentario;
import com.felipemelo.osworks.domain.model.OrdemServico;
import com.felipemelo.osworks.domain.repository.OrdemServicoRepository;
import com.felipemelo.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico/{id}/comentarios")
public class ComentarioController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@GetMapping
	public List<ComentarioDTO> findAll(@PathVariable Long id){
		OrdemServico ordemServico = ordemServicoRepository.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Ordem de serviço não encontrada"));
		
		return toCollectionDto(ordemServico.getComentarios());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioDTO insert(@Valid @PathVariable Long id, @RequestBody ComentarioInput comentarioInput) {
		Comentario comentario = gestaoOrdemServico.addComentario(id, comentarioInput.getDescricao());
		
		return toDto(comentario);
	}
	
	private ComentarioDTO toDto(Comentario comentario) {
		return modelMapper.map(comentario, ComentarioDTO.class);
	}
	
	private List<ComentarioDTO> toCollectionDto(List<Comentario> comentarios){
		return comentarios.stream().map(comentario -> modelMapper.map(comentario, ComentarioDTO.class))
				.collect(Collectors.toList());
	}

}
