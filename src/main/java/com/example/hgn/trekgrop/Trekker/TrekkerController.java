package com.example.hgn.trekgrop.Trekker;

import com.example.hgn.common.ServerResponse;
import com.example.hgn.trekgrop.Trekker.dto.CreateTrekkerRequest;
import com.example.hgn.trekgrop.Trekker.dto.UpdateTrekkerRequest;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.apache.coyote.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/trekker")
public class TrekkerController {

    private final TrekkerService trekkerService;

    public TrekkerController(TrekkerService trekkerService) {
        this.trekkerService = trekkerService;
    }

    @Operation(summary = "Create a trekker",
            description = "Create a trekker",
            tags = {"trekker", "post"})
    @PostMapping(value = "/create")
    public ResponseEntity<ServerResponse> createTrekker(@RequestBody @Validated CreateTrekkerRequest request) {
        ServerResponse response = trekkerService.createTrekker(request);
        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @Operation(summary = "Update the trekker information",
            description = "Update a trekker",
            tags = {"trekker", "put"})
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<ServerResponse> updateTrekker(@PathVariable String id,
                                                        @RequestBody @Validated UpdateTrekkerRequest request) {
        ServerResponse response = trekkerService.updateTrekker(id, request);
        return new ResponseEntity<>(response, response.getHttpStatus());

    }

    @Operation(summary = "Get all trekker",
            description = "Get all trekker",
            tags = {"trekker", "get"})
    @GetMapping(value = "/fetch/all")
    public ResponseEntity<ServerResponse> getAllTrekkers(
            @RequestParam Map<String, String> allRequestParams,
            @RequestParam(name = "pageNumber") @Min(0) @Max(99999999) @Positive(message = "Page Number cannot be negative") int pageNumber,
            @RequestParam(name = "pageSize") @Min(1) @Max(100) @Positive(message = "Page size cannot be negative") int pageSize
    ) {
        ServerResponse response = trekkerService.getAllTrekkers(allRequestParams, PageRequest.of(pageNumber - 1, pageSize));
        return new ResponseEntity<>(response, response.getHttpStatus());

    }


}
