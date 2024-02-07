package com.andre.balancesheet.controller;

import com.andre.balancesheet.dto.BalanceDto;
import com.andre.balancesheet.dto.BalanceDtoResponse;
import com.andre.balancesheet.service.BalanceService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.zip.DataFormatException;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
@RequestMapping("/api/v1")
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
@RequiredArgsConstructor

public class BalanceController {

    private final BalanceService balanceService;

    @PostMapping("/balance")
    @PreAuthorize("hasAnyAuthority('user:create', 'admin:create')")
    public ResponseEntity<BalanceDtoResponse> saveBalance(@Valid @RequestBody BalanceDto dto) {
        BalanceDtoResponse saved = balanceService.save(dto);

        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{balanceId}")
                .buildAndExpand(saved.getId()).toUri();

         return ResponseEntity.created(location)
                .build();
    }

    @GetMapping("/balance/{balanceId}")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<BalanceDtoResponse> getBalanceById(@PathVariable(value = "balanceId") String balanceId) {
        BalanceDtoResponse balanceDtoResponse = balanceService.getBalanceById(balanceId);
        return ResponseEntity.ok().body(balanceDtoResponse);
    }

    @GetMapping("/balance")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<Page<BalanceDtoResponse>> getBalanceByMonthRange(@Parameter(hidden = true)
                                                                          @PageableDefault(
                                                                                  size = 10,
                                                                                  sort = "id",
                                                                                  direction = ASC
                                                                          )
                                                                          Pageable pageable,
                                                                      @RequestParam(required = false, value = "startDate") String startDate,
                                                                      @RequestParam(required = false, value = "endDate") String endDate) throws DataFormatException {
        Page<BalanceDtoResponse> balanceDtoResponse = balanceService.getBalanceByMonthRange(pageable, startDate, endDate);
        return ResponseEntity.ok().body(balanceDtoResponse);
    }

    @Transactional
    @PatchMapping("/balance/{balanceId}")
    @PreAuthorize("hasAnyAuthority('user:update', 'admin:update')")
    public ResponseEntity<BalanceDtoResponse> updateBalance(@PathVariable(value = "balanceId") String balanceId, @RequestBody BalanceDto dto) {
        BalanceDtoResponse balanceDtoResponse = balanceService.updateBalance(balanceId, dto);
        return ResponseEntity.ok().body(balanceDtoResponse);
    }

    @GetMapping("/balance-total")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<String> getBalanceTotal(@Parameter(hidden = true)
            @RequestParam(required = false, value = "startDate") String startDate,
            @RequestParam(required = false, value = "endDate") String endDate) throws DataFormatException {
        var total = balanceService.getBalanceTotal(startDate, endDate);
        return ResponseEntity.ok().body(total);
    }
}
