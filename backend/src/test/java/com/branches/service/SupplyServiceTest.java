package com.branches.service;

import com.branches.mapper.SupplyMapper;
import com.branches.model.Supply;
import com.branches.repository.SupplyRepository;
import com.branches.request.SupplyPostRequest;
import com.branches.response.SupplyGetResponse;
import com.branches.response.SupplyPostResponse;
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
class SupplyServiceTest {
    @InjectMocks
    private SupplyService service;
    @Mock
    private SupplyMapper mapper;
    @Mock
    private SupplyRepository repository;
    private List<Supply> supplyList;
    private List<SupplyGetResponse> supplyGetResponseList;

    @BeforeEach
    void init() {
        supplyList = InsumoUtils.newInsumoList();
        supplyGetResponseList = InsumoUtils.newInsumoGetResponseList();
    }

    @Test
    @Order(1)
    @DisplayName("findAll returns all insumos when the given argument is null")
    void findAll_ReturnsAllInsumos_WhenSuccessful() {
        List<SupplyGetResponse> expectedResponse = supplyGetResponseList;

        BDDMockito.when(repository.findAll()).thenReturn(supplyList);
        BDDMockito.when(mapper.toSupplyGetResponseList(supplyList)).thenReturn(expectedResponse);

        List<SupplyGetResponse> insumosResponse = service.findAll(null);

        Assertions.assertThat(insumosResponse)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @Order(2)
    @DisplayName("findAll returns found objects when the given argument is found")
    void findAll_ReturnsFoundObjects_WhenTheGivenArgumentIsFound() {
        SupplyGetResponse expectedSupplyGetResponse = this.supplyGetResponseList.get(0);
        List<SupplyGetResponse> expectedResponse = List.of(expectedSupplyGetResponse);

        String descriptionToBeSearched = expectedSupplyGetResponse.getDescription();

        List<Supply> foundSupply = List.of(supplyList.get(0));
        BDDMockito.when(repository.findAllByDescriptionContaining(descriptionToBeSearched)).thenReturn(foundSupply);
        BDDMockito.when(mapper.toSupplyGetResponseList(foundSupply)).thenReturn(expectedResponse);

        List<SupplyGetResponse> response = service.findAll(descriptionToBeSearched);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @Order(3)
    @DisplayName("findAll returns an empty list when the given argument is not found")
    void findAll_ReturnsEmptyList_WhenTheGivenArgumentIsNotFound() {
        BDDMockito.when(repository.findAllByDescriptionContaining(ArgumentMatchers.anyString())).thenReturn(Collections.emptyList());

        String randomDescription =  "xaxa";
        List<SupplyGetResponse> response = service.findAll(randomDescription);

        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("saveAll returns the saved objects when successful")
    void saveAll_ReturnsTheSavedObjectS_WhenSuccessful() {
        SupplyPostRequest insumoToBeSaved = InsumoUtils.newInsumoPostRequest();

        Supply supplyMapped = InsumoUtils.newInsumoToSave();

        List<SupplyPostResponse> expectedResponse = List.of(InsumoUtils.newInsumoPostResponse());

        BDDMockito.when(mapper.toSupplyList(List.of(insumoToBeSaved))).thenReturn(List.of(supplyMapped));
        BDDMockito.when(repository.saveAll(ArgumentMatchers.anyList())).thenReturn(List.of(supplyMapped));
        BDDMockito.when(mapper.toSupplyPostResponseList(List.of(supplyMapped))).thenReturn(expectedResponse);

        List<SupplyPostResponse> response = service.saveAll(List.of(insumoToBeSaved));

        Assertions.assertThat(response)
                .isNotNull()
                .isEqualTo(expectedResponse);
    }

}