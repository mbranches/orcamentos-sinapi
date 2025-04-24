package com.branches.service;

import com.branches.mapper.InsumoMapper;
import com.branches.model.Insumo;
import com.branches.repository.InsumoRepository;
import com.branches.request.InsumoPostRequest;
import com.branches.utils.InsumoUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    private List<Insumo> insumoList;

    @BeforeEach
    void init() {
        insumoList = InsumoUtils.newInsumoList();
    }

    @Test
    @Order(1)
    @DisplayName("findAll returns all insumos when successful")
    void findAll_ReturnsAllInsumos_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(insumoList);
        List<Insumo> insumosResponse = service.findAll();

        Assertions.assertThat(insumosResponse)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(this.insumoList);
    }

    @Test
    @Order(2)
    @DisplayName("findByDescription returns found objects when successful")
    void findByDescription_ReturnsFoundObjects_WhenSuccessful() {
        BDDMockito.when(repository.findAllByDescricaoContaining(ArgumentMatchers.anyString())).thenReturn(List.of(insumoList.get(0)));

       Insumo expectedInsumo = this.insumoList.get(0);

        String descriptionToBeSearched = expectedInsumo.getDescricao();
        List<Insumo> insumosResponse = service.findByDescription(descriptionToBeSearched);

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
        List<Insumo> insumosResponse = service.findByDescription(randomDescription);

        Assertions.assertThat(insumosResponse)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("saveAll returns the saved objects when successful")
    void saveAll_ReturnsTheSavedObjectS_WhenSuccessful() {
        InsumoPostRequest insumoToBeSaved = InsumoUtils.newInsumoPostRequest();

        Insumo insumoMapped = InsumoUtils.newInsumoToSave();

        List<Insumo> insumosExpected = List.of(InsumoUtils.newInsumoSaved());

        BDDMockito.when(mapper.toInsumoList(List.of(insumoToBeSaved))).thenReturn(List.of(insumoMapped));
        BDDMockito.when(repository.saveAll(ArgumentMatchers.anyList())).thenReturn(insumosExpected);

        List<Insumo> insumoResponse = service.saveAll(List.of(insumoToBeSaved));

        Assertions.assertThat(insumoResponse)
                .isNotNull()
                .isEqualTo(insumosExpected);
    }

}