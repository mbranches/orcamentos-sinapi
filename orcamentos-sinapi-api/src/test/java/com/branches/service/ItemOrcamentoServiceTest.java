package com.branches.service;

import com.branches.mapper.ItemOrcamentoMapper;
import com.branches.model.ItemOrcamento;
import com.branches.model.Orcamento;
import com.branches.repository.ItemOrcamentoRepository;
import com.branches.request.ItemOrcamentoPostRequest;
import com.branches.response.ItemOrcamentoGetResponse;
import com.branches.response.ItemOrcamentoPostResponse;
import com.branches.utils.ItemOrcamentoUtils;
import com.branches.utils.OrcamentoUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItemOrcamentoServiceTest {
    @InjectMocks
    private ItemOrcamentoService service;
    @Mock
    private ItemOrcamentoRepository repository;
    @Mock
    private OrcamentoService orcamentoService;
    @Mock
    private ItemOrcamentoMapper mapper;
    private List<ItemOrcamento> itemOrcamentoList;
    private List<ItemOrcamentoGetResponse> itemGetResponseList;

    @BeforeEach
    void init() {
        itemOrcamentoList = ItemOrcamentoUtils.newItemOrcamentoList();
        itemGetResponseList = ItemOrcamentoUtils.newItemGetResponseList();
    }

    @Test
    @Order(1)
    @DisplayName("saveAll return all object saved when successful")
    void saveAll_ReturnsAllObjectsSaved_whenSuccessful() {
        List<ItemOrcamentoPostRequest> itemPostRequestList = ItemOrcamentoUtils.newItemPostRequestList();

        List<ItemOrcamentoPostResponse> expectedResponse = ItemOrcamentoUtils.newItemPostResponseList();

        BDDMockito.when(repository.saveAll(ArgumentMatchers.anyList())).thenReturn(itemOrcamentoList);
        BDDMockito.when(mapper.toItemOrcamentoList(itemPostRequestList)).thenReturn(itemOrcamentoList);
        BDDMockito.when(mapper.toItemOrcamentoPostResponseList(itemOrcamentoList)).thenReturn(expectedResponse);

        List<ItemOrcamentoPostResponse> response = service.saveAll(itemPostRequestList);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @DisplayName("findAll returns all items when successful")
    @Order(2)
    void findAll_ReturnsAllItems_WhenSuccessful() {
        List<ItemOrcamentoGetResponse> expectedResponse = this.itemGetResponseList;

        BDDMockito.when(repository.findAll()).thenReturn(itemOrcamentoList);
        BDDMockito.when(mapper.toItemOrcamentoGetResponseList(itemOrcamentoList)).thenReturn(expectedResponse);
        List<ItemOrcamentoGetResponse> response = service.findAll();

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @DisplayName("findByIdOrElseThrowsNotFoundException returns the object found when successful")
    @Order(3)
    void findByIdOrElseThrowsNotFoundException_ReturnsObjectFound_WhenSuccessful() {
        ItemOrcamento expectedItem = itemOrcamentoList.get(0);
        Long idToBeSearched = expectedItem.getId();

        BDDMockito.when(repository.findById(idToBeSearched)).thenReturn(Optional.of(expectedItem));

        ItemOrcamento response = service.findByIdOrElseThrowsNotFoundException(idToBeSearched);

        Assertions.assertThat(response)
                .isNotNull()
                .isEqualTo(expectedItem);
    }

    @Test
    @DisplayName("findByIdOrElseThrowsNotFoundException throws not found exception when id is not found")
    @Order(4)
    void findByIdOrElseThrowsNotFoundException_ThrowsNotFoundException_WhenIdIsNotFound() {
        Long randomId = 440921L;

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.findByIdOrElseThrowsNotFoundException(randomId))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Item Not Found");
    }

    @Test
    @DisplayName("findByOrcamento returns all items of the orcamento submitted when successul")
    @Order(5)
    void findByOrcamento_ReturnsAllItemsOfTheOrcamentoSubmitted_WhenSuccessful() {
        Orcamento orcamentoToBeSubmitted = OrcamentoUtils.newOrcamentoSaved();
        Long idToBeSubmitted = orcamentoToBeSubmitted.getId();
        List<ItemOrcamentoGetResponse> expectedResponse = this.itemGetResponseList;

        BDDMockito.when(repository.findAllByOrcamentoId(ArgumentMatchers.anyLong())).thenReturn(itemOrcamentoList);
        BDDMockito.when(mapper.toItemOrcamentoGetResponseList(itemOrcamentoList)).thenReturn(expectedResponse);

        List<ItemOrcamentoGetResponse> response = service.findByOrcamento(idToBeSubmitted);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @DisplayName("findByOrcamento returns an empty list when orcamento doesn't contains items")
    @Order(6)
    void findByOrcamento_ReturnsAnEmptyList_WhenOrcamentoDoesNotContainsItems() {
        Orcamento orcamentoToBeSubmitted = OrcamentoUtils.newOrcamentoSaved();
        Long idToBeSubmitted = orcamentoToBeSubmitted.getId();

        BDDMockito.when(repository.findAllByOrcamentoId(ArgumentMatchers.anyLong())).thenReturn(Collections.emptyList());

        List<ItemOrcamentoGetResponse> response = service.findByOrcamento(idToBeSubmitted);

        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findByOrcamento throws not found exception when orcamento doesn't exists")
    @Order(7)
    void findByOrcamento_ThrowsNotFoundException_WhenOrcamentoDesNotExists() {
        BDDMockito.when(orcamentoService.findByIdOrElseThrowNotFoundException(ArgumentMatchers.anyLong()))
                .thenThrow(ResponseStatusException.class);

        Long randomId = 8881L;

        Assertions.assertThatThrownBy(() -> service.findByOrcamento(randomId))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("delete removes item when successful")
    @Order(8)
    void delete_ById_RemovesItem_WhenSuccessful() {
        ItemOrcamento itemToBeDeleted = itemOrcamentoList.get(0);
        Long itemToBeDeletedId = itemToBeDeleted.getId();

        BDDMockito.doNothing().when(repository).delete(ArgumentMatchers.any(ItemOrcamento.class));
        BDDMockito.when(repository.findById(itemToBeDeletedId)).thenReturn(Optional.of(itemToBeDeleted));

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.delete(itemToBeDeletedId));
    }

    @Test
    @DisplayName("delete throws not found exception when id is not found")
    @Order(9)
    void delete_ById_ThrowsNotFoundException_WhenIdIsNotFound() {
        Long randomIdToBeDeleted = 440921L;

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.findByIdOrElseThrowsNotFoundException(randomIdToBeDeleted))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Item Not Found");
    }
}