package com.andre.balancesheet.controller;

import com.andre.balancesheet.dto.RegisterRequest;
import com.andre.balancesheet.dto.UserResponseDto;
import com.andre.balancesheet.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasAnyRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    @Transactional
    @PatchMapping("/admin/{userId}")
    @PreAuthorize("hasAnyAuthority('admin:update')")
    public ResponseEntity<UserResponseDto> patch(@PathVariable String userId, @RequestBody RegisterRequest dto) {
        var response = adminService.updateUserRole(userId, dto);
        return ResponseEntity.ok().body(response);
    }
}
