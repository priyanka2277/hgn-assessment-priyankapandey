package com.example.hgn.trekgrop.Trekker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTrekkerRequest {

    @NotBlank(message ="First name is required")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    @NotBlank(message ="Phone Number is required")
    @Size(min =10 , max=10, message ="mobile Number contains 10 numbers")
    @Pattern(regexp = "^(98|97|96|95|94)\\d{8}$", message = "Invalid phone number")
    private String phoneNumber;

    @NotBlank(message ="Trek Group Id is required")
    private String trekGroupId;
}
