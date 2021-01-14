package com.felipemelo.osworks.api.controller;

import java.util.List;
import java.util.Optional;

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
	
	@GetMapping()
	public List<OrdemServico> findAll(){
		return ordemServicoRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OrdemServico> find(@PathVariable Long id){
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(id);
		if (ordemServico.isPresent()) {
			return ResponseEntity.ok(ordemServico.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrdemServico insert(@RequestBody OrdemServico ordemServico) {
		return ordemServicoService.save(ordemServico);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OrdemServico> update(@PathVariable Long id, 
			@RequestBody OrdemServico ordemServico){
		if (!ordemServicoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		ordemServico.setId(id);
		ordemServicoRepository.save(ordemServico);
		
		return ResponseEntity.ok(ordemServico);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		if (!ordemServicoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		ordemServicoRepository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}
