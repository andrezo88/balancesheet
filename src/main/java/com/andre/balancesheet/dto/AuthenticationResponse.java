package com.andre.balancesheet.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse(String token) {}
