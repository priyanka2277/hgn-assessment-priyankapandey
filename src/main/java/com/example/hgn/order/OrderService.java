package com.example.hgn.order;

import com.example.hgn.common.ServerResponse;
import com.example.hgn.device.DeviceEntity;
import com.example.hgn.device.DeviceRepository;
import com.example.hgn.exception.BadRequestException;
import com.example.hgn.exception.NotFoundException;
import com.example.hgn.order.dto.CreateOrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final DeviceRepository deviceRepository;

    public OrderService(OrderRepository orderRepository, DeviceRepository deviceRepository) {
        this.orderRepository = orderRepository;
        this.deviceRepository = deviceRepository;
    }

    @Transactional
    public ServerResponse createOrder(CreateOrderRequest request) {
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new BadRequestException("End date cannot be before the start date");
        }
        DeviceEntity device = deviceRepository.findByDeviceId(request.getDeviceId()).orElseThrow(() -> new NotFoundException("Device not Found"));
        List<OrderEntity> deviceBookedOrders = orderRepository.findOverlappingOrders(
                request.getDeviceId(),
                request.getStartDate(),
                request.getEndDate()
        );
        if (!deviceBookedOrders.isEmpty()) {
            throw new BadRequestException("The requested device is already booked");
        }
        OrderEntity order = createOrderEntity(request, device);
        order = orderRepository.save(order);
        return ServerResponse.successResponse("Order Created sucessfully", HttpStatus.CREATED);


    }

    private OrderEntity createOrderEntity(CreateOrderRequest request, DeviceEntity device) {
        OrderEntity order = new OrderEntity();
        order.setBookingName(request.getBookingName());
        order.setBookingNumber(generateBookingNumber());
        order.setStartDate(request.getStartDate());
        order.setEndDate(request.getEndDate());
        order.setStatus(request.getOrderStatus());
        order.setDevice(device);
        return order;
    }

    private String generateBookingNumber() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomPart = String.format("%04d", new Random().nextInt(10000));
        return "HGN-" + datePart + "-" + randomPart;
    }

   @Transactional
    public ServerResponse updateOrder(String bookingNumber, CreateOrderRequest request) {
        OrderEntity order = orderRepository.findByBookingNumber(bookingNumber)
                .orElseThrow(() -> new NotFoundException("device booking does not exist"));

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new BadRequestException("End date cannot be before the start date");
        }
        DeviceEntity device = deviceRepository.findByDeviceId(request.getDeviceId()).orElseThrow(() -> new NotFoundException("Device does not exist"));
        List<OrderEntity> deviceBookedOrders = orderRepository.findOverlappingOrdersExcludingSelf(
                request.getDeviceId(),
                request.getStartDate(),
                request.getEndDate(),
                order.getId()
        );

        if (!deviceBookedOrders.isEmpty()) {
            throw new BadRequestException("The requested device is already booked by others in the start date and end date you have assigned");
        }
        updateOrderEntity(request, device, order);
        order = orderRepository.save(order);
        return ServerResponse.successResponse("Order Updated sucessfully", HttpStatus.OK);

    }

    private OrderEntity updateOrderEntity(CreateOrderRequest request, DeviceEntity device, OrderEntity order) {
        order.setBookingName(request.getBookingName());
        order.setStartDate(request.getStartDate());
        order.setEndDate(request.getEndDate());
        order.setStatus(request.getOrderStatus());
        order.setDevice(device);
        return order;
    }

}
