package org.example.network.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class LoginRequest {

    @NotNull
    private String porecla;


}
