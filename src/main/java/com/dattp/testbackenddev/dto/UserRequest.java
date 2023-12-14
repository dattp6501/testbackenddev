package com.dattp.testbackenddev.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {
    @NotNull(message = "Userid cannot be blank")
    private String id;

    public UserRequest() {
    }
}
