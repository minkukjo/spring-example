package com.example.example.presentation.api.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SignupResponse extends OkResponse {

    public SignupResponse(int code, String message) {
        super(code, message);
    }


}
