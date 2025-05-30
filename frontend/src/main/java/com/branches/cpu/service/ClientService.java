package com.branches.cpu.service;

import com.branches.cpu.model.Client;
import com.branches.cpu.request.ClientPostRequest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ClientService {
    private final String URL = "http://localhost:8090/v1/clients";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Client> findAll() {
        ParameterizedTypeReference<List<Client>> typeReference = new ParameterizedTypeReference<>(){};

        ResponseEntity<List<Client>> response = restTemplate.exchange(URL, HttpMethod.GET, null, typeReference);

        return response.getBody();
    }

    public void delete(Client selectedClient) {
        String urlForDelete = URL + "/" + selectedClient.getId();

        restTemplate.delete(urlForDelete);
    }

    public List<Client> findAllByName(String nameToSearch) {
        String urlForGet = URL + "?name=" + nameToSearch;

        ParameterizedTypeReference<List<Client>> typeReference = new ParameterizedTypeReference<>() {};
        ResponseEntity<List<Client>> response = restTemplate.exchange(urlForGet, HttpMethod.GET, null, typeReference);

        return response.getBody();
    }

    public void update(Client clientToUpdate) {
        String urlForPut = URL + "/" + clientToUpdate.getId();

        restTemplate.put(urlForPut, clientToUpdate);
    }

    public Client save(Client clientToSave) {
        ClientPostRequest postRequest = ClientPostRequest.of(clientToSave);

        return restTemplate.postForObject(URL, postRequest, Client.class);
    }
}
