package com.example.hgn.coordinator;

import com.example.hgn.common.ServerResponse;
import com.example.hgn.coordinator.dto.CoordiantorListResponseDTO;
import com.example.hgn.coordinator.dto.CreateCoordinatorRequest;
import com.example.hgn.exception.BadRequestException;
import com.example.hgn.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CoordinatorService {
    private final CoordinatorRepository coordinatorRepository;

    public CoordinatorService(CoordinatorRepository coordinatorRepository) {
        this.coordinatorRepository = coordinatorRepository;
    }

    public ServerResponse createCoordinator(CreateCoordinatorRequest request) {
        if (request.getFirstName() == null || request.getFirstName().isBlank()) {
            throw new BadRequestException("First name is required");
        }
        if (request.getLastName() == null || request.getLastName().isBlank()) {
            throw new BadRequestException("Last name is required");
        }
        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new BadRequestException("Email is required");
        }
        if (request.getPhoneNumber() == null || request.getPhoneNumber().isBlank()) {
            throw new BadRequestException("Phone number is required");
        }
        if (coordinatorRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already exists");
        }
        if (coordinatorRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new BadRequestException("Phone number already exists");
        }
        CoordinatorEntity coordinator = createCoordinatorEntity(request);
        coordinatorRepository.save(coordinator);
        return ServerResponse.successResponse("Coordinator created successfully", HttpStatus.CREATED);
    }

    private CoordinatorEntity createCoordinatorEntity(CreateCoordinatorRequest request) {
        CoordinatorEntity coordinator = new CoordinatorEntity();
        coordinator.setFirstName(request.getFirstName());
        coordinator.setLastName(request.getLastName());
        coordinator.setEmail(request.getEmail());
        coordinator.setPhoneNumber(request.getPhoneNumber());
        return coordinator;
    }

    public ServerResponse updateCoordinator(String id, CreateCoordinatorRequest request) {
        CoordinatorEntity coordinator = coordinatorRepository.findById(id).orElseThrow(() -> new NotFoundException("Coordinator with given id does not found"));
        if (request.getFirstName() != null) {
            if (request.getFirstName().isBlank()) {
                throw new BadRequestException("First name cannot be blank");
            }
            coordinator.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            if (request.getLastName().isBlank()) {
                throw new BadRequestException("Last name cannot be blank");
            }
            coordinator.setLastName(request.getLastName());
        }
        if (request.getEmail() != null) {
            if (request.getEmail().isBlank()) {
                throw new BadRequestException("Email  cannot be blank");
            }
            coordinator.setEmail(request.getEmail());
        }
        if (request.getPhoneNumber() != null) {
            if (request.getPhoneNumber().isBlank()) {
                throw new BadRequestException("Phone Number cannot be blank");
            }
            coordinator.setPhoneNumber(request.getPhoneNumber());
        }
        coordinatorRepository.save(coordinator);
        return ServerResponse.successResponse("Coordinator info updated successfully", HttpStatus.OK);
    }

    public ServerResponse fetchAllCoordinator(Map<String, String> filters, Pageable pageable) {
        String email = filters.getOrDefault("email", null);
        Page<CoordinatorEntity> coordinatorResponse = coordinatorRepository.findAllCoordinator(email, pageable);
        List<CoordiantorListResponseDTO> dtoList = coordinatorResponse.stream().map(this::mapToCoordinatorDTO).toList();
        if (!dtoList.isEmpty()) {
            return ServerResponse.successObjectResponse("Coordinator Fetched succcessfully", HttpStatus.OK, dtoList, dtoList.size());
        }
        return ServerResponse.successObjectResponse("Coordinator empty list", HttpStatus.OK, List.of(), 0);


    }

    private CoordiantorListResponseDTO mapToCoordinatorDTO(CoordinatorEntity coordinator) {
        return new CoordiantorListResponseDTO(
                coordinator.getFirstName(),
                coordinator.getLastName(),
                coordinator.getEmail(),
                coordinator.getPhoneNumber()
        );
    }
}
