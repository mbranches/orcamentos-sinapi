package com.branches.controller;

import com.branches.exception.BadRequestException;
import com.branches.exception.NotFoundException;
import com.branches.model.Budget;
import com.branches.model.BudgetItem;
import com.branches.request.BudgetItemPostRequest;
import com.branches.request.BudgetItemPutRequest;
import com.branches.request.BudgetPostRequest;
import com.branches.request.BudgetPutRequest;
import com.branches.response.BudgetGetResponse;
import com.branches.response.BudgetItemGetResponse;
import com.branches.response.BudgetItemPostResponse;
import com.branches.response.BudgetPostResponse;
import com.branches.service.BudgetItemService;
import com.branches.service.BudgetService;
import com.branches.utils.BudgetItemUtils;
import com.branches.utils.BudgetUtils;
import com.branches.utils.FileUtils;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

@WebMvcTest(BudgetController.class)
@Import(FileUtils.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BudgetControllerTest {
    private final String URL = "/v1/budgets";
    @MockitoBean
    private BudgetService service;
    @MockitoBean
    private BudgetItemService budgetItemService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FileUtils fileUtils;
    private List<BudgetGetResponse> budgetGetResponseList;

    @BeforeEach
    void init() {
        budgetGetResponseList = BudgetUtils.newBudgetGetResponseList();
    }

    @Test
    @DisplayName("/v1/budgets returns all budgets when successful")
    @Order(1)
    void findAll_ReturnsAllBudgets_WhenSuccessful() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("budget/get-budgets-null-description.json");

        BDDMockito.when(service.findAll(null)).thenReturn(budgetGetResponseList);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("/v1/budgets?description=Orçamento%201 returns found budgets when the argument is given")
    @Order(2)
    void findAll_ReturnsFoundBudgets_WhenTheGivenDescriptionIsFound() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("budget/get-budgets-orcamento1-description.json");

        BudgetGetResponse budgetToBeFound = budgetGetResponseList.getFirst();
        String descriptionToSearch = budgetToBeFound.getDescription();

        List<BudgetGetResponse> response = Collections.singletonList(budgetToBeFound);

        BDDMockito.when(service.findAll(descriptionToSearch)).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.get(URL)
                                .param("description", descriptionToSearch)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("/v1/budgets?Random%20Description returns an empty list when the given description is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenTheGivenDescriptionIsNotFound() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("budget/get-budgets-invalid-description.json");

        String randomDescription = "Random Description";

        BDDMockito.when(service.findAll(randomDescription)).thenReturn(Collections.emptyList());

        mockMvc.perform(
                        MockMvcRequestBuilders.get(URL)
                                .param("description", randomDescription)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("/v1/budgets/1 returns found budget when successful")
    @Order(4)
    void findById_ReturnsFoundBudget_WhenSuccessful() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("budget/get-budget-by-id-200.json");

        BudgetGetResponse budgetToBeFound = budgetGetResponseList.getFirst();
        Long idToSearch = budgetToBeFound.getId();

        BDDMockito.when(service.findById(idToSearch)).thenReturn(budgetToBeFound);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/%s".formatted(idToSearch)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("/v1/budgets/999 throws NotFoundException when the given id is not found")
    @Order(5)
    void findById_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("budget/get-budget-by-id-404.json");

        Long randomId = 999L;

        BDDMockito.when(service.findById(randomId)).thenThrow(new NotFoundException("Budget with id '%s' is not found".formatted(randomId)));

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/%s".formatted(randomId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/budgets/1/items returns all found budgetItems when successful")
    @Order(6)
    void findAllItemsByBudgetId_ReturnsAllFoundBudgetItems_WhenSuccessful() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("budget/get-budgetsItems-null-supplyDescription-by-budget-id-200.json");

        Budget budgetToSearch = BudgetUtils.newBudgetList().getFirst();
        Long budgetId = budgetToSearch.getId();

        BudgetItemGetResponse foundBudgetItem = BudgetItemUtils.newBudgetItemGetResponseList().getFirst();
        List<BudgetItemGetResponse> foundBudgetList = Collections.singletonList(foundBudgetItem);

        BDDMockito.when(budgetItemService.findAllByBudgetId(budgetId, null)).thenReturn(foundBudgetList);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/%s/items".formatted(budgetId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("/v1/budgets/1/items/1 returns an empty list when the budget does not have budgetItems")
    @Order(7)
    void findAllItemsByBudgetId_ReturnsEmptyList_WhenTheBudgetDoesNotHaveBudgetItems() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("budget/get-budgetsItems-by-budget-empty-id-200.json");

        Budget budgetToSearch = BudgetUtils.newBudgetList().getFirst();
        Long budgetId = budgetToSearch.getId();

        BDDMockito.when(budgetItemService.findAllByBudgetId(budgetId, null)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/%s/items".formatted(budgetId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("/v1/budgets/1/items?supplyDescription=Descrição%201 returns found budgetItems when the argument is given")
    @Order(8)
    void findAllItemsByBudgetId_ReturnsFoundBudgetItems_WhenTheArgumentIsGiven() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("budget/get-budgetsItems-descricao1-supplyDescription-by-budget-id-200.json");

        Budget budgetToSearch = BudgetUtils.newBudgetList().getFirst();
        Long budgetId = budgetToSearch.getId();

        BudgetItemGetResponse budgetItemToBeFound = BudgetItemUtils.newBudgetItemGetResponseList().getFirst();
        String supplyDescription = budgetItemToBeFound.getSupply().description();
        List<BudgetItemGetResponse> budgetItemToBeFoundList = Collections.singletonList(budgetItemToBeFound);

        BDDMockito.when(budgetItemService.findAllByBudgetId(budgetId, supplyDescription)).thenReturn(budgetItemToBeFoundList);

        mockMvc.perform(
                MockMvcRequestBuilders.get(URL + "/%s/items".formatted(budgetId))
                        .param("supplyDescription", supplyDescription)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("/v1/budgets/1/items?supplyDescription=Random%20Description returns an empty list when given budget has no supply with the given description")
    @Order(9)
    void findAllItemsByBudgetId_ReturnsEmptyList_WhenTheGivenBudgetHasNoSupplyWithTheGivenDescription() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("budget/get-budgetsItems-invalid-supplyDescription-by-budget-id-200.json");

        Budget budgetToSearch = BudgetUtils.newBudgetList().getFirst();
        Long budgetId = budgetToSearch.getId();

        String randomSupplyDescription = "Random Description";

        BDDMockito.when(budgetItemService.findAllByBudgetId(budgetId, randomSupplyDescription)).thenReturn(Collections.emptyList());

        mockMvc.perform(
                        MockMvcRequestBuilders.get(URL + "/%s/items".formatted(budgetId))
                                .param("supplyDescription", randomSupplyDescription)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));

    }

    @Test
    @DisplayName("/v1/budgets/999/items throws NotFoundException when the given budget id is not found")
    @Order(10)
    void findAllItemsByBudgetId_ThrowsNotFoundException_WhenTheGivenBudgetIdIsNotFound() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("budget/get-budgetsItems-by-budget-id-404.json");

        Long randomBudgetId = 999L;

        BDDMockito.when(budgetItemService.findAllByBudgetId(randomBudgetId, null))
                .thenThrow(new NotFoundException("Budget with id '%s' is not found".formatted(randomBudgetId)));

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/%s/items".formatted(randomBudgetId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("POST /v1/budgets returns saved budget when successful")
    @Order(11)
    void save_ReturnsSavedBudget_WhenSuccessful() throws Exception {
        String request = fileUtils.readResourceFile("budget/post-request-budget-200.json");
        String expectedResponse = fileUtils.readResourceFile("budget/post-response-budget-201.json");

        BudgetPostRequest postRequest = BudgetUtils.newBudgetPostRequest();
        BudgetPostResponse postResponse = BudgetUtils.newBudgetPostResponse();

        BDDMockito.when(service.save(postRequest)).thenReturn(postResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.post(URL)
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("POST /v1/budgets throws NotFoundException when the given client id is not found")
    @Order(12)
    void save_ThrowsNotFoundException_WhenTheGivenClientIdIsNotFound() throws Exception {
        String request = fileUtils.readResourceFile("budget/post-request-budget-invalid-clientId-200.json");
        String expectedResponse = fileUtils.readResourceFile("budget/post-response-budget-invalid-clientId-404.json");

        Long randomClientId = 999L;
        BudgetPostRequest postRequest = BudgetUtils.newBudgetPostRequest().withClientId(randomClientId);

        BDDMockito.when(service.save(postRequest)).thenThrow(new NotFoundException("Client with id '%s' is not found".formatted(randomClientId)));

        mockMvc.perform(
                        MockMvcRequestBuilders.post(URL)
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }



    @Test
    @DisplayName("POST /v1/budgets/1/items returns saved budgetItem when successful")
    @Order(13)
    void save_ReturnsSavedBudgetItem_WhenSuccessful() throws Exception {
        String request = fileUtils.readResourceFile("budget/post-request-budgetItem-200.json");
        String expectedResponse = fileUtils.readResourceFile("budget/post-response-budgetItem-201.json");

        BudgetGetResponse budget = budgetGetResponseList.getFirst();
        Long budgetId = budget.getId();

        BudgetItemPostRequest postRequest = BudgetItemUtils.newBudgetItemPostRequest();
        BudgetItemPostResponse postResponse = BudgetItemUtils.newBudgetItemPostResponse();

        BDDMockito.when(budgetItemService.save(budgetId, postRequest)).thenReturn(postResponse);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(URL + "/%s/items".formatted(budgetId))
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("POST /v1/budgets/1/items updates quantity and total value when the budget id and supply id are already registered")
    @Order(14)
    void save_UpdatesQuantityAndTotalValue_WhenTheBudgetIdAndSupplyIdAreAlreadyRegistered() throws Exception {
        String request = fileUtils.readResourceFile("budget/post-request-budgetItem-already-registered-200.json");
        String expectedResponse = fileUtils.readResourceFile("budget/post-response-budgetItem-already-registered-201.json");

        BudgetGetResponse budget = budgetGetResponseList.getFirst();
        Long budgetId = budget.getId();

        BudgetItemPostRequest postRequest = BudgetItemUtils.newBudgetItemAlreadyRegisteredPostRequest();
        BudgetItemPostResponse postResponse = BudgetItemUtils.newBudgetItemAlreadyRegisteredPostResponse();

        BDDMockito.when(budgetItemService.save(budgetId, postRequest)).thenReturn(postResponse);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(URL + "/%s/items".formatted(budgetId))
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("POST /v1/budgets/999/items returns throws NotFoundException when the given budget id is not found")
    @Order(15)
    void save_ThrowsNotFoundException_WhenTheGivenBudgetIdIsNotFound() throws Exception {
        String request = fileUtils.readResourceFile("budget/post-request-budgetItem-200.json");
        String expectedResponse = fileUtils.readResourceFile("budget/post-response-budgetItem-invalid-budgetId-404.json");

        Long randomBudgetId = 999L;

        BudgetItemPostRequest postRequest = BudgetItemUtils.newBudgetItemPostRequest();

        BDDMockito.when(budgetItemService.save(randomBudgetId, postRequest)).thenThrow(new NotFoundException("Budget with id '%s' is not found".formatted(randomBudgetId)));

        mockMvc.perform(
                        MockMvcRequestBuilders.post(URL + "/%s/items".formatted(randomBudgetId))
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("POST /v1/budgets/1/items returns throws NotFoundException when the given supply id is not found")
    @Order(16)
    void save_ThrowsNotFoundException_WhenTheGivenSupplyIdIsNotFound() throws Exception {
        String request = fileUtils.readResourceFile("budget/post-request-budgetItem-invalid-supplyId-200.json");
        String expectedResponse = fileUtils.readResourceFile("budget/post-request-budgetItem-invalid-supplyId-404.json");

        Budget budget = BudgetUtils.newBudgetList().getFirst();
        Long budgetId = budget.getId();

        Long randomSupplyId = 999L;
        BudgetItemPostRequest postRequest = BudgetItemUtils.newBudgetItemPostRequest().withSupplyId(randomSupplyId);

        BDDMockito.when(budgetItemService.save(budgetId, postRequest)).thenThrow(new NotFoundException("Supply with id '%s' is not found".formatted(randomSupplyId)));

        mockMvc.perform(
                        MockMvcRequestBuilders.post(URL + "/%s/items".formatted(budgetId))
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("PUT /v1/budgets/1 updates budget when successful")
    @Order(17)
    void update_UpdatesBudget_WhenSuccessful() throws Exception {
        String request = fileUtils.readResourceFile("budget/put-request-budget-200.json");

        BudgetPutRequest putRequest = BudgetUtils.newBudgetPutRequest();
        Long budgetId = putRequest.getId();

        BDDMockito.doNothing().when(service).update(budgetId, putRequest);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(URL + "/%s".formatted(budgetId))
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("PUT /v1/budgets/999 throws BadRequestException when the url id does not match request body id")
    @Order(18)
    void update_ThrowsBadRequestException_WhenTheUrlIdDoesNotMatchRequestBodyId() throws Exception {
        String request = fileUtils.readResourceFile("budget/put-request-budget-200.json");
        String expectedResponse = fileUtils.readResourceFile("budget/put-response-budget-ids-not-matching-200.json");

        BudgetPutRequest putRequest = BudgetUtils.newBudgetPutRequest();
        Long randomUrlId = 999L;

        BDDMockito.doThrow(new BadRequestException("The ID in the URL (%s) does not match the ID in the request body (%s)".formatted(randomUrlId, putRequest.getId())))
                .when(service).update(randomUrlId, putRequest);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(URL + "/%s".formatted(randomUrlId))
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("PUT /v1/budgets/999 throws NotFoundException when the given budget id is not found")
    @Order(19)
    void update_ThrowsNotFoundException_WhenTheGivenBudgetIdIsNotFound() throws Exception {
        String request = fileUtils.readResourceFile("budget/put-request-budget-invalid-id-200.json");
        String expectedResponse = fileUtils.readResourceFile("budget/put-response-budget-invalid-id-404.json");

        Long randomBudgetId = 999L;
        BudgetPutRequest putRequest = BudgetUtils.newBudgetPutRequest().withId(randomBudgetId);

        BDDMockito.doThrow(new NotFoundException("Budget with id '%s' is not found".formatted(randomBudgetId)))
                .when(service).update(randomBudgetId, putRequest);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(URL + "/%s".formatted(randomBudgetId))
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("PUT /v1/budgets/1 throws NotFoundException when the given client id is not found")
    @Order(20)
    void update_ThrowsNotFoundException_WhenTheGivenClientIdIsNotFound() throws Exception {
        String request = fileUtils.readResourceFile("budget/put-request-budget-invalid-clientId-200.json");
        String expectedResponse = fileUtils.readResourceFile("budget/put-response-budget-invalid-clientId-404.json");

        Long randomClientId = 999L;
        BudgetPutRequest putRequest = BudgetUtils.newBudgetPutRequest().withClientId(randomClientId);

        BDDMockito.doThrow(new NotFoundException("Client with id '%s' is not found".formatted(randomClientId)))
                .when(service).update(putRequest.getId(), putRequest);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(URL + "/%s".formatted(putRequest.getId()))
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("PUT /v1/budgets/1/items/1 updates budgetItem when successful")
    @Order(21)
    void updateBudgetItem_UpdatesBudgetItem_WhenSuccessful() throws Exception {
        String request = fileUtils.readResourceFile("budget/put-request-budgetItem-200.json");

        BudgetGetResponse budget = budgetGetResponseList.getFirst();
        Long budgetId = budget.getId();

        BudgetItemPutRequest putRequest = BudgetItemUtils.newBudgetItemPutRequest();
        Long budgetItemId = putRequest.getId();

        BDDMockito.doNothing().when(budgetItemService).update(budgetId, budgetItemId, putRequest);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(URL + "/%s/items/%s".formatted(budgetId, budgetItemId))
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("PUT /v1/budgets/1/items/999 throws BadRequestException when the url id does not match request body id")
    @Order(22)
    void updateBudgetItem_ThrowsBadRequestException_WhenTheUrlIdDoesNotMatchRequestBodyId() throws Exception {
        String request = fileUtils.readResourceFile("budget/put-request-budgetItem-200.json");
        String expectedResponse = fileUtils.readResourceFile("budget/put-response-budgetItem-ids-not-matching-200.json");

        BudgetGetResponse budget = budgetGetResponseList.getFirst();
        Long budgetId = budget.getId();

        BudgetItemPutRequest putRequest = BudgetItemUtils.newBudgetItemPutRequest();
        Long budgetItemId = putRequest.getId();

        Long randomUrlId = 999L;

        BDDMockito.doThrow(new BadRequestException("The ID in the URL (%s) does not match the ID in the request body (%s)".formatted(randomUrlId, putRequest.getId())))
                .when(budgetItemService).update(budgetId, budgetItemId, putRequest);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(URL + "/%s/items/%s".formatted(budgetId, budgetItemId))
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("PUT /v1/budgets/1/items/999 throws NotFoundException when the given budgetItem id is not found")
    @Order(23)
    void updateBudgetItem_ThrowsNotFoundException_WhenTheGivenBudgetItemIdIsNotFound() throws Exception {
        String request = fileUtils.readResourceFile("budget/put-request-budgetItem-invalid-id-200.json");
        String expectedResponse = fileUtils.readResourceFile("budget/put-response-budgetItem-invalid-id-404.json");

        BudgetGetResponse budget = budgetGetResponseList.getFirst();
        Long budgetId = budget.getId();

        long randomId = 999L;
        BudgetItemPutRequest putRequest = BudgetItemUtils.newBudgetItemPutRequest().withId(randomId);

        BDDMockito.doThrow(new NotFoundException("Item with id '%s' is not found".formatted(randomId)))
                .when(budgetItemService).update(budgetId, randomId, putRequest);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(URL + "/%s/items/%s".formatted(budgetId, randomId))
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("PUT /v1/budgets/999/items/1 throws NotFoundException when the given budget id is not found")
    @Order(24)
    void updateBudgetItem_ThrowsNotFoundException_WhenTheGivenBudgetIdIsNotFound() throws Exception {
        String request = fileUtils.readResourceFile("budget/put-request-budgetItem-200.json");
        String expectedResponse = fileUtils.readResourceFile("budget/put-response-budgetItem-invalid-budgetId-404.json");

        Long randomBudgetId = 999L;

        BudgetItemPutRequest putRequest = BudgetItemUtils.newBudgetItemPutRequest();

        BDDMockito.doThrow(new NotFoundException("Budget with id '%s' is not found".formatted(randomBudgetId)))
                .when(budgetItemService).update(randomBudgetId, putRequest.getId(), putRequest);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(URL + "/%s/items/%s".formatted(randomBudgetId, putRequest.getId()))
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("updateBudgetItem throws NotFoundException when the given supply id is not found")
    @Order(25)
    void updateBudgetItem_ThrowsNotFoundException_WhenTheGivenSupplyIdIsNotFound() throws Exception {
        String request = fileUtils.readResourceFile("budget/put-request-budgetItem-invalid-supplyId-200.json");
        String expectedResponse = fileUtils.readResourceFile("budget/put-response-budgetItem-invalid-supplyId-404.json");

        BudgetGetResponse budget = budgetGetResponseList.getFirst();
        Long budgetId = budget.getId();

        long randomSupplyId = 999L;
        BudgetItemPutRequest putRequest = BudgetItemUtils.newBudgetItemPutRequest().withSupplyId(randomSupplyId);

        BDDMockito.doThrow(new NotFoundException("Supply with id '%s' is not found".formatted(randomSupplyId)))
                .when(budgetItemService).update(budgetId, putRequest.getId(), putRequest);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(URL + "/%s/items/%s".formatted(budgetId, putRequest.getId()))
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("deleteById removes budget when successful")
    @Order(26)
    void deleteById_RemovesBudget_WhenSuccessful() throws Exception {
        BudgetGetResponse budgetToDelete = budgetGetResponseList.getFirst();
        Long idToDelete = budgetToDelete.getId();

        BDDMockito.doNothing().when(service).deleteById(idToDelete);

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/%s".formatted(idToDelete)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("deleteById throws NotFoundException when the given id is not found")
    @Order(27)
    void deleteById_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("budget/delete-budget-by-id-404.json");

        Long randomId = 999L;

        BDDMockito.doThrow(new NotFoundException("Budget with id '%s' is not found".formatted(randomId))).when(service).deleteById(randomId);

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/%s".formatted(randomId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("deleteBudgetItemById removes budgetItem when successful")
    @Order(28)
    void deleteBudgetItemById_RemovesBudgetItem_WhenSuccessful() throws Exception {
        BudgetGetResponse budget = budgetGetResponseList.getFirst();
        Long budgetId = budget.getId();

        BudgetItem budgetItemToDelete = BudgetItemUtils.newBudgetItemList().getFirst();
        Long idToDelete = budgetItemToDelete.getId();

        BDDMockito.doNothing().when(budgetItemService).deleteById(budgetId, idToDelete);

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/%s/items/%s".formatted(budgetId, idToDelete)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("deleteBudgetItemById throws NotFoundException when the given id is not found")
    @Order(29)
    void deleteBudgetItemById_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("budget/delete-budgetItem-by-id-404.json");

        BudgetGetResponse budget = budgetGetResponseList.getFirst();
        Long budgetId = budget.getId();

        Long randomId = 999L;

        BDDMockito.doThrow(new NotFoundException("Item with id '%s' is not found".formatted(randomId)))
                .when(budgetItemService).deleteById(budgetId, randomId);

        BDDMockito.doNothing().when(service).deleteById(randomId);

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/%s/items/%s".formatted(budgetId, randomId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("deleteBudgetItemById throws NotFoundException when the given budget id is not found")
    @Order(29)
    void deleteBudgetItemById_ThrowsNotFoundException_WhenTheGivenBudgetIdIsNotFound() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("budget/delete-budgetItem-by-id-invalid-budgetId-404.json");

        Long randomBudgetId = 999L;

        BudgetItem budgetItemToDelete = BudgetItemUtils.newBudgetItemList().getFirst();
        Long idToDelete = budgetItemToDelete.getId();

        BDDMockito.doThrow(new NotFoundException("Budget with id '%s' is not found".formatted(randomBudgetId)))
                .when(budgetItemService).deleteById(randomBudgetId, idToDelete);

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/%s/items/%s".formatted(randomBudgetId, idToDelete)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }
}