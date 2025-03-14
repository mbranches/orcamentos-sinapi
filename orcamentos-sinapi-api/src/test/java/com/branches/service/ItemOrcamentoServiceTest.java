package com.branches.service;

import com.branches.mapper.ItemOrcamentoMapper;
import com.branches.model.ItemOrcamento;
import com.branches.repository.ItemOrcamentoRepository;
import com.branches.request.ItemOrcamentoPostRequest;
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

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ItemOrcamentoServiceTest {
//    @InjectMocks
//    private ItemOrcamentoService service;
//    @Mock
//    private ItemOrcamentoRepository repository;
//    @Mock
//    private ItemOrcamentoMapper mapper;
//
//    private final List<ItemOrcamento> ITEMS_ORCAMENTO_LIST = new ArrayList<>();

//    @BeforeEach
//    void init() {
//        ItemOrcamento item1 = ItemOrcamento.builder()
//                .id(1L)
//                .insumo(InsumoCreator.createsInsumo())
//                .quantidade(1)
//                .orcamento(OrcamentoCreator.createsOrcamento())
//                .build();
//
//        ItemOrcamento item2 = ItemOrcamento.builder()
//                .id(2L)
//                .insumo(InsumoCreator.createsInsumo())
//                .quantidade(3)
//                .orcamento(OrcamentoCreator.createsOrcamento())
//                .build();
//
//        ItemOrcamento item3 = ItemOrcamento.builder()
//                .id(3L)
//                .insumo(InsumoCreator.createsInsumo())
//                .quantidade(89)
//                .orcamento(OrcamentoCreator.createsOrcamento())
//                .build();
//
//        ITEMS_ORCAMENTO_LIST.addAll(List.of(item1, item2, item3));
//
//        ItemOrcamento itemMapped1 = ItemOrcamento.builder()
//                .insumo(InsumoCreator.createsInsumo())
//                .quantidade(1)
//                .orcamento(OrcamentoCreator.createsOrcamento())
//                .build();
//
//        ItemOrcamento itemMapped2 = ItemOrcamento.builder()
//                .insumo(InsumoCreator.createsInsumo())
//                .quantidade(3)
//                .orcamento(OrcamentoCreator.createsOrcamento())
//                .build();
//
//        ItemOrcamento itemMapped3 = ItemOrcamento.builder()
//                .insumo(InsumoCreator.createsInsumo())
//                .quantidade(89)
//                .orcamento(OrcamentoCreator.createsOrcamento())
//                .build();
//
//        BDDMockito.when(repository.saveAll(ArgumentMatchers.anyList())).thenReturn(ITEMS_ORCAMENTO_LIST);
//        BDDMockito.when(mapper.toItemOrcamentoList(ArgumentMatchers.anyList())).thenReturn(List.of(itemMapped1, itemMapped2, itemMapped3));
//    }
//
//    @Test
//    @DisplayName("saveAll return all object saved when successful")
//    void saveAll_ReturnsAllObjectsSaved_whenSuccessful() {
//        ItemOrcamentoPostRequest item1 = ItemOrcamentoPostRequest.builder()
//                .insumo(InsumoCreator.createsInsumo())
//                .quantidade(1)
//                .orcamento(OrcamentoCreator.createsOrcamento())
//                .build();
//
//        ItemOrcamentoPostRequest item2 = ItemOrcamentoPostRequest.builder()
//                .insumo(InsumoCreator.createsInsumo())
//                .quantidade(3)
//                .orcamento(OrcamentoCreator.createsOrcamento())
//                .build();
//
//        ItemOrcamentoPostRequest item3 = ItemOrcamentoPostRequest.builder()
//                .insumo(InsumoCreator.createsInsumo())
//                .quantidade(89)
//                .orcamento(OrcamentoCreator.createsOrcamento())
//                .build();
//
//        List<ItemOrcamento> itemsSaved = service.saveAll(List.of(item1, item2, item3));
//
//        Assertions.assertThat(itemsSaved)
//                .isNotNull()
//                .isNotEmpty()
//                .containsExactlyElementsOf(this.ITEMS_ORCAMENTO_LIST);
//    }
}