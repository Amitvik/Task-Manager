package com.cs790.app.model.dto;

import lombok.Data;

@Data
public class CustomExceptionDto {

    private String errorCode;
    private String customMessage;
}
