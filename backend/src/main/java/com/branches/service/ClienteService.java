package com.branches.service;

import com.branches.response.ClienteGetResponse;
import com.branches.request.ClientePostRequest;
import com.branches.response.ClientePostResponse;
import com.branches.exception.NotFoundException;
import com.branches.mapper.ClienteMapper;
import com.branches.model.Cliente;
import com.branches.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;
    private final ClienteMapper mapper;

    public List<ClienteGetResponse> findAll() {
        List<Cliente> response = repository.findAll();

        return mapper.toClienteGetResponseList(response);
    }

    public ClientePostResponse save(ClientePostRequest postRequest) {
        Cliente cliente = mapper.toCliente(postRequest);

        Cliente response = repository.save(cliente);

        return mapper.toClientePostResponse(response);
    }

    public ClienteGetResponse findById(Long id) {
        Cliente response = findByIdOrThrowsNotFoundException(id);

        return mapper.toClienteGetResponse(response);
    }

    public Cliente findByIdOrThrowsNotFoundException(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente with id '%s' is not found".formatted(id)));
    }
}
