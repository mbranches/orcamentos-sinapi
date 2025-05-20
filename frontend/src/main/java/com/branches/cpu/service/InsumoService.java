package com.branches.cpu.service;

import com.branches.cpu.model.Supply;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class InsumoService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://localhost:8090/v1/insumos";

    public List<Supply> findAll() {
        Supply[] arraySupply = restTemplate.getForObject(url, Supply[].class);
        return Arrays.asList(arraySupply);
    }

    public List<Supply> findByName(String description) {
        String urlGetByName = url + "?description=" + description;

        Supply[] arraySupply = restTemplate.getForObject(urlGetByName, Supply[].class);
        return Arrays.asList(arraySupply);
    }
}
