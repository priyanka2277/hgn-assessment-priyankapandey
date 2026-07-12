package com.example.hgn.coordinator;

import com.example.hgn.common.ServerResponse;
import com.example.hgn.coordinator.dto.CreateCoordinatorRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/coordinator")
public class CoordinatorController {

    private final CoordinatorService coordinatorService;

    public CoordinatorController(CoordinatorService coordinatorService) {
        this.coordinatorService = coordinatorService;
    }

    @Operation(summary = "create the coordinator",
            description = "create the coordinator",
            tags = {"coordinator", "create"})
    @PostMapping(value = "/create")
    public ResponseEntity<ServerResponse> createCoordinator(
            @RequestBody @Valid CreateCoordinatorRequest request
    ) {
        ServerResponse response = coordinatorService.createCoordinator(request);
        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @Operation(summary = "update the coordinator",
            description = "update the coordinator",
            tags = {"coordinator", "update"})
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ServerResponse> updateCoordinator(
            @PathVariable String id,
            @RequestBody @Validated CreateCoordinatorRequest request
    ) {
        ServerResponse response = coordinatorService.updateCoordinator(id, request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "get all the coordinator",
            description = "get all the coordinator",
            tags = {"coordinator", "get"})
    @GetMapping(value = "/fetch/all")
    public ResponseEntity<ServerResponse> getAllCoordinator(
            @RequestParam Map<String, String> allRequestParam,
            @RequestParam(name = "pageNumber") @Min(0) @Max(99999999)
            @Positive(message = "Page number cannot be negative")
            int pageNumber,
            @RequestParam(name = "pageSize") @Min(1) @Max(100)
            @Positive(message = "Page size cannot be negative")
            int pageSize
    ) {
        ServerResponse response = coordinatorService.fetchAllCoordinator(allRequestParam, PageRequest.of(pageNumber - 1, pageSize));
        return new ResponseEntity<>(response, response.getHttpStatus());

    }


}
