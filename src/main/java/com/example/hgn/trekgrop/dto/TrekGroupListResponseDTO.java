package com.example.hgn.trekgrop.dto;

import com.example.hgn.trekgrop.constant.TrekGroupType;

public record TrekGroupListResponseDTO(
        String id,
        String groupName,
        TrekGroupType trekGroupType,
        String bookingNumber
) {
}
