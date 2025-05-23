package com.branches.request;

import com.branches.model.ClientType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientPostRequest {
    private String name;
    private ClientType clientType;
}
