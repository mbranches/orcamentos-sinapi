package com.branches.service;

import com.branches.exception.BadRequestException;
import com.branches.exception.NotFoundException;
import com.branches.mapper.BudgetMapper;
import com.branches.model.Budget;
import com.branches.model.BudgetItem;
import com.branches.model.Client;
import com.branches.model.Supply;
import com.branches.repository.BudgetRepository;
import com.branches.request.BudgetPostRequest;
import com.branches.request.BudgetPutRequest;
import com.branches.response.BudgetGetResponse;
import com.branches.response.BudgetPostResponse;
import com.branches.utils.BudgetUtils;
import com.branches.utils.SupplyUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BudgetServiceTest {
    @InjectMocks
    private BudgetService service;
    @Mock
    private BudgetRepository repository;
    @Mock
    private BudgetMapper mapper;
    @Mock
    private ClientService clientService;
    private List<Budget> budgetList;
    private List<BudgetGetResponse> budgetGetResponseList;

    @BeforeEach
    void init() {
        budgetList = BudgetUtils.newBudgetList();
        budgetGetResponseList = BudgetUtils.newBudgetGetResponseList();
    }
    
    @Test
    @DisplayName("findAll returns all budgets when successful")
    @Order(1)
    void findAll_ReturnsAllBudgets_WhenSuccessful() {
        Sort sort = Sort.by("createdAt").descending();

        BDDMockito.when(repository.findAll(sort)).thenReturn(budgetList);
        BDDMockito.when(mapper.toBudgetGetResponseList(budgetList)).thenReturn(budgetGetResponseList);

        List<BudgetGetResponse> response = service.findAll(null);

        org.assertj.core.api.Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(budgetGetResponseList);

    }

    @Test
    @DisplayName("findAll returns found budgets when the argument is given")
    @Order(2)
    void findAll_ReturnsFoundBudgets_WhenTheGivenDescriptionIsFound() {
        Sort sort = Sort.by("createdAt").descending();

        String randomDescription = "Random Description";

        BDDMockito.when(repository.findAllByDescriptionContaining(randomDescription, sort)).thenReturn(Collections.emptyList());
        BDDMockito.when(mapper.toBudgetGetResponseList(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<BudgetGetResponse> response = service.findAll(randomDescription);

        org.assertj.core.api.Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findAll returns an empty list when the given description is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenTheGivenDescriptionIsNotFound() {
        Sort sort = Sort.by("createdAt").descending();

        Budget budgetToBeFound = budgetList.getFirst();
        String descriptionToSearch = budgetToBeFound.getDescription();

        List<Budget> foundBudgets = Collections.singletonList(budgetToBeFound);

        List<BudgetGetResponse> expectedResponse = Collections.singletonList(budgetGetResponseList.getFirst());

        BDDMockito.when(repository.findAllByDescriptionContaining(descriptionToSearch, sort)).thenReturn(foundBudgets);
        BDDMockito.when(mapper.toBudgetGetResponseList(foundBudgets)).thenReturn(expectedResponse);

        List<BudgetGetResponse> response = service.findAll(descriptionToSearch);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @DisplayName("findByIdOrElseThrowsNotFoundException returns found budget when successful")
    @Order(4)
    void findByIdOrElseThrowsNotFoundException_ReturnsFoundBudget_WhenSuccessful() {
        Budget budgetToBeFound = budgetList.getFirst();
        Long idToSearch = budgetToBeFound.getId();

        BDDMockito.when(repository.findById(idToSearch)).thenReturn(Optional.of(budgetToBeFound));

        Budget response = service.findByIdOrElseThrowsNotFoundException(idToSearch);

        Assertions.assertThat(response)
                .isNotNull()
                .isEqualTo(budgetToBeFound);
    }

    @Test
    @DisplayName("findByIdOrElseThrowsNotFoundException throws NotFoundException when the given id is not found")
    @Order(5)
    void findByIdOrElseThrowsNotFoundException_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() {
        Long randomId = 999L;

        BDDMockito.when(repository.findById(randomId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.findByIdOrElseThrowsNotFoundException(randomId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Budget with id '%s' is not found".formatted(randomId));
    }

    @Test
    @DisplayName("save returns saved budget when successful")
    @Order(6)
    void save_ReturnsSavedBudget_WhenSuccessful() {
        BudgetPostRequest postRequest = BudgetUtils.newBudgetPostRequest();
        BudgetPostResponse postResponse = BudgetUtils.newBudgetPostResponse();

        Budget budgetToSave = BudgetUtils.newBudgetToSave();
        Budget savedBudget = BudgetUtils.newBudgetSaved();

        Client client = budgetToSave.getClient();

        BDDMockito.when(clientService.findByIdOrThrowsNotFoundException(postRequest.getClientId())).thenReturn(client);
        BDDMockito.when(mapper.toBudget(postRequest)).thenReturn(budgetToSave);
        BDDMockito.when(repository.save(budgetToSave)).thenReturn(savedBudget);
        BDDMockito.when(mapper.toBudgetPostResponse(savedBudget)).thenReturn(postResponse);

        BudgetPostResponse response = service.save(postRequest);

        Assertions.assertThat(response)
                .isNotNull()
                .isEqualTo(postResponse);
    }

    @Test
    @DisplayName("save throws NotFoundException when the given client id is not found")
    @Order(7)
    void save_ThrowsNotFoundException_WhenTheGivenClientIdIsNotFound() {
        BudgetPostRequest postRequest = BudgetUtils.newBudgetPostRequest();

        BDDMockito.when(clientService.findByIdOrThrowsNotFoundException(postRequest.getClientId())).thenThrow(NotFoundException.class);

        Assertions.assertThatThrownBy(() -> service.save(postRequest))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("update updates budget when successful")
    @Order(8)
    void update_UpdatesBudget_WhenSuccessful() {
        Budget budgetNotUpdated = budgetList.getFirst();
        Long budgetToUpdateId = budgetNotUpdated.getId();

        BudgetPutRequest putRequest = BudgetUtils.newBudgetPutRequest();

        Budget budgetToUpdate = BudgetUtils.newBudgetToUpdate();
        Client client = budgetToUpdate.getClient();

        BDDMockito.when(repository.findById(putRequest.getId())).thenReturn(Optional.of(budgetNotUpdated));
        BDDMockito.when(clientService.findByIdOrThrowsNotFoundException(putRequest.getClientId())).thenReturn(client);
        BDDMockito.when(mapper.toBudget(putRequest)).thenReturn(budgetToUpdate);
        BDDMockito.when(repository.save(budgetToUpdate)).thenReturn(budgetToUpdate);

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.update(budgetToUpdateId, putRequest));
    }

    @Test
    @DisplayName("update throws BadRequestException when the url id does not match request body id")
    @Order(9)
    void update_ThrowsBadRequestException_WhenTheUrlIdDoesNotMatchRequestBodyId() {
        Long randomId = 999L;

        BudgetPutRequest putRequest = BudgetUtils.newBudgetPutRequest();

        Assertions.assertThatThrownBy(() -> service.update(randomId, putRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("The ID in the URL (%s) does not match the ID in the request body (%s)".formatted(randomId, putRequest.getId()));
    }

    @Test
    @DisplayName("update throws NotFoundException when the given budget id is not found")
    @Order(10)
    void update_ThrowsNotFoundException_WhenTheGivenBudgetIdIsNotFound() {
        Long randomId = 999L;

        BudgetPutRequest putRequest = BudgetUtils.newBudgetPutRequest().withId(randomId);

        BDDMockito.when(repository.findById(putRequest.getId())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.update(randomId, putRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Budget with id '%s' is not found".formatted(randomId));
    }

    @Test
    @DisplayName("update throws NotFoundException when the given client id is not found")
    @Order(11)
    void update_ThrowsNotFoundException_WhenTheGivenClientIdIsNotFound() {
        Long randomClientId = 999L;

        Budget budgetToUpdate = budgetList.getFirst();
        Long budgetToUpdateId = budgetToUpdate.getId();

        BudgetPutRequest putRequest = BudgetUtils.newBudgetPutRequest().withClientId(randomClientId);

        BDDMockito.when(repository.findById(budgetToUpdateId)).thenReturn(Optional.of(budgetToUpdate));
        BDDMockito.when(clientService.findByIdOrThrowsNotFoundException(randomClientId)).thenThrow(NotFoundException.class);

        Assertions.assertThatThrownBy(() -> service.update(budgetToUpdateId, putRequest))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("deleteById removes budget when successful")
    @Order(12)
    void deleteById_RemovesBudget_WhenSuccessful() {
        Budget budgetToDelete = budgetList.getFirst();
        Long budgetToDeleteId = budgetToDelete.getId();

        BDDMockito.when(repository.findById(budgetToDeleteId)).thenReturn(Optional.of(budgetToDelete));
        BDDMockito.doNothing().when(repository).delete(budgetToDelete);

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.deleteById(budgetToDeleteId));
    }

    @Test
    @DisplayName("deleteById throws NotFoundException when the given id is not found")
    @Order(13)
    void deleteById_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() {
        Long randomId = 999L;

        BDDMockito.when(repository.findById(randomId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.deleteById(randomId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Budget with id '%s' is not found".formatted(randomId));
    }

    @Test
    @DisplayName("updatesTotalValue updates budget total value when successful")
    @Order(14)
    void updatesTotalValue_UpdatesBudgetTotalValue_WhenSuccessful() {
        Budget budgetToUpdateTotalValue = budgetList.getFirst().withTotalValue(0D);
        Long budgetToUpdateTotalValueId = budgetToUpdateTotalValue.getId();

        Supply supply = SupplyUtils.newSupplyList().getFirst();
        BudgetItem budgetItem = BudgetItem.builder().id(1L).supply(supply).budget(budgetToUpdateTotalValue).quantity(1).totalValue(supply.getPrice() * 1).build();
        double totalValue = budgetItem.getSupply().getPrice() * budgetItem.getQuantity();
        List<BudgetItem> budgetItemList = List.of(budgetItem);

        budgetToUpdateTotalValue.setItems(budgetItemList);

        Budget budgetUpdated = budgetToUpdateTotalValue.withTotalValue(totalValue);

        BDDMockito.when(repository.findById(budgetToUpdateTotalValueId)).thenReturn(Optional.of(budgetToUpdateTotalValue));
        BDDMockito.when(repository.save(budgetUpdated)).thenReturn(budgetUpdated);

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.updatesTotalValue(budgetToUpdateTotalValueId));
    }

    @Test
    @DisplayName("updatesTotalValue throws NotFoundException when the given id is not found")
    @Order(15)
    void updatesTotalValue_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() {
        long randomId = 999L;

        BDDMockito.when(repository.findById(randomId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.updatesTotalValue(randomId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Budget with id '%s' is not found".formatted(randomId));
    }
}