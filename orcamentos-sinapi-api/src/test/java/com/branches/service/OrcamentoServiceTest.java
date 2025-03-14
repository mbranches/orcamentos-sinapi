package com.branches.service;

import com.branches.mapper.OrcamentoMapper;
import com.branches.model.Orcamento;
import com.branches.repository.OrcamentoRepository;
import com.branches.request.OrcamentoPostRequest;
import com.branches.utils.OrcamentoCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrcamentoServiceTest {
    @InjectMocks
    private OrcamentoService service;
    @Mock
    private OrcamentoRepository repository;
    @Mock
    private OrcamentoMapper mapper;

    @Test
    @Order(1)
    @DisplayName("save returns created object when successful")
    void save_ReturnsCreatedObject_WhenSuccessful() {
        Orcamento orcamentoExpected = OrcamentoCreator.createsOrcamento();
        OrcamentoPostRequest orcamentoToBeSaved = OrcamentoPostRequest.builder().nome("Orçamento 1").build();
        Orcamento orcamentoMapped = OrcamentoCreator.createsOrcamento();
        orcamentoMapped.setId(null);

        BDDMockito.when(repository.save(ArgumentMatchers.any(Orcamento.class))).thenReturn(orcamentoExpected);
        BDDMockito.when(mapper.toOrcamento(orcamentoToBeSaved)).thenReturn(orcamentoMapped);

        Orcamento returnedOrcamento = service.save(orcamentoToBeSaved);

        Assertions.assertThat(returnedOrcamento)
                .isNotNull()
                .isEqualTo(orcamentoExpected);
    }
}