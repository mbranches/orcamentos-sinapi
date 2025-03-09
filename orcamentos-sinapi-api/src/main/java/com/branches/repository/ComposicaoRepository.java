package com.branches.repository;

import com.branches.model.Composicao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComposicaoRepository extends JpaRepository<Composicao, Long> {
    List<Composicao> findAllByDescricaoContaining(String descricao);
}
