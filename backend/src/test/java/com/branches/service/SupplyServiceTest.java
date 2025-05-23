package com.branches.service;

import com.branches.exception.NotFoundException;
import com.branches.mapper.SupplyMapper;
import com.branches.model.Supply;
import com.branches.repository.SupplyRepository;
import com.branches.request.SupplyPostRequest;
import com.branches.response.SupplyGetResponse;
import com.branches.response.SupplyPostResponse;
import com.branches.utils.SupplyUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SupplyServiceTest {
    @InjectMocks
    private SupplyService service;
    @Mock
    private SupplyRepository repository;
    @Mock
    private SupplyMapper mapper;
    private List<Supply> supplyList;
    private List<SupplyGetResponse> supplyGetResponseList;

    @BeforeEach
    void init() {
        supplyList = SupplyUtils.newSupplyList();
        supplyGetResponseList = SupplyUtils.newSupplyGetResponseList();
    }

    @Test
    @DisplayName("findAll returns all supplies when successful")
    @Order(1)
    void findAll_ReturnsAllSupplies_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(supplyList);
        BDDMockito.when(mapper.toSupplyGetResponseList(supplyList)).thenReturn(supplyGetResponseList);

        List<SupplyGetResponse> response = service.findAll(null);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(supplyGetResponseList);

    }

    @Test
    @DisplayName("findAll returns found supplies when the argument is given")
    @Order(2)
    void findAll_ReturnsFoundSupplies_WhenTheGivenDescriptionIsFound() {
        String randomDescription = "Random Description";

        BDDMockito.when(repository.findAllByDescriptionContaining(randomDescription)).thenReturn(Collections.emptyList());
        BDDMockito.when(mapper.toSupplyGetResponseList(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<SupplyGetResponse> response = service.findAll(randomDescription);

        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findAll returns an empty list when the given description is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenTheGivenDescriptionIsNotFound() {
        Supply supplyToBeFound = supplyList.getFirst();
        String descriptionToSearch = supplyToBeFound.getDescription();

        List<Supply> foundSupplies = Collections.singletonList(supplyToBeFound);

        List<SupplyGetResponse> expectedResponse = Collections.singletonList(supplyGetResponseList.getFirst());

        BDDMockito.when(repository.findAllByDescriptionContaining(descriptionToSearch)).thenReturn(foundSupplies);
        BDDMockito.when(mapper.toSupplyGetResponseList(foundSupplies)).thenReturn(expectedResponse);

        List<SupplyGetResponse> response = service.findAll(descriptionToSearch);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @DisplayName("saveAll returns all saved supplies when successful")
    @Order(4)
    void saveAll_ReturnsAllSavedSupplies_WhenSuccessful() {
        List<Supply> suppliesToSave = Collections.singletonList(SupplyUtils.newSupplyToSave());
        List<Supply> savedSupplies = Collections.singletonList(SupplyUtils.newSupplySaved());

        List<SupplyPostRequest> postRequestList = Collections.singletonList(SupplyUtils.newSupplyPostRequest());
        List<SupplyPostResponse> postResponseList = Collections.singletonList(SupplyUtils.newSupplyPostResponse());

        BDDMockito.when(mapper.toSupplyList(postRequestList)).thenReturn(suppliesToSave);
        BDDMockito.when(repository.saveAll(suppliesToSave)).thenReturn(savedSupplies);
        BDDMockito.when(mapper.toSupplyPostResponseList(savedSupplies)).thenReturn(postResponseList);

        List<SupplyPostResponse> response = service.saveAll(postRequestList);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(postResponseList);
    }

    @Test
    @DisplayName("findByIdOrElseThrowNotFoundException returns found supply when successful")
    @Order(5)
    void findByIdOrElseThrowNotFoundException_ReturnsFoundSupply_WhenSuccessful() {
        Supply supplyToBeFound = supplyList.getFirst();
        Long idToSearch = supplyToBeFound.getId();

        BDDMockito.when(repository.findById(idToSearch)).thenReturn(Optional.of(supplyToBeFound));

        Supply response = service.findByIdOrElseThrowNotFoundException(idToSearch);

        Assertions.assertThat(response)
                .isNotNull()
                .isEqualTo(supplyToBeFound);
    }

    @Test
    @DisplayName("findByIdOrElseThrowNotFoundException throws NotFoundException when the given id is not found")
    @Order(6)
    void findByIdOrElseThrowNotFoundException_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() {
        Long randomId = 999L;

        BDDMockito.when(repository.findById(randomId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.findByIdOrElseThrowNotFoundException(randomId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Supply with id '%s' is not found".formatted(randomId));
    }
}