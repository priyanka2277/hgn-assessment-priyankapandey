package com.example.hgn.Alert;

import com.example.hgn.Alert.constant.ALERTSTATUS;
import com.example.hgn.Alert.dto.AlertCreateRequest;
import com.example.hgn.common.ServerResponse;
import com.example.hgn.device.DeviceEntity;
import com.example.hgn.device.DeviceRepository;
import com.example.hgn.exception.BadRequestException;
import com.example.hgn.exception.NotFoundException;
import com.example.hgn.order.OrderEntity;
import com.example.hgn.order.OrderRepository;
import com.example.hgn.trekgrop.TrekGroupEntity;
import com.example.hgn.trekgrop.TrekGroupRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final DeviceRepository deviceRepository;
    private final OrderRepository orderRepository;
    private final TrekGroupRepository trekGroupRepository;

    public AlertService(AlertRepository alertRepository, DeviceRepository deviceRepository, OrderRepository orderRepository, TrekGroupRepository trekGroupRepository){
        this.alertRepository = alertRepository;
        this.deviceRepository = deviceRepository;
        this.orderRepository = orderRepository;
        this.trekGroupRepository = trekGroupRepository;
    }

    public ServerResponse saveAlert(AlertCreateRequest request){
        DeviceEntity device = deviceRepository.findByDeviceId(request.getDeviceId()).orElseThrow(()->new NotFoundException("Device does not found in hsn"));
        OrderEntity order = orderRepository.findActiveOrder(request.getDeviceId(), request.getDeviceTimeStamp().toLocalDate()).orElseThrow(()-> new NotFoundException("No active booking found for this device"));
        TrekGroupEntity group = trekGroupRepository.findByOrderId(order.getId()).orElseThrow(()-> new NotFoundException("No group is associated with the above booking"));
        Optional<AlertEntity> latestAlert = alertRepository.findFirstByDevice_DeviceIdOrderByDeviceTimestampDesc(request.getDeviceId());
        if(latestAlert.isPresent()){
            AlertEntity alert = latestAlert.get();
            Duration difference = Duration.between(
                    alert.getDeviceTimestamp(),
                    request.getDeviceTimeStamp()
            );
            if(difference.abs().toMinutes() <=5){
                throw new BadRequestException("Duplicate SOS received");
            }
        }
        AlertEntity alert = createAlertEntity(request,device,order,group);
        alertRepository.save(alert);
        return ServerResponse.successResponse("Alert saved sucessfully", HttpStatus.CREATED);

    }
    private AlertEntity createAlertEntity(AlertCreateRequest request, DeviceEntity device, OrderEntity order, TrekGroupEntity group){
        AlertEntity alert = new AlertEntity();
        alert.setDevice(device);
        alert.setOrder(order);
        alert.setGroup(group);
        alert.setDeviceTimestamp(request.getDeviceTimeStamp());
        alert.setLatitude(request.getLatitude());
        alert.setLongitude(request.getLongitude());
        alert.setEscalated(false);
        alert.setReceivedAt(LocalDateTime.now());
        alert.setAlertStatus(ALERTSTATUS.NEW);
        return alert;
    }
}
