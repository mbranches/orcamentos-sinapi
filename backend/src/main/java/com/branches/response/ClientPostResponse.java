package com.branches.response;

import com.branches.model.ClientType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientPostResponse {
    private Long id;
    private String name;
    private ClientType clientType;
}
