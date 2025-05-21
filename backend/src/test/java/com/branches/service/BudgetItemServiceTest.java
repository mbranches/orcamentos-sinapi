package com.branches.service;

import com.branches.exception.BadRequestException;
import com.branches.exception.NotFoundException;
import com.branches.mapper.BudgetItemMapper;
import com.branches.model.Budget;
import com.branches.model.BudgetItem;
import com.branches.model.Supply;
import com.branches.repository.BudgetItemRepository;
import com.branches.request.BudgetItemPostRequest;
import com.branches.request.BudgetItemPutRequest;
import com.branches.response.BudgetItemGetResponse;
import com.branches.response.BudgetItemGetResponse.BudgetByBudgetItemGetResponse;
import com.branches.response.BudgetItemPostResponse;
import com.branches.response.BudgetItemPostResponse.BudgetByBudgetItemPostResponse;
import com.branches.response.BudgetItemPostResponse.SupplyByBudgetItemPostResponse;
import com.branches.utils.BudgetItemUtils;
import com.branches.utils.BudgetUtils;
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
class BudgetItemServiceTest {
    @InjectMocks
    private BudgetItemService service;
    @Mock
    private BudgetItemRepository repository;
    @Mock
    private BudgetItemMapper mapper;
    @Mock
    private BudgetService budgetService;
    @Mock
    private SupplyService supplyService;
    private List<BudgetItem> budgetItemList;
    private List<BudgetItemGetResponse> budgetGetResponseList;

    @BeforeEach
    void init() {
        budgetItemList = BudgetItemUtils.newBudgetItemList();
        budgetGetResponseList = BudgetItemUtils.newBudgetItemGetResponseList();
    }

    @Test
    @DisplayName("findAll returns all budgetItems when successful")
    @Order(1)
    void findAll_ReturnsAllBudgetItems_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(budgetItemList);
        BDDMockito.when(mapper.toBudgetItemGetResponseList(budgetItemList)).thenReturn(budgetGetResponseList);

