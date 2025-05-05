package com.branches.repository;

import com.branches.model.ItemOrcamento;
import com.branches.model.Orcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemOrcamentoRepository extends JpaRepository<ItemOrcamento, Long> {

    List<ItemOrcamento> findAllByOrcamentoId(Long orcamentoId);

    @Modifying
    @Query("delete from item_orcamento io where io.orcamento.id = :orcamentoId")
    void deleteByOrcamentoId(@Param("orcamentoId") Long orcamentoId);
}
