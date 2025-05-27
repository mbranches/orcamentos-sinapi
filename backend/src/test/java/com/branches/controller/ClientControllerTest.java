package com.branches.controller;

import com.branches.exception.BadRequestException;
import com.branches.exception.NotFoundException;
import com.branches.request.ClientPostRequest;
import com.branches.request.ClientPutRequest;
import com.branches.response.ClientGetResponse;
import com.branches.response.ClientPostResponse;
import com.branches.service.ClientService;
import com.branches.utils.ClientUtils;
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

@WebMvcTest(ClientController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(FileUtils.class)
public class ClientControllerTest {
    private final String URL = "/v1/clients";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private FileUtils fileUtils;
    @MockitoBean
    private ClientService service;
    private List<ClientGetResponse> clientGetResponseList;

    @BeforeEach
    void init() {
        clientGetResponseList = ClientUtils.newClientGetResponseList();
    }

    @Test
    @DisplayName("GET /v1/clients returns all clientes when successful")
    @Order(1)
    void findAll_ReturnsAllClientes_WhenSuccessful() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("client/get-clients-null-name.json");

        BDDMockito.when(service.findAll(null)).thenReturn(clientGetResponseList);

        mockMvc.perform(MockMvcRequestBuilders.get(URL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/clients?name=Marcus%20Branches returns found clientes when the argument is given")
    @Order(2)
    void findAll_ReturnsFoundClientes_WhenTheGivenNameIsFound() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("client/get-clients-marcusBranches-name.json");

        ClientGetResponse clientToBeFound = clientGetResponseList.getFirst();
        String nameToSearch = clientToBeFound.getName();

        List<ClientGetResponse> response = Collections.singletonList(clientToBeFound);

        BDDMockito.when(service.findAll(nameToSearch)).thenReturn(response);

        mockMvc.perform(
                        MockMvcRequestBuilders.get(URL)
                                .param("name", nameToSearch)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/clients?name=Random%20Name returns an empty list when the given name is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenTheGivenNameIsNotFound() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("client/get-clients-invalid-name.json");

        String randomName = "Random Name";

        BDDMockito.when(service.findAll(randomName)).thenReturn(Collections.emptyList());

        mockMvc.perform(
                        MockMvcRequestBuilders.get(URL)
                                .param("name", randomName)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("POST /v1/clients returns saved client when successful")
    @Order(4)
    void save_ReturnsSavedClient_WhenSuccessful() throws Exception {
        String request = fileUtils.readResourceFile("client/post-request-client-200.json");
        String expectedResponse = fileUtils.readResourceFile("client/post-response-client-201.json");

        ClientPostRequest postRequest = ClientUtils.newClientPostRequest();
        ClientPostResponse postResponse = ClientUtils.newClientPostResponse();

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
    @DisplayName("GET /v1/clients/1 returns found client when successful")
    @Order(5)
    void findById_ReturnsFoundClient_WhenSuccessful() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("client/get-client-by-id-200.json");

        ClientGetResponse clientToBeFound = clientGetResponseList.getFirst();
        Long idToSearch = clientToBeFound.getId();

        BDDMockito.when(service.findById(idToSearch)).thenReturn(clientToBeFound);

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/%s".formatted(idToSearch)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("GET /v1/clients/999 throws NotFoundException when the given id is not found")
    @Order(6)
    void findById_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("client/get-client-by-id-404.json");

        Long randomId = 999L;

        BDDMockito.when(service.findById(randomId)).thenThrow(new NotFoundException("Client with id '%s' is not found".formatted(randomId)));

        mockMvc.perform(MockMvcRequestBuilders.get(URL + "/%s".formatted(randomId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("DELETE /v1/clients/1 removes client when successful")
    @Order(7)
    void deleteById_RemovesClient_WhenSuccessful() throws Exception {
        ClientGetResponse clientToDelete = clientGetResponseList.getFirst();
        Long idToDelete = clientToDelete.getId();

        BDDMockito.doNothing().when(service).deleteById(idToDelete);

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/%s".formatted(idToDelete)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /v1/clients/999 throws NotFoundException when the given id is not found")
    @Order(8)
    void deleteById_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() throws Exception {
        String expectedResponse = fileUtils.readResourceFile("client/delete-client-by-id-404.json");

        Long randomClientId = 999L;

        BDDMockito.doThrow(new NotFoundException("Client with id '%s' is not found".formatted(randomClientId)))
                .when(service).deleteById(randomClientId);

        mockMvc.perform(MockMvcRequestBuilders.delete(URL + "/%s".formatted(randomClientId)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("PUT /v1/clients/1 updates client when successful")
    @Order(9)
    void update_UpdatesClient_WhenSuccessful() throws Exception {
        String request = fileUtils.readResourceFile("client/put-request-client-200.json");

        ClientPutRequest putRequest = ClientUtils.newClientPutRequest();
        Long clientId = putRequest.getId();

        BDDMockito.doNothing().when(service).update(clientId, putRequest);

        mockMvc.perform(MockMvcRequestBuilders.put(
                URL + "/%s".formatted(clientId))
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("PUT /v1/clients/999 throws BadRequestException when the url id does not match request body id")
    @Order(10)
    void update_ThrowsBadRequestException_WhenTheUrlIdDoesNotMatchRequestBodyId() throws Exception {
        String request = fileUtils.readResourceFile("client/put-request-client-200.json");
        String expectedResponse = fileUtils.readResourceFile("client/put-response-not-match-ids-client-400.json");

        ClientPutRequest putRequest = ClientUtils.newClientPutRequest();

        Long randomUrlId = 999L;

        BDDMockito.doThrow(new BadRequestException("The ID in the URL (%s) does not match the ID in the request body (%s)".formatted(randomUrlId, putRequest.getId())))
                .when(service).update(randomUrlId, putRequest);

        mockMvc.perform(MockMvcRequestBuilders.put(
                                URL + "/%s".formatted(randomUrlId))
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }

    @Test
    @DisplayName("PUT /v1/clients/999 throws NotFoundException when the given client id is not found")
    @Order(11)
    void update_ThrowsNotFoundException_WhenTheGivenClientIdIsNotFound() throws Exception {
        String request = fileUtils.readResourceFile("client/put-request-client-invalid-id-200.json");
        String expectedResponse = fileUtils.readResourceFile("client/put-response-invalid-id-404.json");

        Long randomId = 999L;
        ClientPutRequest putRequest = ClientUtils.newClientPutRequest().withId(randomId);


        BDDMockito.doThrow(new NotFoundException("Client with id '%s' is not found".formatted(randomId)))
                .when(service).update(randomId, putRequest);

        mockMvc.perform(MockMvcRequestBuilders.put(
                                URL + "/%s".formatted(randomId))
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(expectedResponse));
    }
}
