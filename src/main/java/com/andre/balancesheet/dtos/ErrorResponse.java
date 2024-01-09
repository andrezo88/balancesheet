package com.andre.balancesheet.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private Integer status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errors;
}
