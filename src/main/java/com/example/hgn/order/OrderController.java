package com.example.hgn.order;

import com.example.hgn.common.ServerResponse;
import com.example.hgn.order.dto.CreateOrderRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Create Order",
            description = " Create an Order",
            tags = {"order", "post"}
    )
    @PostMapping(value = "/create")
    public ResponseEntity<ServerResponse> createOrder(
            @RequestBody @Validated CreateOrderRequest createOrderRequest
    ) {
        ServerResponse response = orderService.createOrder(createOrderRequest);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Update Order",
            description = "Update an Order",
            tags = {"order", "put"}

    )
    @PutMapping(value = "/update/{bookingNumber}")
    public ResponseEntity<ServerResponse> updateOrder(
            @PathVariable String bookingNumber,
            @RequestBody @Validated CreateOrderRequest updateOrderRequest
    ) {
        ServerResponse response = orderService.updateOrder(bookingNumber, updateOrderRequest);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
