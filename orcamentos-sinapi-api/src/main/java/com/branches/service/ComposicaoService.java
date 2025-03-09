package com.branches.service;

import com.branches.model.Composicao;
import com.branches.repository.ComposicaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComposicaoService {
    private final ComposicaoRepository repository;

    public List<Composicao> findAll() {
        return repository.findAll();
    }

    public List<Composicao> findByDescription(String description) {
        return repository.findAllByDescricaoContaining(description);
    }

    public Composicao save(Composicao composicao) {
        return repository.save(composicao);
    }
}
