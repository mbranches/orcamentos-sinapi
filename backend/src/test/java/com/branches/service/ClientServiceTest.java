package com.branches.service;

import com.branches.exception.BadRequestException;
import com.branches.exception.NotFoundException;
import com.branches.mapper.ClientMapper;
import com.branches.model.Budget;
import com.branches.model.Client;
import com.branches.model.Client;
import com.branches.repository.ClientRepository;
import com.branches.request.BudgetPutRequest;
import com.branches.request.ClientPostRequest;
import com.branches.request.ClientPutRequest;
import com.branches.response.ClientGetResponse;
import com.branches.response.ClientPostResponse;
import com.branches.utils.BudgetUtils;
import com.branches.utils.ClientUtils;
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
class ClientServiceTest {
    @InjectMocks
    private ClientService service;
    @Mock
    private ClientRepository repository;
    @Mock
    private ClientMapper mapper;
    private List<Client> clientList;
    private List<ClientGetResponse> clientGetResponseList;

    @BeforeEach
    void init() {
        clientList = ClientUtils.newClientList();
        clientGetResponseList = ClientUtils.newClientGetResponseList();
    }

    @Test
    @DisplayName("findAll returns all clientes when successful")
    @Order(1)
    void findAll_ReturnsAllClientes_WhenSuccessful() {
        BDDMockito.when(repository.findAll()).thenReturn(clientList);
        BDDMockito.when(mapper.toClientGetResponseList(clientList)).thenReturn(clientGetResponseList);

        List<ClientGetResponse> response = service.findAll(null);

        org.assertj.core.api.Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(clientGetResponseList);

    }

    @Test
    @DisplayName("findAll returns found clientes when the argument is given")
    @Order(2)
    void findAll_ReturnsFoundClientes_WhenTheGivenNameIsFound() {
        String randomName = "Random Name";

        BDDMockito.when(repository.findAllByNameContaining(randomName)).thenReturn(Collections.emptyList());
        BDDMockito.when(mapper.toClientGetResponseList(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<ClientGetResponse> response = service.findAll(randomName);

        org.assertj.core.api.Assertions.assertThat(response)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("findAll returns an empty list when the given name is not found")
    @Order(3)
    void findAll_ReturnsEmptyList_WhenTheGivenNameIsNotFound() {
        Client clientToBeFound = clientList.getFirst();
        String nameToSearch = clientToBeFound.getName();

        List<Client> foundClientes = Collections.singletonList(clientToBeFound);

        List<ClientGetResponse> expectedResponse = Collections.singletonList(clientGetResponseList.getFirst());

        BDDMockito.when(repository.findAllByNameContaining(nameToSearch)).thenReturn(foundClientes);
        BDDMockito.when(mapper.toClientGetResponseList(foundClientes)).thenReturn(expectedResponse);

        List<ClientGetResponse> response = service.findAll(nameToSearch);

        Assertions.assertThat(response)
                .isNotNull()
                .isNotEmpty()
                .containsExactlyElementsOf(expectedResponse);
    }

    @Test
    @DisplayName("save returns saved client when successful")
    @Order(4)
    void save_ReturnsSavedClient_WhenSuccessful() {
        ClientPostRequest postRequest = ClientUtils.newClientPostRequest();
        ClientPostResponse postResponse = ClientUtils.newClientPostResponse();

        Client clientToSave = ClientUtils.newClientToSave();
        Client savedClient = ClientUtils.newClientSaved();

        BDDMockito.when(mapper.toClient(postRequest)).thenReturn(clientToSave);
        BDDMockito.when(repository.save(clientToSave)).thenReturn(savedClient);
        BDDMockito.when(mapper.toClientPostResponse(savedClient)).thenReturn(postResponse);

        ClientPostResponse response = service.save(postRequest);

        Assertions.assertThat(response)
                .isNotNull()
                .isEqualTo(postResponse);
    }

    @Test
    @DisplayName("findById returns found client when successful")
    @Order(5)
    void findById_ReturnsFoundClient_WhenSuccessful() {
        Client clientToBeFound = clientList.getFirst();
        Long idToSearch = clientToBeFound.getId();

        ClientGetResponse expectedResponse = clientGetResponseList.getFirst();

        BDDMockito.when(repository.findById(idToSearch)).thenReturn(Optional.of(clientToBeFound));
        BDDMockito.when(mapper.toClientGetResponse(clientToBeFound)).thenReturn(expectedResponse);

        ClientGetResponse response = service.findById(idToSearch);

        Assertions.assertThat(response)
                .isNotNull()
                .isEqualTo(expectedResponse);
    }

    @Test
    @DisplayName("findById throws NotFoundException when the given id is not found")
    @Order(6)
    void findById_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() {
        Long randomId = 999L;

        BDDMockito.when(repository.findById(randomId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.findById(randomId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Client with id '%s' is not found".formatted(randomId));
    }

    @Test
    @DisplayName("deleteById removes client when successful")
    @Order(7)
    void deleteById_RemovesClient_WhenSuccessful() {
        Client clientToDelete = clientList.getFirst();
        Long clientToDeleteId = clientToDelete.getId();

        BDDMockito.when(repository.findById(clientToDeleteId)).thenReturn(Optional.of(clientToDelete));
        BDDMockito.doNothing().when(repository).delete(clientToDelete);

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.deleteById(clientToDeleteId));
    }

    @Test
    @DisplayName("deleteById throws NotFoundException when the given id is not found")
    @Order(8)
    void deleteById_ThrowsNotFoundException_WhenTheGivenIdIsNotFound() {
        Long randomId = 999L;

        BDDMockito.when(repository.findById(randomId)).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.deleteById(randomId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Client with id '%s' is not found".formatted(randomId));
    }

    @Test
    @DisplayName("update updates client when successful")
    @Order(8)
    void update_UpdatesClient_WhenSuccessful() {
        Client clientNotUpdated = clientList.getFirst();
        Long clientToUpdateId = clientNotUpdated.getId();

        ClientPutRequest putRequest = ClientUtils.newClientPutRequest();

        Client clientToUpdate = ClientUtils.newClientToUpdate();

        BDDMockito.when(repository.findById(putRequest.getId())).thenReturn(Optional.of(clientNotUpdated));
        BDDMockito.when(mapper.toClient(putRequest)).thenReturn(clientToUpdate);
        BDDMockito.when(repository.save(clientToUpdate)).thenReturn(clientToUpdate);

        Assertions.assertThatNoException()
                .isThrownBy(() -> service.update(clientToUpdateId, putRequest));
    }

    @Test
    @DisplayName("update throws BadRequestException when the url id does not match request body id")
    @Order(9)
    void update_ThrowsBadRequestException_WhenTheUrlIdDoesNotMatchRequestBodyId() {
        Long randomId = 999L;

        ClientPutRequest putRequest = ClientUtils.newClientPutRequest();

        Assertions.assertThatThrownBy(() -> service.update(randomId, putRequest))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("The ID in the URL (%s) does not match the ID in the request body (%s)".formatted(randomId, putRequest.getId()));
    }

    @Test
    @DisplayName("update throws NotFoundException when the given client id is not found")
    @Order(10)
    void update_ThrowsNotFoundException_WhenTheGivenClientIdIsNotFound() {
        Long randomId = 999L;

        ClientPutRequest putRequest = ClientUtils.newClientPutRequest().withId(randomId);

        BDDMockito.when(repository.findById(putRequest.getId())).thenReturn(Optional.empty());

        Assertions.assertThatThrownBy(() -> service.update(randomId, putRequest))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Client with id '%s' is not found".formatted(randomId));
    }
}