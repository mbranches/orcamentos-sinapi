package com.branches.service;

import com.branches.exception.BadRequestException;
import com.branches.model.Client;
import com.branches.request.ClientPutRequest;
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

    public void update(Long clientId, ClientPutRequest putRequest) {
        if (!clientId.equals(putRequest.getId())) throw new BadRequestException("The ID in the URL (%s) does not match the ID in the request body (%s)".formatted(clientId, putRequest.getId()));

        findByIdOrThrowsNotFoundException(clientId);

        Client clientToUpdate = mapper.toClient(putRequest);

        repository.save(clientToUpdate);
    }
}