        List<BudgetItemGetResponse> response = service.findAll();

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(budgetGetResponseList);
    }

    @Test
    @DisplayName("findByIdOrElseThrowsNotFoundException returns found budgetItem when successful")
    @Order(2)
    void findByIdOrElseThrowsNotFoundException_ReturnsFoundBudgetItem_WhenSuccessful() {
        BudgetItem budgetItemToBeFound = budgetItemList.getFirst();
        Long idToSearch = budgetItemToBeFound.getId();

        BDDMockito.when(repository.findById(idToSearch)).thenReturn(Optional.of(budgetItemToBeFound));

        BudgetItem response = service.findByIdOrElseThrowsNotFoundException(idToSearch);

        Assertions.assertThat(response)
                .isNotNull()
                .isEqualTo(budgetItemToBeFound);
    }

    @Test
    @DisplayName("findByIdOrElseThrowsNotFoundException throws NotFoundException when the given id is not found")
    @Order(3)
    void findByIdOrElseThrowsNotFoundException_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() {
        Long randomId = 999L;

        BDDMockito.when(repository.findById(randomId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.findByIdOrElseThrowsNotFoundException(randomId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Item with id '%s' is not found".formatted(randomId));
    }

    @Test
    @DisplayName("findByBudgetId returns all found budgetItems when successful")
    @Order(4)
    void findByBudgetId_ReturnsAllFoundBudgetItems_WhenSuccessful() {
        Budget budget = BudgetUtils.newBudgetList().getFirst();
        Long budgetId = budget.getId();

        List<BudgetItem> foundBudgetItemList = budgetItemList.stream()
                .filter(bi -> bi.getBudget().equals(budget))
                .toList();

        BudgetByBudgetItemGetResponse budgetByBudgetItemGetResponse = new BudgetByBudgetItemGetResponse(budgetId, budget.getDescription(), budget.getTotalValue());
        List<BudgetItemGetResponse> foundBudgetItemGetResponseList = budgetGetResponseList.stream()
                .filter(bi -> bi.getBudget().equals(budgetByBudgetItemGetResponse))
                .toList();

        BDDMockito.when(budgetService.findByIdOrElseThrowsNotFoundException(budgetId)).thenReturn(budget);
        BDDMockito.when(repository.findAllByBudgetId(budgetId)).thenReturn(foundBudgetItemList);
        BDDMockito.when(mapper.toBudgetItemGetResponseList(foundBudgetItemList)).thenReturn(foundBudgetItemGetResponseList);

        List<BudgetItemGetResponse> response = service.findByBudgetId(budgetId, null);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(foundBudgetItemGetResponseList);
    }

    @Test
    @DisplayName("findByBudgetId returns an empty list when the budget does not have budgetItems")
    @Order(5)
    void findByBudgetId_ReturnsEmptyList_WhenTheBudgetDoesNotHaveBudgetItems() {
        Budget budget = BudgetUtils.newBudgetSaved();
        Long budgetId = budget.getId();

        BDDMockito.when(budgetService.findByIdOrElseThrowsNotFoundException(budgetId)).thenReturn(budget);
        BDDMockito.when(repository.findAllByBudgetId(budgetId)).thenReturn(Collections.emptyList());
        BDDMockito.when(mapper.toBudgetItemGetResponseList(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<BudgetItemGetResponse> response = service.findByBudgetId(budgetId, null);

        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findByBudgetId returns found budgetItems when the argument is given")
    @Order(6)
    void findByBudgetId_ReturnsFoundBudgetItems_WhenTheArgumentIsGiven() {
        Budget budget = BudgetUtils.newBudgetList().getFirst();
        Long budgetId = budget.getId();

        Supply supplyToSearch = budgetItemList.getFirst().getSupply();
        String descriptionToSearch = supplyToSearch.getDescription();

        List<BudgetItem> foundBudgetItemList = budgetItemList.stream()
                .filter(bi ->
                    bi.getBudget().equals(budget) && bi.getSupply().getDescription().equalsIgnoreCase(descriptionToSearch)
                )
                .toList();

        BudgetByBudgetItemGetResponse budgetByBudgetItemGetResponse = new BudgetByBudgetItemGetResponse(budgetId, budget.getDescription(), budget.getTotalValue());
        List<BudgetItemGetResponse> foundBudgetItemGetResponseList = budgetGetResponseList.stream()
                .filter(bi ->
                        bi.getBudget().equals(budgetByBudgetItemGetResponse) && bi.getSupply().description().equalsIgnoreCase(descriptionToSearch)

                )
                .toList();

        BDDMockito.when(budgetService.findByIdOrElseThrowsNotFoundException(budgetId)).thenReturn(budget);
        BDDMockito.when(repository.findAllByBudget_IdAndSupply_DescriptionContaining(budgetId, descriptionToSearch)).thenReturn(foundBudgetItemList);
        BDDMockito.when(mapper.toBudgetItemGetResponseList(foundBudgetItemList)).thenReturn(foundBudgetItemGetResponseList);

        List<BudgetItemGetResponse> response = service.findByBudgetId(budgetId, descriptionToSearch);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(foundBudgetItemGetResponseList);
    }

    @Test
    @DisplayName("findByBudgetId returns an empty list when given budget has no supply with the given description")
    @Order(7)
    void findByBudgetId_ReturnsEmptyList_WhenTheGivenBudgetHasNoSupplyWithTheGivenDescription() {
        Budget budget = BudgetUtils.newBudgetList().getFirst();
        Long budgetId = budget.getId();

        String randomSupplyDescription = "Random Description";

        BDDMockito.when(budgetService.findByIdOrElseThrowsNotFoundException(budgetId)).thenReturn(budget);
        BDDMockito.when(repository.findAllByBudget_IdAndSupply_DescriptionContaining(budgetId, randomSupplyDescription)).thenReturn(Collections.emptyList());
        BDDMockito.when(mapper.toBudgetItemGetResponseList(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<BudgetItemGetResponse> response = service.findByBudgetId(budgetId, randomSupplyDescription);

        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findByBudgetId throws NotFoundException when the given budget id is not found")
    @Order(8)
    void findByBudgetId_ThrowsNotFoundException_WhenTheGivenBudgetIdIsNotFound() {
        Long randomId = 999L;

        BDDMockito.when(budgetService.findByIdOrElseThrowsNotFoundException(randomId)).thenThrow(NotFoundException.class);

        Assertions.assertThatThrownBy(() -> service.findByBudgetId(randomId, null))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("save returns saved budgetItem when successful")
    @Order(9)
    void save_ReturnsSavedBudgetItem_WhenSuccessful() {
        BudgetItemPostRequest postRequest = BudgetItemUtils.newBudgetItemPostRequest();
        BudgetItemPostResponse postResponse = BudgetItemUtils.newBudgetItemPostResponse();

        BudgetItem budgetItemToSave = BudgetItemUtils.newBudgetItemToSave();
        BudgetItem budgetItemSaved = BudgetItemUtils.newBudgetItemSaved();

        Long supplyId = postRequest.getSupplyId();
        Long budgetId = postRequest.getBudgetId();

        BDDMockito.when(budgetService.findByIdOrElseThrowsNotFoundException(budgetId)).thenReturn(budgetItemToSave.getBudget());
        BDDMockito.when(supplyService.findByIdOrElseThrowNotFoundException(supplyId)).thenReturn(budgetItemToSave.getSupply());
        BDDMockito.when(repository.findBySupply_IdAndBudget_Id(supplyId, budgetId)).thenReturn(Optional.empty());
        BDDMockito.when(mapper.toBudgetItem(postRequest)).thenReturn(budgetItemToSave);
        BDDMockito.when(repository.save(budgetItemToSave)).thenReturn(budgetItemSaved);
        BDDMockito.doNothing().when(budgetService).updatesTotalValue(budgetId);
        BDDMockito.when(mapper.toBudgetItemPostResponse(budgetItemSaved)).thenReturn(postResponse);

        BudgetItemPostResponse response = service.save(postRequest);

        Assertions.assertThat(response)
                .isNotNull()
                .isEqualTo(postResponse);
    }

    @Test
    @DisplayName("save updates quantity and total value when the budget id and supply id are already registered")
    @Order(10)
    void save_UpdatesQuantityAndTotalValue_WhenTheBudgetIdAndSupplyIdAreAlreadyRegistered() {
        BudgetItem budgetItemAlreadyRegistered = budgetItemList.getFirst();
        Budget budget = budgetItemAlreadyRegistered.getBudget();
        Supply supply = budgetItemAlreadyRegistered.getSupply();

        BudgetItemPostRequest postRequest = BudgetItemPostRequest.builder()
                .budgetId(budget.getId())
                .supplyId(supply.getId())
                .quantity(5)
                .build();

        BudgetByBudgetItemPostResponse budgetByBudgetItemPostResponse = new BudgetByBudgetItemPostResponse(
                budget.getId(),
                budget.getDescription(),
                budget.getTotalValue()
                );

        SupplyByBudgetItemPostResponse supplyByBudgetItemPostResponse = new SupplyByBudgetItemPostResponse(
                supply.getId(),
                supply.getCode(),
                supply.getDescription(),
                supply.getUnitMeasurement(),
                supply.getOriginPrice(),
                supply.getPrice()
        );

        int quantity = postRequest.getQuantity() + budgetItemAlreadyRegistered.getQuantity();
        double totalValue = quantity * supply.getPrice();
        BudgetItemPostResponse postResponse = BudgetItemPostResponse.builder()
                .budget(budgetByBudgetItemPostResponse)
                .supply(supplyByBudgetItemPostResponse)
                .quantity(quantity)
                .totalValue(totalValue)
                .build();

        BudgetItem budgetItemToUpdate = BudgetItem.builder().supply(supply).budget(budget).quantity(postRequest.getQuantity()).build();
        BudgetItem budgetItemUpdated = budgetItemToUpdate.withId(budgetItemAlreadyRegistered.getId()).withQuantity(quantity).withTotalValue(totalValue);

        BDDMockito.when(budgetService.findByIdOrElseThrowsNotFoundException(postRequest.getBudgetId())).thenReturn(budget);
        BDDMockito.when(supplyService.findByIdOrElseThrowNotFoundException(postRequest.getSupplyId())).thenReturn(supply);
        BDDMockito.when(repository.findBySupply_IdAndBudget_Id(supply.getId(), budget.getId())).thenReturn(Optional.of(budgetItemAlreadyRegistered));
        BDDMockito.when(mapper.toBudgetItem(postRequest)).thenReturn(budgetItemToUpdate);
        BDDMockito.when(repository.save(budgetItemUpdated)).thenReturn(budgetItemUpdated);
        BDDMockito.doNothing().when(budgetService).updatesTotalValue(budget.getId());
        BDDMockito.when(mapper.toBudgetItemPostResponse(budgetItemUpdated)).thenReturn(postResponse);

        BudgetItemPostResponse response = service.save(postRequest);

        Assertions.assertThat(response)
                .isNotNull()
                .isEqualTo(postResponse);
    }

    @Test
    @DisplayName("save returns throws NotFoundException when the given budget id is not found")
    @Order(11)
    void save_ThrowsNotFoundException_WhenTheGivenBudgetIdIsNotFound() {
        BudgetItemPostRequest postRequest = BudgetItemUtils.newBudgetItemPostRequest().withBudgetId(999L);

        Long randomBudgetId = postRequest.getBudgetId();

        BDDMockito.when(budgetService.findByIdOrElseThrowsNotFoundException(randomBudgetId)).thenThrow(NotFoundException.class);

        Assertions.assertThatThrownBy(() -> service.save(postRequest))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("save returns throws NotFoundException when the given supply id is not found")
    @Order(12)
    void save_ThrowsNotFoundException_WhenTheGivenSupplyIdIsNotFound() {
        BudgetItemPostRequest postRequest = BudgetItemUtils.newBudgetItemPostRequest().withSupplyId(999L);

        BudgetItem budgetItemToSave =  BudgetItemUtils.newBudgetItemToSave();

        Long budgetId = postRequest.getBudgetId();
        Long randomSupplyId = postRequest.getSupplyId();

        BDDMockito.when(budgetService.findByIdOrElseThrowsNotFoundException(budgetId)).thenReturn(budgetItemToSave.getBudget());
        BDDMockito.when(supplyService.findByIdOrElseThrowNotFoundException(randomSupplyId)).thenThrow(NotFoundException.class);

        Assertions.assertThatThrownBy(() -> service.save(postRequest))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("update updates budgetItem when successful")
    @Order(13)
    void update_UpdatesBudgetItem_WhenSuccessful() {
        BudgetItem budgetItemNotUpdated = budgetItemList.getFirst();
        Long budgetItemToUpdateId = budgetItemNotUpdated.getId();

        BudgetItemPutRequest putRequest = BudgetItemUtils.newBudgetItemPutRequest();

        BudgetItem budgetItemToUpdate = BudgetItemUtils.newBudgetItemToUpdate();
        Budget budget = budgetItemToUpdate.getBudget();
        Supply supply = budgetItemToUpdate.getSupply();

        double totalValue = supply.getPrice() * putRequest.getQuantity();

        BDDMockito.when(repository.findById(putRequest.getId())).thenReturn(Optional.of(budgetItemNotUpdated));
        BDDMockito.when(budgetService.findByIdOrElseThrowsNotFoundException(putRequest.getBudgetId())).thenReturn(budget);
        BDDMockito.when(supplyService.findByIdOrElseThrowNotFoundException(putRequest.getSupplyId())).thenReturn(supply);
        BDDMockito.when(mapper.toBudgetItem(putRequest)).thenReturn(budgetItemToUpdate);
        BDDMockito.when(repository.save(budgetItemToUpdate.withTotalValue(totalValue))).thenReturn(budgetItemToUpdate);
        BDDMockito.doNothing().when(budgetService).updatesTotalValue(budget.getId());

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.update(budgetItemToUpdateId, putRequest));
    }

    @Test
    @DisplayName("update throws BadRequestException when the url id does not match request body id")
    @Order(14)
    void update_ThrowsBadRequestException_WhenTheUrlIdDoesNotMatchRequestBodyId() {
        Long randomId = 999L;

        BudgetItemPutRequest putRequest = BudgetItemUtils.newBudgetItemPutRequest();

        Assertions.assertThatThrownBy(() -> service.update(randomId, putRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("The ID in the URL (%s) does not match the ID in the request body (%s)".formatted(randomId, putRequest.getId()));
    }

    @Test
    @DisplayName("update throws NotFoundException when the given budgetItem id is not found")
    @Order(15)
    void update_ThrowsNotFoundException_WhenTheGivenBudgetItemIdIsNotFound() {
        Long randomId = 999L;

        BudgetItemPutRequest putRequest = BudgetItemUtils.newBudgetItemPutRequest().withId(randomId);

        BDDMockito.when(repository.findById(putRequest.getId())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.update(randomId, putRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Item with id '%s' is not found".formatted(randomId));
    }

    @Test
    @DisplayName("update throws NotFoundException when the given budget id is not found")
    @Order(16)
    void update_ThrowsNotFoundException_WhenTheGivenBudgetIdIsNotFound() {
        Long randomBudgetId = 999L;

        BudgetItem budgetItemToUpdate = budgetItemList.getFirst();
        Long budgetItemToUpdateId = budgetItemToUpdate.getId();

        BudgetItemPutRequest putRequest = BudgetItemUtils.newBudgetItemPutRequest().withBudgetId(randomBudgetId);

        BDDMockito.when(repository.findById(budgetItemToUpdateId)).thenReturn(Optional.of(budgetItemToUpdate));
        BDDMockito.when(budgetService.findByIdOrElseThrowsNotFoundException(randomBudgetId)).thenThrow(NotFoundException.class);

        Assertions.assertThatThrownBy(() -> service.update(budgetItemToUpdateId, putRequest))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("update throws NotFoundException when the given supply id is not found")
    @Order(17)
    void update_ThrowsNotFoundException_WhenTheGivenSupplyIdIsNotFound() {
        Long randomSupplyId = 999L;

        BudgetItem budgetItemToUpdate = budgetItemList.getFirst();
        Long budgetItemToUpdateId = budgetItemToUpdate.getId();

        BudgetItemPutRequest putRequest = BudgetItemUtils.newBudgetItemPutRequest().withSupplyId(randomSupplyId);

        BDDMockito.when(repository.findById(budgetItemToUpdateId)).thenReturn(Optional.of(budgetItemToUpdate));
        BDDMockito.when(budgetService.findByIdOrElseThrowsNotFoundException(putRequest.getBudgetId())).thenReturn(budgetItemToUpdate.getBudget());
        BDDMockito.when(supplyService.findByIdOrElseThrowNotFoundException(randomSupplyId)).thenThrow(NotFoundException.class);

        Assertions.assertThatThrownBy(() -> service.update(budgetItemToUpdateId, putRequest))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("deleteById removes budgetItem when successful")
    @Order(18)
    void deleteById_RemovesBudgetItem_WhenSuccessful() {
        BudgetItem budgetItemToDelete = budgetItemList.getFirst();
        Long budgetItemToDeleteId = budgetItemToDelete.getId();

        BDDMockito.when(repository.findById(budgetItemToDeleteId)).thenReturn(Optional.of(budgetItemToDelete));
        BDDMockito.doNothing().when(repository).delete(budgetItemToDelete);

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.deleteById(budgetItemToDeleteId));
    }

    @Test
    @DisplayName("deleteById throws NotFoundException when the given id is not found")
    @Order(19)
    void deleteById_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() {
        Long randomId = 999L;

        BDDMockito.when(repository.findById(randomId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.deleteById(randomId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Item with id '%s' is not found".formatted(randomId));
    }
}