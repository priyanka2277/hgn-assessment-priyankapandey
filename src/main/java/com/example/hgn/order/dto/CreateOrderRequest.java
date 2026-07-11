package com.example.hgn.order.dto;

import com.example.hgn.order.constant.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateOrderRequest {
    @NotBlank(message = "Booking name is required")
    private String bookingName;


    @NotNull(message = "Order status is required")
    private OrderStatus orderStatus;

    @NotNull(message = "Start date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private String deviceId;

}
