package com.example.hgn.trekgrop;

import com.example.hgn.common.ServerResponse;
import com.example.hgn.trekgrop.dto.CreateTreakGroupRequest;
import com.example.hgn.trekgrop.dto.UpdateTrekGroupRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/trekgroup")
public class TrekGroupController {
    private final TrekGroupService trekGroupService;

    public TrekGroupController(TrekGroupService trekGroupService) {
        this.trekGroupService = trekGroupService;
    }

    @Operation(summary = "Create treakGroup",
            description = "Create a treakGroup",
            tags = {"trekgroup", "post"}
    )
    @PostMapping(value = "/create")
    public ResponseEntity<ServerResponse> createTreakGroup(
            @RequestBody @Validated CreateTreakGroupRequest request
    ) {
        ServerResponse response = trekGroupService.createTrekGroup(request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "UpdatetreakGroup",
            description = "Update a treak group",
            tags = {"trekgroup", "put"}
    )
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ServerResponse> updateTreakGroup(
            @PathVariable String id,
            @RequestBody @Validated UpdateTrekGroupRequest request
    ) {
        ServerResponse response = trekGroupService.updateTrekGroup(id, request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Get all trek Group",
            description = "Get all the trek groups",
            tags = {"trekgroup", "get"}
    )
    @GetMapping(value = "/fetch/all")
    public ResponseEntity<ServerResponse> getAllTrekGroup(
            @RequestParam Map<String, String> allRequestParams,
            @RequestParam(name = "pageNumber") @Min(0) @Max(99999999)
            @Positive(message = "Page number cannot be negative") int pageNumber,
            @RequestParam(name = "pageSize") @Min(1) @Max(100)
            @Positive(message = "Page size cannot be negative")
            int pageSize
    ) {
        ServerResponse response = trekGroupService.fetchAllTrekGroup(allRequestParams, PageRequest.of(pageNumber - 1, pageSize));
        return new ResponseEntity<>(response, response.getHttpStatus());
    }


}
