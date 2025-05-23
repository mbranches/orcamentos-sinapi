package com.branches.request;

import com.branches.model.ClientType;
import lombok.Builder;
import lombok.Data;
import lombok.With;

@With
@Data
@Builder
public class ClientPutRequest {
    private Long id;
    private String name;
    private ClientType clientType;
}
