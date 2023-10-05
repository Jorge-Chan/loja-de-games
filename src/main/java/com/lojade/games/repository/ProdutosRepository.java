package com.lojade.games.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.lojade.games.model.Produtos;

public interface ProdutosRepository extends  JpaRepository<Produtos, Long>{

	public List <Produtos> findAllByNomeContainingIgnoreCase(@Param("nome") String nome);
}
