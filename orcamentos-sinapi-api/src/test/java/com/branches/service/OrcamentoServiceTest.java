package com.branches.service;

import com.branches.mapper.OrcamentoMapper;
import com.branches.model.Orcamento;
import com.branches.repository.OrcamentoRepository;
import com.branches.request.OrcamentoPostRequest;
import com.branches.request.OrcamentoPutRequest;
import com.branches.response.OrcamentoGetResponse;
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
class OrcamentoServiceTest {
    @InjectMocks
    private OrcamentoService service;
    @Mock
    private ItemOrcamentoService itemOrcamentoService;
    @Mock
    private OrcamentoRepository repository;
    @Mock
    private OrcamentoMapper mapper;
    private List<Orcamento> orcamentoList;
    private List<OrcamentoGetResponse> orcamentoGetResponseList;

    @BeforeEach
    void init() {
        orcamentoList = OrcamentoUtils.newOrcamentoList();
        orcamentoGetResponseList = OrcamentoUtils.newOrcamentoGetResponseList();
    }

    @Test
    @Order(1)
    @DisplayName("findAll return all orcamento when the given argument is null")
    void findAll_ReturnsAllOrcamento_WhenSuccessful() {
        List<OrcamentoGetResponse> expectedResponse = orcamentoGetResponseList;

        BDDMockito.when(repository.findAll()).thenReturn(orcamentoList);
        BDDMockito.when(mapper.toOrcamentoGetResponse(orcamentoList)).thenReturn(expectedResponse);

        List<OrcamentoGetResponse> response = service.findAll(null);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @DisplayName("findAll returns all objects found when the given argument is found")
    @Order(2)
    void findAll_ReturnsAllObjectsFound_WhenTheGivenArgumentIsFound() {
        OrcamentoGetResponse expectedOrcamentoGetResponse = orcamentoGetResponseList.get(0);
        String nameToBeSearched = expectedOrcamentoGetResponse.getNome();

        Orcamento expectedOrcamento = orcamentoList.get(0);

        List<OrcamentoGetResponse> expectedResponse = List.of(expectedOrcamentoGetResponse);
        BDDMockito.when(repository.findAllByNomeContaining(nameToBeSearched)).thenReturn(List.of(expectedOrcamento));
        BDDMockito.when(mapper.toOrcamentoGetResponse(List.of(expectedOrcamento))).thenReturn(expectedResponse);

        List<OrcamentoGetResponse> response = service.findAll(nameToBeSearched);

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

        List<OrcamentoGetResponse> response = service.findAll(randomName);

        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @Order(4)
    @DisplayName("save returns created object when successful")
    void save_ReturnsCreatedObject_WhenSuccessful() {
        Orcamento orcamentoExpected = OrcamentoUtils.newOrcamentoToSaved();
        OrcamentoPostRequest orcamentoToBeSaved = OrcamentoUtils.newOrcamentoPostRequest();
        Orcamento orcamentoMapped = OrcamentoUtils.newOrcamentoSaved();
        orcamentoMapped.setId(null);

        BDDMockito.when(repository.save(ArgumentMatchers.any(Orcamento.class))).thenReturn(orcamentoExpected);
        BDDMockito.when(mapper.toOrcamento(orcamentoToBeSaved)).thenReturn(orcamentoMapped);

        Orcamento returnedOrcamento = service.save(orcamentoToBeSaved);

        Assertions.assertThat(returnedOrcamento)
                .isNotNull()
                .isEqualTo(orcamentoExpected);
    }

    @Test
    @Order(5)
    @DisplayName("findByIdOrElseThrowsNotFoundException returns object found when successful")
    void findByIdOrElseThrowNotFoundException_ReturnsObjectFound_WhenSuccessful() {
        Orcamento orcamentoExpected = orcamentoList.get(0);
        Long idToBeSearched = orcamentoExpected.getId();

        BDDMockito.when(repository.findById(idToBeSearched)).thenReturn(Optional.of(orcamentoExpected));

        Orcamento response = service.findByIdOrElseThrowNotFoundException(idToBeSearched);

        Assertions.assertThat(response)
                .isNotNull()
                .isEqualTo(orcamentoExpected);
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
        Orcamento currentOrcamento = OrcamentoUtils.newOrcamentoSaved();
        OrcamentoPutRequest orcamentoToBeUpdated = OrcamentoUtils.newOrcamentoPutRequest();

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(currentOrcamento));

        orcamentoToBeUpdated.setNome("Nome novo");

        Assertions.assertThatNoException().isThrownBy(() -> service.update(orcamentoToBeUpdated));
    }

    @Test
    @DisplayName("update throws not found exception when orcamento doesn't exists")
    @Order(8)
    void update_ThrowsNotFoundException_WhenOrcamentoDoesNotExists() {
        OrcamentoPutRequest orcamentoToBeUpdated = OrcamentoUtils.newOrcamentoPutRequest();

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        orcamentoToBeUpdated.setNome("Nome novo");

        Assertions.assertThatThrownBy(() -> service.update(orcamentoToBeUpdated))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Orcamento Not Found");
    }

    @Test
    @DisplayName("delete deletes orcamento when successful")
    @Order(9)
    void delete_DeletesOrcamento_WhenSuccessful() {
        Orcamento orcamentoToBeDeleted = orcamentoList.get(0);
        Long orcamentoToBeDeletedId = orcamentoToBeDeleted.getId();

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(orcamentoToBeDeleted));
        BDDMockito.doNothing().when(itemOrcamentoService).deleteByOrcamentoId(orcamentoToBeDeletedId);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(orcamentoToBeDeletedId));
    }

    @Test
    @DisplayName("delete throws not found exception when orcamento doesn't exists")
    @Order(10)
    void delete_ThrowsNotFoundException_WhenOrcamentoDoesNotExists() {
        Long randomId = 13131L;

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.delete(randomId))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Orcamento Not Found");
    }
}