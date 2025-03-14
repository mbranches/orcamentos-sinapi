package com.branches.service;

import com.branches.mapper.InsumoMapper;
import com.branches.model.Insumo;
import com.branches.repository.InsumoRepository;
import com.branches.request.InsumoPostRequest;
import com.branches.utils.InsumoCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class InsumoServiceTest {
    @InjectMocks
    private InsumoService service;
    @Mock
    private InsumoMapper mapper;
    @Mock
    private InsumoRepository repository;

    private final List<com.branches.model.Insumo> INSUMOS_LIST = new ArrayList<>();

    @BeforeEach
    void init() {
        com.branches.model.Insumo insumo1 = com.branches.model.Insumo.builder().id(1L).codigo(11L).descricao("Descrição 1").unidadeMedida("m2").origemPreco("XX").preco(25D).build();
        com.branches.model.Insumo insumo2 = com.branches.model.Insumo.builder().id(2L).codigo(22L).descricao("Descrição 2").unidadeMedida("m2").origemPreco("XX").preco(50D).build();
        com.branches.model.Insumo insumo3 = com.branches.model.Insumo.builder().id(3L).codigo(33L).descricao("Descrição 3").unidadeMedida("un").origemPreco("YY").preco(80D).build();
        INSUMOS_LIST.addAll(List.of(insumo1, insumo2, insumo3));
    }

    @Test
    @Order(1)
    @DisplayName("findAll returns all insumos when successful")
    void findAll_ReturnsAllInsumos_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(INSUMOS_LIST);
        List<com.branches.model.Insumo> insumosResponse = service.findAll();

        Assertions.assertThat(insumosResponse)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(this.INSUMOS_LIST);
    }

    @Test
    @Order(2)
    @DisplayName("findByDescription returns found objects when successful")
    void findByDescription_ReturnsFoundObjects_WhenSuccessful() {
        BDDMockito.when(repository.findAllByDescricaoContaining(ArgumentMatchers.anyString())).thenReturn(List.of(INSUMOS_LIST.get(0)));

       Insumo expectedInsumo = this.INSUMOS_LIST.get(0);

        String descriptionToBeSearched = expectedInsumo.getDescricao();
        List<com.branches.model.Insumo> insumosResponse = service.findByDescription(descriptionToBeSearched);

        Assertions.assertThat(insumosResponse)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(insumosResponse.get(0))
                .isEqualTo(expectedInsumo);
    }

    @Test
    @Order(3)
    @DisplayName("findByDescription returns empty list when description not exists")
    void findByDescription_ReturnsEmptyList_WhenDescriptionNotExists() {
        BDDMockito.when(repository.findAllByDescricaoContaining(ArgumentMatchers.anyString())).thenReturn(Collections.emptyList());

        String randomDescription =  "xaxa";
        List<com.branches.model.Insumo> insumosResponse = service.findByDescription(randomDescription);

        Assertions.assertThat(insumosResponse)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("save returns the saved object when successful")
    void save_ReturnsTheSavedObject_WhenSuccessful() {
        InsumoPostRequest insumoToBeSaved = InsumoPostRequest.builder()
                .codigo(1L)
                .descricao("Areia")
                .unidadeMedida("Kg")
                .origemPreco("XX")
                .preco(20D)
                .build();

        Insumo insumoMapped = Insumo.builder()
                .codigo(1L)
                .descricao("Areia")
                .unidadeMedida("Kg")
                .origemPreco("XX")
                .preco(20D)
                .build();;

        Insumo insumoExpected = InsumoCreator.createsInsumo();

        BDDMockito.when(mapper.toInsumo(insumoToBeSaved)).thenReturn(insumoMapped);
        BDDMockito.when(repository.save(ArgumentMatchers.any(Insumo.class))).thenReturn(insumoExpected);

        Insumo insumoResponse = service.save(insumoToBeSaved);

        Assertions.assertThat(insumoResponse)
                .isNotNull()
                .isEqualTo(insumoExpected);
    }

}