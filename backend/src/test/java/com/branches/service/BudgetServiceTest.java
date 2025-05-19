package com.branches.service;

import com.branches.mapper.BudgetMapper;
import com.branches.model.Budget;
import com.branches.repository.BudgetRepository;
import com.branches.request.BudgetPostRequest;
import com.branches.request.BudgetPutRequest;
import com.branches.response.BudgetGetResponse;
import com.branches.response.BudgetPostResponse;
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
class BudgetServiceTest {
    @InjectMocks
    private BudgetService service;
    @Mock
    private BudgetItemService budgetItemService;
    @Mock
    private BudgetRepository repository;
    @Mock
    private BudgetMapper mapper;
    private List<Budget> budgetList;
    private List<BudgetGetResponse> budgetGetResponseList;

    @BeforeEach
    void init() {
        budgetList = OrcamentoUtils.newOrcamentoList();
        budgetGetResponseList = OrcamentoUtils.newOrcamentoGetResponseList();
    }

    @Test
    @Order(1)
    @DisplayName("findAll return all orcamento when the given argument is null")
    void findAll_ReturnsAllOrcamento_WhenSuccessful() {
        List<BudgetGetResponse> expectedResponse = budgetGetResponseList;

        BDDMockito.when(repository.findAll()).thenReturn(budgetList);
        BDDMockito.when(mapper.toOrcamentoGetResponse(budgetList)).thenReturn(expectedResponse);

        List<BudgetGetResponse> response = service.findAll(null);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @DisplayName("findAll returns all objects found when the given argument is found")
    @Order(2)
    void findAll_ReturnsAllObjectsFound_WhenTheGivenArgumentIsFound() {
        BudgetGetResponse expectedBudgetGetResponse = budgetGetResponseList.get(0);
        String nameToBeSearched = expectedBudgetGetResponse.getNome();

        Budget expectedBudget = budgetList.get(0);

        List<BudgetGetResponse> expectedResponse = List.of(expectedBudgetGetResponse);
        BDDMockito.when(repository.findAllByNomeContaining(nameToBeSearched)).thenReturn(List.of(expectedBudget));
        BDDMockito.when(mapper.toOrcamentoGetResponse(List.of(expectedBudget))).thenReturn(expectedResponse);

        List<BudgetGetResponse> response = service.findAll(nameToBeSearched);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @DisplayName("findAll returns an empty list when the given argument is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenTheGivenArgumentIsNotFound() {
        String randomName = "XAxaKIlpan";

        BDDMockito.when(repository.findAllByNomeContaining(randomName)).thenReturn(Collections.emptyList());

        List<BudgetGetResponse> response = service.findAll(randomName);

        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("save returns created object when successful")
    void save_ReturnsCreatedObject_WhenSuccessful() {
        Budget budgetSaved = OrcamentoUtils.newOrcamentoToSaved();
        BudgetPostRequest orcamentoToBeSaved = OrcamentoUtils.newOrcamentoPostRequest();

        Budget budgetMapped = OrcamentoUtils.newOrcamentoToSaved();
        budgetMapped.setId(null);

        BudgetPostResponse expectedResponse = OrcamentoUtils.newOrcamentoPostResponse();

        BDDMockito.when(mapper.toOrcamento(orcamentoToBeSaved)).thenReturn(budgetMapped);
        BDDMockito.when(repository.save(budgetMapped)).thenReturn(budgetSaved);
        BDDMockito.when(mapper.toOrcamentoPostResponse(budgetSaved)).thenReturn(expectedResponse);

        BudgetPostResponse returnedOrcamento = service.save(orcamentoToBeSaved);

        Assertions.assertThat(returnedOrcamento)
                .isNotNull()
                .isEqualTo(expectedResponse);
    }

    @Test
    @Order(5)
    @DisplayName("findByIdOrElseThrowsNotFoundException returns object found when successful")
    void findByIdOrElseThrowNotFoundException_ReturnsObjectFound_WhenSuccessful() {
        Budget budgetExpected = budgetList.get(0);
        Long idToBeSearched = budgetExpected.getId();

        BDDMockito.when(repository.findById(idToBeSearched)).thenReturn(Optional.of(budgetExpected));

        Budget response = service.findByIdOrElseThrowNotFoundException(idToBeSearched);

        Assertions.assertThat(response)
                .isNotNull()
                .isEqualTo(budgetExpected);
    }

    @Test
    @Order(6)
    @DisplayName("findByIdOrElseThrowsNotFoundException throws not found exception when orcamentos doesn't exists")
    void findByIdOrElseThrowNotFoundException_ThrowsNotFoundException_WhenOrcamentoDoesNotExists() {
        Long randomId = 112099L;
        BDDMockito.when(repository.findById(randomId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.findByIdOrElseThrowNotFoundException(randomId))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Orcamento Not Found");
    }

    @Test
    @DisplayName("update updates orcamento when successful")
    @Order(7)
    void update_UpdatesOrcamento_WhenSuccessful() {
        Budget currentBudget = OrcamentoUtils.newOrcamentoSaved();
        BudgetPutRequest orcamentoToBeUpdated = OrcamentoUtils.newOrcamentoPutRequest();

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(currentBudget));

        orcamentoToBeUpdated.setNome("Nome novo");

        Assertions.assertThatNoException().isThrownBy(() -> service.update(orcamentoToBeUpdated));
    }

    @Test
    @DisplayName("update throws not found exception when orcamento doesn't exists")
    @Order(8)
    void update_ThrowsNotFoundException_WhenOrcamentoDoesNotExists() {
        BudgetPutRequest orcamentoToBeUpdated = OrcamentoUtils.newOrcamentoPutRequest();

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        orcamentoToBeUpdated.setNome("Nome novo");

        Assertions.assertThatThrownBy(() -> service.update(orcamentoToBeUpdated))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Orcamento Not Found");
    }

    @Test
    @DisplayName("deleteById deletes orcamento when successful")
    @Order(9)
    void deleteById_DeletesOrcamento_WhenSuccessful() {
        Budget budgetToBeDeleted = budgetList.get(0);
        Long orcamentoToBeDeletedId = budgetToBeDeleted.getId();

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(budgetToBeDeleted));

        Assertions.assertThatNoException().isThrownBy(() -> service.deleteById(orcamentoToBeDeletedId));
    }

    @Test
    @DisplayName("deleteById throws not found exception when orcamento doesn't exists")
    @Order(10)
    void deleteById_ThrowsNotFoundException_WhenOrcamentoDoesNotExists() {
        Long randomId = 13131L;

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.deleteById(randomId))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Orcamento Not Found");
    }
}