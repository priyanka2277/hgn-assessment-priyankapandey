package com.example.hgn.Alert.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AlertCreateRequest {
    @NotBlank(message = "Device Id is required")
    private String deviceId;

    @NotNull(message = "Latitude is required")
    private Double latitude;

    @NotNull(message = "Longitude is required")
    private Double longitude;

    @NotBlank(message = "Device time stamp is required")
    private LocalDateTime deviceTimeStamp;
}
