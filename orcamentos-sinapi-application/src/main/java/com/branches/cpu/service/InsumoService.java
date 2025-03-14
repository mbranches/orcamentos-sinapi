package com.branches.cpu.service;

import com.branches.cpu.model.Insumo;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class InsumoService {
    private final RestTemplate restTemplate = new RestTemplate();
    private final String url = "http://localhost:8090/v1/insumos";

    public List<Insumo> findAll() {
        Insumo[] arrayInsumo = restTemplate.getForObject(url, Insumo[].class);
        return Arrays.asList(arrayInsumo);
    }

    public List<Insumo> findByName(String description) {
        String urlGetByName = url + "?description=" + description;

        Insumo[] arrayInsumo = restTemplate.getForObject(urlGetByName, Insumo[].class);
        return Arrays.asList(arrayInsumo);
    }
}
