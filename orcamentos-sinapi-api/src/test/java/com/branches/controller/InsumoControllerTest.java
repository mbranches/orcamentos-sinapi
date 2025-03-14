package com.branches.controller;

import com.branches.mapper.InsumoMapperImpl;
import com.branches.model.Insumo;
import com.branches.repository.InsumoRepository;
import com.branches.service.InsumoService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(controllers = InsumoController.class)
@Import({InsumoService.class, InsumoRepository.class, InsumoMapperImpl.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InsumoControllerTest {
    @MockitoBean
    private InsumoService insumoService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    @DisplayName("findAll returns all insumos when description is null")
    void findAll_ReturnsAllInsumos_WhenSuccessful() {

    }
}