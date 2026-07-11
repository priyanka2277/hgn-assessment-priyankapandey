package com.example.hgn.device;

import com.example.hgn.common.ServerResponse;
import com.example.hgn.device.dto.DeviceCreateRequest;
import com.example.hgn.device.dto.DeviceListResponseDTO;
import com.example.hgn.device.dto.DeviceUpdateRequest;
import com.example.hgn.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    public ServerResponse createDevice(DeviceCreateRequest request) {

        if (deviceRepository.existsByDeviceId(request.getDeviceId())) {
            throw new IllegalArgumentException("Device already exists");
        }
        DeviceEntity device = createDeviceEntity(request, new DeviceEntity());
        device = deviceRepository.save(device);
        return ServerResponse.successResponse("Device created successfully", HttpStatus.CREATED);

    }

    private DeviceEntity createDeviceEntity(DeviceCreateRequest request, DeviceEntity device) {
        device.setDeviceId(request.getDeviceId());
        device.setDeviceName(request.getDeviceName());
        return device;
    }

    @Transactional
    public ServerResponse updateDevice(String deviceId, DeviceUpdateRequest request) {
        DeviceEntity device = deviceRepository.findByDeviceId(deviceId).orElseThrow(() -> new NotFoundException("Device with given deviceId not Found "));
        updateDeviceEntity(request, device);
        device = deviceRepository.save(device);
        return ServerResponse.successResponse("device updated successfully", HttpStatus.OK);
    }

    private DeviceEntity updateDeviceEntity(DeviceUpdateRequest request, DeviceEntity device) {
        device.setDeviceName(request.getDeviceName());
        return device;
    }

    public ServerResponse getAllDevices(Map<String, String> allRequestParams, Pageable pageable) {
        String deviceName = allRequestParams.getOrDefault("deviceName", null);
        Page<DeviceEntity> devices = deviceRepository.findAllDevices(deviceName, pageable);
        List<DeviceListResponseDTO> dtoList = devices.stream().map(this::mapToDeviceDTO).toList();
        if (!dtoList.isEmpty()) {
            return ServerResponse.successObjectResponse("Devices fetched sucessfully", HttpStatus.OK, dtoList, dtoList.size());
        }
        return ServerResponse.successObjectResponse("Devices empty", HttpStatus.OK, List.of(), 0);

    }

    private DeviceListResponseDTO mapToDeviceDTO(DeviceEntity device) {
        return new DeviceListResponseDTO(
                device.getDeviceName(),
                device.getDeviceId()
        );
    }
}
