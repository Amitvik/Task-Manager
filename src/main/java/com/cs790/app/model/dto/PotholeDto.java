package com.cs790.app.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class PotholeDto {
    private String streetName;
    private String dateUploaded;

    private Map<String,String> imageMetaData;

    private String userId;

}
