package com.branches.service;

import com.branches.mapper.OrcamentoMapper;
import com.branches.model.Orcamento;
import com.branches.repository.OrcamentoRepository;
import com.branches.request.OrcamentoPostRequest;
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
class OrcamentoServiceTest {
    @InjectMocks
    private OrcamentoService service;
    @Mock
    private ItemOrcamentoService itemOrcamentoService;
    @Mock
    private OrcamentoRepository repository;
    @Mock
    private OrcamentoMapper mapper;

    private final List<Orcamento> ORCAMENTO_LIST = new ArrayList<>();

    @BeforeEach
    void init() {
        ORCAMENTO_LIST.addAll(OrcamentoUtils.createsOrcamentoList());
    }

    @Test
    @Order(1)
    @DisplayName("findAll return all orcamento when successful")
    void findAll_ReturnsAllOrcamento_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(ORCAMENTO_LIST);

        List<Orcamento> response = service.findAll();

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(ORCAMENTO_LIST);
    }

    @Test
    @Order(2)
    @DisplayName("save returns created object when successful")
    void save_ReturnsCreatedObject_WhenSuccessful() {
        Orcamento orcamentoExpected = OrcamentoUtils.createsOrcamento();
        OrcamentoPostRequest orcamentoToBeSaved = OrcamentoPostRequest.builder().nome("Orçamento 1").build();
        Orcamento orcamentoMapped = OrcamentoUtils.createsOrcamento();
        orcamentoMapped.setId(null);

        BDDMockito.when(repository.save(ArgumentMatchers.any(Orcamento.class))).thenReturn(orcamentoExpected);
        BDDMockito.when(mapper.toOrcamento(orcamentoToBeSaved)).thenReturn(orcamentoMapped);

        Orcamento returnedOrcamento = service.save(orcamentoToBeSaved);

        Assertions.assertThat(returnedOrcamento)
                .isNotNull()
                .isEqualTo(orcamentoExpected);
    }

    @Test
    @Order(3)
    @DisplayName("findByIdOrElseThrowsNotFoundException returns object found when successful")
    void findByIdOrElseThrowNotFoundException_ReturnsObjectFound_WhenSuccessful() {
        Orcamento orcamentoExpected = ORCAMENTO_LIST.get(0);
        Long idToBeSearched = orcamentoExpected.getId();

        BDDMockito.when(repository.findById(idToBeSearched)).thenReturn(Optional.of(orcamentoExpected));

        Orcamento response = service.findByIdOrElseThrowNotFoundException(idToBeSearched);

        Assertions.assertThat(response)
                .isNotNull()
                .isEqualTo(orcamentoExpected);
    }

    @Test
    @Order(4)
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
    @Order(5)
    void update_UpdatesOrcamento_WhenSuccessful() {
        Orcamento orcamentoToBeUpdated = ORCAMENTO_LIST.get(0);

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(orcamentoToBeUpdated));

        orcamentoToBeUpdated.setNome("Nome novo");

        Assertions.assertThatNoException().isThrownBy(() -> service.update(orcamentoToBeUpdated));
    }

    @Test
    @DisplayName("update throws not found exception when orcamento doesn't exists")
    @Order(6)
    void update_ThrowsNotFoundException_WhenOrcamentoDoesNotExists() {
        Orcamento orcamentoToBeUpdated = ORCAMENTO_LIST.get(0);

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        orcamentoToBeUpdated.setNome("Nome novo");

        Assertions.assertThatThrownBy(() -> service.update(orcamentoToBeUpdated))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Orcamento Not Found");
    }

    @Test
    @DisplayName("delete deletes orcamento when successful")
    @Order(7)
    void delete_DeletesOrcamento_WhenSuccessful() {
        Orcamento orcamentoToBeDeleted = ORCAMENTO_LIST.get(0);
        Long orcamentoToBeDeletedId = orcamentoToBeDeleted.getId();

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(orcamentoToBeDeleted));
        BDDMockito.doNothing().when(itemOrcamentoService).deleteByOrcamentoId(orcamentoToBeDeletedId);

        Assertions.assertThatNoException().isThrownBy(() -> service.delete(orcamentoToBeDeletedId));
    }

    @Test
    @DisplayName("delete throws not found exception when orcamento doesn't exists")
    @Order(8)
    void delete_ThrowsNotFoundException_WhenOrcamentoDoesNotExists() {
        Long randomId = 13131L;

        BDDMockito.when(repository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.delete(randomId))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Orcamento Not Found");
    }

    @Test
    @DisplayName("findAllByName returns all objects found when successful")
    @Order(9)
    void findAllByName_ReturnsAllObjectsFound_WhenSuccessful() {
        Orcamento expectedOrcamento = ORCAMENTO_LIST.get(0);
        String nameToBeSearched = expectedOrcamento.getNome();

        List<Orcamento> expectedResponse = List.of(expectedOrcamento);
        BDDMockito.when(repository.findAllByNomeContaining(nameToBeSearched)).thenReturn(expectedResponse);


        List<Orcamento> response = service.findAllByName(nameToBeSearched);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @DisplayName("findAllByName returns an empty list when name is not found")
    @Order(9)
    void findAllByName_ReturnsEmptyList_WhenNameIsNotFound() {
        String randomName = "XAxaKIlpan";

        BDDMockito.when(repository.findAllByNomeContaining(randomName)).thenReturn(Collections.emptyList());

        List<Orcamento> response = service.findAllByName(randomName);

        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }
}