package com.example.hgn.trekgrop.Trekker;

import com.example.hgn.common.ServerResponse;
import com.example.hgn.exception.BadRequestException;
import com.example.hgn.exception.NotFoundException;
import com.example.hgn.trekgrop.TrekGroupEntity;
import com.example.hgn.trekgrop.TrekGroupRepository;
import com.example.hgn.trekgrop.Trekker.dto.CreateTrekkerRequest;
import com.example.hgn.trekgrop.Trekker.dto.TrekkerListResponseDTO;
import com.example.hgn.trekgrop.Trekker.dto.UpdateTrekkerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TrekkerService {

    private final TrekkerRepository trekkerRepository;
    private final TrekGroupRepository trekGroupRepository;

    public TrekkerService(TrekkerRepository trekkerRepository, TrekGroupRepository trekGroupRepository) {
        this.trekkerRepository = trekkerRepository;
        this.trekGroupRepository = trekGroupRepository;
    }

    public ServerResponse createTrekker(CreateTrekkerRequest request) {
        if (trekkerRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new BadRequestException("Phone number already exists");
        }
        TrekGroupEntity group = trekGroupRepository.findById(request.getTrekGroupId()).orElseThrow(() -> new NotFoundException("Trek Group not found"));
        TrekkerEntity trekker = createTrekkerEntity(request, group);
        trekkerRepository.save(trekker);
        return ServerResponse.successResponse("Trekker created successfully", HttpStatus.CREATED);

    }

    private TrekkerEntity createTrekkerEntity(CreateTrekkerRequest request, TrekGroupEntity group) {
        TrekkerEntity trekkerEntity = new TrekkerEntity();
        trekkerEntity.setFirstName(request.getFirstName());
        trekkerEntity.setLastName(request.getLastName());
        trekkerEntity.setPhoneNumber(request.getPhoneNumber());
        trekkerEntity.setGroup(group);
        return trekkerEntity;
    }

    public ServerResponse updateTrekker(String trekkerId, UpdateTrekkerRequest request) {
        TrekkerEntity trekker = trekkerRepository.findById(trekkerId).orElseThrow(() -> new NotFoundException("Trekker with given id does not found"));
        if (request.getFirstName() != null) {
            if (request.getFirstName().isBlank()) {
                throw new BadRequestException("First name cannot be blank");
            }
            trekker.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            if (request.getLastName().isBlank()) {
                throw new BadRequestException("Last name cannot be blank");
            }
            trekker.setLastName(request.getLastName());
        }
        if (request.getPhoneNumber() != null) {
            if (request.getPhoneNumber().isBlank()) {
                throw new BadRequestException("Phone number cannot be blank");
            }
            trekker.setPhoneNumber(request.getPhoneNumber());
        }
        trekkerRepository.save(trekker);
        return ServerResponse.successResponse("Trekker information updated succesfully", HttpStatus.OK);
    }

    public ServerResponse getAllTrekkers(Map<String, String> allRequestParams, Pageable pageable) {
        String firstName = allRequestParams.getOrDefault("firstName", null);
        String lastName = allRequestParams.getOrDefault("lastName", null);
        String phoneNumber = allRequestParams.getOrDefault("phoneNumber", null);
        Page<TrekkerEntity> trekkerResponse = trekkerRepository.fetchAllTrekkers(firstName, lastName, phoneNumber, pageable);
        List<TrekkerListResponseDTO> dtoList = trekkerResponse.stream().map(this::mapToTrekkerDTO).toList();
        if (!dtoList.isEmpty()) {
            return ServerResponse.successObjectResponse("Trekker fetched successfully", HttpStatus.OK, dtoList, dtoList.size());
        }
        return ServerResponse.successObjectResponse("Empty Trekker list", HttpStatus.OK, List.of(), 0);
    }

    private TrekkerListResponseDTO mapToTrekkerDTO(TrekkerEntity trekker) {
        return new TrekkerListResponseDTO(
                trekker.getId(),
                trekker.getFirstName(),
                trekker.getLastName(),
                trekker.getPhoneNumber()
        );
    }
}
