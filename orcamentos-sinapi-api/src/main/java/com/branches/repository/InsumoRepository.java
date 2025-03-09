package com.branches.repository;

import com.branches.model.Insumo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsumoRepository extends JpaRepository<Insumo, Long> {
    List<Insumo> findAllByDescricaoContaining(String descricao);
}
