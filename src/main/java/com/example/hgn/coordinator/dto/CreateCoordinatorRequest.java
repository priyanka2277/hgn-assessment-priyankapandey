package com.example.hgn.coordinator.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateCoordinatorRequest {
    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Email is required")
    private String email;

    @NotNull(message = "phone Number is required")
    private String phoneNumber;
}
