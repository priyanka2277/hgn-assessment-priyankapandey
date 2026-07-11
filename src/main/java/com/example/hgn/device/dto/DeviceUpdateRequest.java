package com.example.hgn.device.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceUpdateRequest {
    @NotNull
    private String deviceName;
}
