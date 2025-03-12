package com.branches.repository;

import com.branches.model.ItemOrcamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemOrcamentoRepository extends JpaRepository<ItemOrcamento, Long> {

}
