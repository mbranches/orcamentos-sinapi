package com.branches.service;

import com.branches.model.Client;
import com.branches.response.ClientGetResponse;
import com.branches.request.ClientPostRequest;
import com.branches.response.ClientPostResponse;
import com.branches.exception.NotFoundException;
import com.branches.mapper.ClientMapper;
import com.branches.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repository;
    private final ClientMapper mapper;

    public List<ClientGetResponse> findAll(String name) {
        List<Client> response = name == null ? repository.findAll() : repository.findAllByNameContaining(name);

        return mapper.toClientGetResponseList(response);
    }

    public ClientPostResponse save(ClientPostRequest postRequest) {
        Client client = mapper.toClient(postRequest);

        Client response = repository.save(client);

        return mapper.toClientPostResponse(response);
    }

    public ClientGetResponse findById(Long id) {
        Client response = findByIdOrThrowsNotFoundException(id);

        return mapper.toClientGetResponse(response);
    }

    public Client findByIdOrThrowsNotFoundException(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Client with id '%s' is not found".formatted(id)));
    }

    public void deleteById(Long id) {
        repository.delete(findByIdOrThrowsNotFoundException(id));
    }
}
