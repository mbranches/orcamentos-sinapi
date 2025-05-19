package com.branches.repository;

import com.branches.model.ItemOrcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemOrcamentoRepository extends JpaRepository<ItemOrcamento, Long> {

    List<ItemOrcamento> findAllByOrcamentoId(Long orcamentoId);

    Optional<ItemOrcamento> findByInsumo_IdAndOrcamento_Id(Long insumoId, Long orcamentoId);
}
