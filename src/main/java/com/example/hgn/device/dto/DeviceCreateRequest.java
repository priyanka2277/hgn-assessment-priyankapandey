package com.example.hgn.device.dto;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceCreateRequest {
    @NotBlank(message = "Device name is required")
    private String deviceName;

    @NotBlank(message = "Device Id is required")
    private String deviceId;

}
