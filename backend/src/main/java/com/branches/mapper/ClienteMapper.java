package com.branches.mapper;

import com.branches.controller.ClienteGetResponse;
import com.branches.controller.ClientePostRequest;
import com.branches.controller.ClientePostResponse;
import com.branches.model.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Primary
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ClienteMapper {

    List<ClienteGetResponse> toClienteGetResponseList(List<Cliente> clienteList);

    Cliente toCliente(ClientePostRequest postRequest);

    ClientePostResponse toClientePostResponse(Cliente cliente);

    ClienteGetResponse toClienteGetResponse(Cliente cliente);
}
