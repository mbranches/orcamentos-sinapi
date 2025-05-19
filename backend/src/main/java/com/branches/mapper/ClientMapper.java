package com.branches.mapper;

import com.branches.model.Client;
import com.branches.response.ClientGetResponse;
import com.branches.request.ClientPostRequest;
import com.branches.response.ClientPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClientMapper {

    List<ClientGetResponse> toClientGetResponseList(List<Client> clientList);

    Client toClient(ClientPostRequest postRequest);

    ClientPostResponse toClientPostResponse(Client client);

    ClientGetResponse toClientGetResponse(Client client);
}
