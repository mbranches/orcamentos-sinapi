package com.branches.utils;

import com.branches.model.Client;
import com.branches.model.ClientType;
import com.branches.request.ClientPostRequest;
import com.branches.response.BudgetGetResponse;
import com.branches.response.BudgetPostResponse;
import com.branches.response.ClientGetResponse;
import com.branches.response.ClientPostResponse;

import java.util.ArrayList;
import java.util.List;

public class ClientUtils {
    public static List<Client> newClientList() {
        Client client1 = Client.builder().id(1L).name("Marcus Branches").clientType(ClientType.pessoa).build();
        Client client2 = Client.builder().id(2L).name("MassConstruções").clientType(ClientType.empresa).build();
        Client client3 = Client.builder().id(3L).name("Restaurante Bom Apetite").clientType(ClientType.empresa).build();

        return new ArrayList<>(List.of(client1, client2, client3));
    }

    public static List<ClientGetResponse> newClientGetResponseList() {
        ClientGetResponse client1 = ClientGetResponse.builder().id(1L).name("Marcus Branches").clientType(ClientType.pessoa).build();
        ClientGetResponse client2 = ClientGetResponse.builder().id(2L).name("MassConstruções").clientType(ClientType.empresa).build();
        ClientGetResponse client3 = ClientGetResponse.builder().id(3L).name("Restaurante Bom Apetite").clientType(ClientType.empresa).build();

        return new ArrayList<>(List.of(client1, client2, client3));
    }

    public static Client newClientToSave() {
        return Client.builder()
                .name("Condomínio Costa&Silva")
                .clientType(ClientType.empresa)
                .build();
    }

    public static Client newClientSaved() {
        return newClientToSave().withId(4L);
    }

    public static ClientPostRequest newClientPostRequest() {
        return ClientPostRequest.builder()
                .name("Condomínio Costa&Silva")
                .clientType(ClientType.empresa)
                .build();
    }

    public static ClientPostResponse newClientPostResponse() {
        return ClientPostResponse.builder()
                .id(4L)
                .name("Condomínio Costa&Silva")
                .clientType(ClientType.empresa)
                .build();
    }

    public static BudgetGetResponse.ClientByBudgetGetResponse newClientByBudgetGetResponse() {
        return new BudgetGetResponse.ClientByBudgetGetResponse(1L, "Marcus Branches", ClientType.pessoa);
    }

    public static BudgetPostResponse.ClientByBudgetPostResponse newClientByBudgetPostResponse() {
        return new BudgetPostResponse.ClientByBudgetPostResponse(1L, "Marcus Branches", ClientType.pessoa);
    }
}
