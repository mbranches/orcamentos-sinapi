package com.branches.controller;

import com.branches.request.SupplyPostRequest;
import com.branches.response.SupplyGetResponse;
import com.branches.response.SupplyPostResponse;
import com.branches.service.SupplyService;
import com.branches.utils.FileUtils;
import com.branches.utils.SupplyUtils;
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

@WebMvcTest(SupplyController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(FileUtils.class)
class SupplyControllerTest {
    private final String URL = "/v1/supplies";
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private SupplyService service;
    @Autowired
    private FileUtils fileUtils;
    private List<SupplyGetResponse> supplyGetResponseList;

    @BeforeEach
    void init() {
        supplyGetResponseList = SupplyUtils.newSupplyGetResponseList();
    }

    @Test
    @DisplayName("GET /v1/supplies returns all supplies when successful")
    @Order(1)
    void findAll_ReturnsAllSupplies_WhenSuccessful() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("supply/get-supplies-null-description.json");

        BDDMockito.when(service.findAll(null)).thenReturn(supplyGetResponseList);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/supplies?description=Descrição%201 returns found supplies when the argument is given")
    @Order(2)
    void findAll_ReturnsFoundSupplies_WhenTheArgumentIsGiven() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("supply/get-supplies-descricao1-description.json");

        SupplyGetResponse supplyToBeFound = supplyGetResponseList.getFirst();
        String descriptionToSearch = supplyToBeFound.getDescription();

        List<SupplyGetResponse> response = Collections.singletonList(supplyToBeFound);

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
    @DisplayName("GET /v1/supplies?description=Random%20Description returns an empty list when the given description is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenTheGivenDescriptionIsNotFound() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("supply/get-supplies-invalid-description.json");

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
    @DisplayName("POST /v1/supplies returns all saved supplies when successful")
    @Order(4)
    void saveAll_ReturnsAllSavedSupplies_WhenSuccessful() throws Exception {
        String request = fileUtils.readResourceFile("supply/post-request-supplies-200.json");
        String expectedResponse = fileUtils.readResourceFile("supply/post-response-supplies-201.json");

        List<SupplyPostRequest> postRequestList = Collections.singletonList(SupplyUtils.newSupplyPostRequest());
        List<SupplyPostResponse> postResponseList = Collections.singletonList(SupplyUtils.newSupplyPostResponse());

        BDDMockito.when(service.saveAll(postRequestList)).thenReturn(postResponseList);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(URL)
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }
}