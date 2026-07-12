package com.example.hgn.coordinator.dto;

import lombok.Getter;
import lombok.Setter;


public record CoordiantorListResponseDTO(
        String firstName,
        String lastName,
        String email,
        String phoneNumber
) {
}
