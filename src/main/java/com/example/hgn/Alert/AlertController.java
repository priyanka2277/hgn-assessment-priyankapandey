package com.example.hgn.Alert;

import com.example.hgn.Alert.dto.AlertCreateRequest;
import com.example.hgn.common.ServerResponse;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/alert")
public class AlertController {
    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @Operation(summary = "save the alert",
            description = "save the alert",
            tags = {"alert", "post"})
    @PostMapping(value = "/save")
    public ResponseEntity<ServerResponse> saveAlert(@RequestBody AlertCreateRequest alertCreateRequest) {
        ServerResponse response = alertService.saveAlert(alertCreateRequest);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
