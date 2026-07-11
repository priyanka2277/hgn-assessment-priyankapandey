package com.example.hgn.trekgrop.dto;

import com.example.hgn.trekgrop.constant.TrekGroupType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateTreakGroupRequest {
    @NotBlank(message ="Group name is required")
    private String groupName;

    @NotNull(message = "Group type is required")
    private TrekGroupType trekGroupType;

    @NotBlank(message = "Booking number is required")
    private String bookingNumber;



}
