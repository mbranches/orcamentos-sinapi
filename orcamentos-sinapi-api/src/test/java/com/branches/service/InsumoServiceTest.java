package com.branches.service;

import com.branches.mapper.InsumoMapper;
import com.branches.model.Insumo;
import com.branches.repository.InsumoRepository;
import com.branches.request.InsumoPostRequest;
import com.branches.response.InsumoGetResponse;
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
    private List<InsumoGetResponse> insumoGetResponseList;

    @BeforeEach
    void init() {
        insumoList = InsumoUtils.newInsumoList();
        insumoGetResponseList = InsumoUtils.newInsumoGetResponseList();
    }

    @Test
    @Order(1)
    @DisplayName("findAll returns all insumos when the given argument is null")
    void findAll_ReturnsAllInsumos_WhenSuccessful() {
        List<InsumoGetResponse> expectedResponse = insumoGetResponseList;

        BDDMockito.when(repository.findAll()).thenReturn(insumoList);
        BDDMockito.when(mapper.toInsumoGetResponseList(insumoList)).thenReturn(expectedResponse);

        List<InsumoGetResponse> insumosResponse = service.findAll(null);

        Assertions.assertThat(insumosResponse)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @Order(2)
    @DisplayName("findAll returns found objects when the given argument is found")
    void findAll_ReturnsFoundObjects_WhenTheGivenArgumentIsFound() {
        InsumoGetResponse expectedInsumoGetResponse = this.insumoGetResponseList.get(0);
        List<InsumoGetResponse> expectedResponse = List.of(expectedInsumoGetResponse);

        String descriptionToBeSearched = expectedInsumoGetResponse.getDescricao();

        List<Insumo> foundInsumo = List.of(insumoList.get(0));
        BDDMockito.when(repository.findAllByDescricaoContaining(descriptionToBeSearched)).thenReturn(foundInsumo);
        BDDMockito.when(mapper.toInsumoGetResponseList(foundInsumo)).thenReturn(expectedResponse);

        List<InsumoGetResponse> response = service.findAll(descriptionToBeSearched);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @Order(3)
    @DisplayName("findAll returns an empty list when the given argument is not found")
    void findAll_ReturnsEmptyList_WhenTheGivenArgumentIsNotFound() {
        BDDMockito.when(repository.findAllByDescricaoContaining(ArgumentMatchers.anyString())).thenReturn(Collections.emptyList());

        String randomDescription =  "xaxa";
        List<InsumoGetResponse> response = service.findAll(randomDescription);

        Assertions.assertThat(response)
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