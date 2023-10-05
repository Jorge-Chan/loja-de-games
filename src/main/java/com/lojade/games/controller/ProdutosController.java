package com.lojade.games.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import com.lojade.games.model.Produtos;
import com.lojade.games.repository.CategoriaRepository;
import com.lojade.games.repository.ProdutosRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*" , allowedHeaders = "*")
public class ProdutosController {

	@Autowired
	private ProdutosRepository produtosRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping
	public ResponseEntity<List<Produtos>>getAll(){
		return ResponseEntity.ok(produtosRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Produtos> getById(@PathVariable Long id){
		return produtosRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/nome/{nome}")
	public ResponseEntity<List<Produtos>> gerByNome(@PathVariable String titulo){
		return ResponseEntity.ok(produtosRepository.findAllByNomeContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity<Produtos>post(@Valid @RequestBody Produtos produtos){
		if(categoriaRepository.existsById(produtos.getCategoria().getId()))
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(produtosRepository.save(produtos));
		throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "Categoria não existente", null);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		java.util.Optional<Produtos> produtos = produtosRepository.findById(id);
		
		if(produtos.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		produtosRepository.deleteById(id);
	}
	@PutMapping
	public ResponseEntity<Produtos> put(@Valid @RequestBody Produtos produtos){
		if (produtosRepository.existsById(produtos.getId())) {
			
			if(categoriaRepository.existsById(produtos.getCategoria().getId()))
				return ResponseEntity.status(HttpStatus.OK)
						.body(produtosRepository.save(produtos));
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tema não existe!", null);
			
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
}
