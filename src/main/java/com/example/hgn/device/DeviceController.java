package com.example.hgn.device;

import com.example.hgn.common.ServerResponse;
import com.example.hgn.device.dto.DeviceCreateRequest;
import com.example.hgn.device.dto.DeviceUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/device")
@Slf4j
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Operation(summary = "Create device", description = "Register a new device", tags = {"device", "post"})
    @PostMapping(value = "/create")
    public ResponseEntity<ServerResponse> createDevice(
            @RequestBody @Validated DeviceCreateRequest dto
    ) {
        ServerResponse response = deviceService.createDevice(dto);
        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @Operation(summary = "Update Device",
            description = "Update a device",
            tags = {"device", "put"}
    )
    @PutMapping(value = "/update/{deviceId}")
    public ResponseEntity<ServerResponse> updateDevice(
            @PathVariable String deviceId,
            @RequestBody DeviceUpdateRequest dto
    ) {
        ServerResponse response = deviceService.updateDevice(deviceId, dto);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Get device list",
            description = "Get list of all devices",
            tags = {"device", "get"})
    @GetMapping(value = "/fetch/all")
    public ResponseEntity<ServerResponse> getAllDevices(
            @RequestParam Map<String, String> allRequestParams,
            @RequestParam(name = "pageNumber") @Min(0) @Max(999999) @Positive(message = "PageNumber cannot be negative") int pageNumber,
            @RequestParam(name = "pageSize") @Min(1) @Max(100) @Positive(message = "page size cannot be negative") int pageSize
    ) {
        ServerResponse response = deviceService.getAllDevices(allRequestParams, PageRequest.of(pageNumber - 1, pageSize));
        return new ResponseEntity<>(response, response.getHttpStatus());
    }


}
