package com.branches.service;

import com.branches.mapper.BudgetItemMapper;
import com.branches.model.Budget;
import com.branches.model.BudgetItem;
import com.branches.repository.BudgetItemRepository;
import com.branches.response.BudgetItemGetResponse;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BudgetItemServiceTest {
    @InjectMocks
    private BudgetItemService service;
    @Mock
    private BudgetItemRepository repository;
    @Mock
    private BudgetService budgetService;
    @Mock
    private BudgetItemMapper mapper;
    private List<BudgetItem> budgetItemList;
    private List<BudgetItemGetResponse> itemGetResponseList;

    @BeforeEach
    void init() {
        budgetItemList = ItemOrcamentoUtils.newItemOrcamentoList();
        itemGetResponseList = ItemOrcamentoUtils.newItemGetResponseList();
    }

    @Test
    @DisplayName("findAll returns all items when successful")
    @Order(2)
    void findAll_ReturnsAllItems_WhenSuccessful() {
        List<BudgetItemGetResponse> expectedResponse = this.itemGetResponseList;

        BDDMockito.when(repository.findAll()).thenReturn(budgetItemList);
        BDDMockito.when(mapper.toBudgetItemGetResponseList(budgetItemList)).thenReturn(expectedResponse);
        List<BudgetItemGetResponse> response = service.findAll();

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @DisplayName("findByIdOrElseThrowsNotFoundException returns the object found when successful")
    @Order(3)
    void findByIdOrElseThrowsNotFoundException_ReturnsObjectFound_WhenSuccessful() {
        BudgetItem expectedItem = budgetItemList.get(0);
        Long idToBeSearched = expectedItem.getId();

        BDDMockito.when(repository.findById(idToBeSearched)).thenReturn(Optional.of(expectedItem));

        BudgetItem response = service.findByIdOrElseThrowsNotFoundException(idToBeSearched);

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
        Budget budgetToBeSubmitted = OrcamentoUtils.newOrcamentoSaved();
        Long idToBeSubmitted = budgetToBeSubmitted.getId();
        List<BudgetItemGetResponse> expectedResponse = this.itemGetResponseList;

        BDDMockito.when(repository.findAllByBudgetId(ArgumentMatchers.anyLong())).thenReturn(budgetItemList);
        BDDMockito.when(mapper.toBudgetItemGetResponseList(budgetItemList)).thenReturn(expectedResponse);

        List<BudgetItemGetResponse> response = service.findByBudgetId(idToBeSubmitted);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @DisplayName("findByOrcamento returns an empty list when orcamento doesn't contains items")
    @Order(6)
    void findByOrcamento_ReturnsAnEmptyList_WhenOrcamentoDoesNotContainsItems() {
        Budget budgetToBeSubmitted = OrcamentoUtils.newOrcamentoSaved();
        Long idToBeSubmitted = budgetToBeSubmitted.getId();

        BDDMockito.when(repository.findAllByBudgetId(ArgumentMatchers.anyLong())).thenReturn(Collections.emptyList());

        List<BudgetItemGetResponse> response = service.findByBudgetId(idToBeSubmitted);

        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findByOrcamento throws not found exception when orcamento doesn't exists")
    @Order(7)
    void findByOrcamento_ThrowsNotFoundException_WhenOrcamentoDesNotExists() {
        BDDMockito.when(budgetService.findByIdOrElseThrowNotFoundException(ArgumentMatchers.anyLong()))
                .thenThrow(ResponseStatusException.class);

        Long randomId = 8881L;

        Assertions.assertThatThrownBy(() -> service.findByBudgetId(randomId))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("delete removes item when successful")
    @Order(8)
    void delete_ById_RemovesItem_WhenSuccessful() {
        BudgetItem itemToBeDeleted = budgetItemList.get(0);
        Long itemToBeDeletedId = itemToBeDeleted.getId();

        BDDMockito.doNothing().when(repository).delete(ArgumentMatchers.any(BudgetItem.class));
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