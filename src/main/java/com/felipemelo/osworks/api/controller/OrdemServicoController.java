package com.felipemelo.osworks.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.felipemelo.osworks.api.model.OrdemServicoDTO;
import com.felipemelo.osworks.api.model.OrdemServicoInput;
import com.felipemelo.osworks.domain.model.OrdemServico;
import com.felipemelo.osworks.domain.repository.OrdemServicoRepository;
import com.felipemelo.osworks.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

	@Autowired
	private GestaoOrdemServicoService ordemServicoService;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping()
	public List<OrdemServicoDTO> findAll(){
		return toCollectionDto(ordemServicoRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrdemServicoDTO> find(@PathVariable Long id){
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(id);
		if (ordemServico.isPresent()) {
			OrdemServicoDTO ordemServicoDto = toDto(ordemServico.get());
			return ResponseEntity.ok(ordemServicoDto);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServicoDTO insert(@Valid @RequestBody OrdemServicoInput ordemServicoInput) {
		OrdemServico ordemServico = fromInput(ordemServicoInput);
		
		return toDto(ordemServicoService.save(ordemServico));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OrdemServicoDTO> update(@PathVariable Long id, 
			@RequestBody OrdemServico ordemServico){
		if (!ordemServicoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		ordemServico.setId(id);
		
		return ResponseEntity.ok(toDto(ordemServicoRepository.save(ordemServico)));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		if (!ordemServicoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		ordemServicoRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
	
	private OrdemServicoDTO toDto(OrdemServico ordemServico) {
		return modelMapper.map(ordemServico, OrdemServicoDTO.class);
	}
	
	private List<OrdemServicoDTO> toCollectionDto(List<OrdemServico> ordensServico){
		return ordensServico.stream()
				.map(ordemServico -> toDto(ordemServico))
				.collect(Collectors.toList());
	}
	
	private OrdemServico fromInput(OrdemServicoInput ordemServicoInput) {
		return modelMapper.map(ordemServicoInput, OrdemServico.class);
	}
}
