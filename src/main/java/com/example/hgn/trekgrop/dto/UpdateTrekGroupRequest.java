package com.example.hgn.trekgrop.dto;

import com.example.hgn.trekgrop.constant.TrekGroupType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTrekGroupRequest {
    private String groupName;

    private TrekGroupType trekGroupType;
}
