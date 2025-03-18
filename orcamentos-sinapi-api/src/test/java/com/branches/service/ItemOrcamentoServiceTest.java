package com.branches.service;

import com.branches.model.ItemOrcamento;
import com.branches.model.Orcamento;
import com.branches.repository.ItemOrcamentoRepository;
import com.branches.utils.InsumoCreator;
import com.branches.utils.OrcamentoCreator;
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

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItemOrcamentoServiceTest {
    @InjectMocks
    private ItemOrcamentoService service;
    @Mock
    private ItemOrcamentoRepository repository;
    @Mock
    private OrcamentoService orcamentoService;

    private final List<ItemOrcamento> ITEMS_ORCAMENTO_LIST = new ArrayList<>();

    @BeforeEach
    void init() {
        ItemOrcamento item1 = ItemOrcamento.builder()
                .id(1L)
                .insumo(InsumoCreator.createsInsumo())
                .quantidade(1D)
                .orcamento(OrcamentoCreator.createsOrcamento())
                .build();

        ItemOrcamento item2 = ItemOrcamento.builder()
                .id(2L)
                .insumo(InsumoCreator.createsInsumo())
                .quantidade(3D)
                .orcamento(OrcamentoCreator.createsOrcamento())
                .build();

        ItemOrcamento item3 = ItemOrcamento.builder()
                .id(3L)
                .insumo(InsumoCreator.createsInsumo())
                .quantidade(89D)
                .orcamento(OrcamentoCreator.createsOrcamento())
                .build();

        ITEMS_ORCAMENTO_LIST.addAll(List.of(item1, item2, item3));

    }

    @Test
    @Order(1)
    @DisplayName("saveAll return all object saved when successful")
    void saveAll_ReturnsAllObjectsSaved_whenSuccessful() {
        BDDMockito.when(repository.saveAll(ArgumentMatchers.anyList())).thenReturn(ITEMS_ORCAMENTO_LIST);
        Orcamento ownerOrcamento = OrcamentoCreator.createsOrcamento();

        ItemOrcamento item1 = ItemOrcamento.builder()
                .insumo(InsumoCreator.createsInsumo())
                .quantidade(1D)
                .orcamento(ownerOrcamento)
                .build();

        ItemOrcamento item2 = ItemOrcamento.builder()
                .insumo(InsumoCreator.createsInsumo())
                .quantidade(3D)
                .orcamento(ownerOrcamento)
                .build();

        ItemOrcamento item3 = ItemOrcamento.builder()
                .insumo(InsumoCreator.createsInsumo())
                .quantidade(89D)
                .orcamento(ownerOrcamento)
                .build();

        List<ItemOrcamento> itemsSaved = service.saveAll(List.of(item1, item2, item3));

        Assertions.assertThat(itemsSaved)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(this.ITEMS_ORCAMENTO_LIST);
    }

    @Test
    @DisplayName("findAll returns all items when successful")
    @Order(2)
    void findAll_ReturnsAllItems_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(ITEMS_ORCAMENTO_LIST);
        List<ItemOrcamento> response = service.findAll();

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(this.ITEMS_ORCAMENTO_LIST);
    }

    @Test
    @DisplayName("findByOrcamento returns all items of the orcamento submitted when successul")
    @Order(3)
    void findByOrcamento_ReturnsAllItemsOfTheOrcamentoSubmitted_WhenSuccessful() {
        BDDMockito.when(repository.findAllByOrcamentoId(ArgumentMatchers.anyLong())).thenReturn(ITEMS_ORCAMENTO_LIST);
        Orcamento orcamentoToBeSubmitted = OrcamentoCreator.createsOrcamento();
        Long idToBeSubmitted = orcamentoToBeSubmitted.getId();

        List<ItemOrcamento> response = service.findByOrcamento(idToBeSubmitted);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(this.ITEMS_ORCAMENTO_LIST);
    }

    @Test
    @DisplayName("findByOrcamento returns an empty list when orcamento doesn't contains items")
    @Order(3)
    void findByOrcamento_ReturnsAnEmptyList_WhenOrcamentoDoesNotContainsItems() {
        BDDMockito.when(repository.findAllByOrcamentoId(ArgumentMatchers.anyLong())).thenReturn(Collections.emptyList());

        Orcamento orcamentoToBeSubmitted = OrcamentoCreator.createsOrcamento();
        Long idToBeSubmitted = orcamentoToBeSubmitted.getId();

        List<ItemOrcamento> response = service.findByOrcamento(idToBeSubmitted);

        Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findByOrcamento throws not found exception when orcamento doesn't exists")
    @Order(4)
    void findByOrcamento_ThrowsNotFoundException_WhenOrcamentoDesNotExists() {
        BDDMockito.when(orcamentoService.findByIdOrElseThrowNotFoundException(ArgumentMatchers.anyLong()))
                .thenThrow(ResponseStatusException.class);

        Long randomId = 8881L;

        Assertions.assertThatThrownBy(() -> service.findByOrcamento(randomId))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    @DisplayName("deleteByOrcamentoId removes all items of the orcamento submitted when successful")
    @Order(5)
    void deleteByOrcamentoId_RemovesAllItemsOfTheOrcamentoSubmitted_WhenSuccessful() {
        Orcamento orcamentoToBeSubmitted = OrcamentoCreator.createsOrcamento();
        Long idToBeSubmitted = orcamentoToBeSubmitted.getId();

        BDDMockito.doNothing().when(repository).deleteByOrcamentoId(idToBeSubmitted);
        Assertions.assertThatNoException().isThrownBy(() -> service.deleteByOrcamentoId(idToBeSubmitted));
    }

    @Test
    @DisplayName("deleteByOrcamentoId Throws Not Found Exception when orcamento doesn't exists")
    @Order(6)
    void deleteByOrcamentoId_ThrowsNotFoundException_WhenOrcamentoDoesNotExists() {
        BDDMockito.when(orcamentoService.findByIdOrElseThrowNotFoundException(ArgumentMatchers.anyLong()))
                .thenThrow(ResponseStatusException.class);

        Long randomId = 8881L;

        Assertions.assertThatThrownBy(() -> service.deleteByOrcamentoId(randomId))
                .isInstanceOf(ResponseStatusException.class);
    }
}