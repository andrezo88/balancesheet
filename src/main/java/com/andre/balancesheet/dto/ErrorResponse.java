package com.andre.balancesheet.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.List;


@Builder
public record ErrorResponse(
        String message,
        Integer status,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        List<String> errors
) {}
