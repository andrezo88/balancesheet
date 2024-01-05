package com.andre.balancesheet.controllers;

import com.andre.balancesheet.dtos.BalanceDto;
import com.andre.balancesheet.dtos.BalanceDtoResponse;
import com.andre.balancesheet.services.BalanceService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PostMapping("/v1/balance")
    public ResponseEntity<Void> saveBalance(@NotNull @RequestBody BalanceDto dto) {
        balanceService.save(dto);

        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{balanceId}")
                .buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(location)
                .build();
    }

    @GetMapping("/v1/balance")
    public ResponseEntity<List<BalanceDtoResponse>> getBalance() {
        List<BalanceDtoResponse> balanceDtoResponse = balanceService.getBalance();
        return ResponseEntity.ok().body(balanceDtoResponse);
    }

    @GetMapping("/v1/balance/{balanceId}")
    public ResponseEntity<BalanceDtoResponse> getBalanceById(@PathVariable(value = "balanceId") String balanceId) {
        BalanceDtoResponse balanceDtoResponse = balanceService.getBalanceById(balanceId);
        return ResponseEntity.ok().body(balanceDtoResponse);
    }

    @GetMapping("/v1/balance/{monthNumber}/monthly")
    public ResponseEntity<List<BalanceDtoResponse>> getBalanceByMonth(@PathVariable(value = "monthNumber") String monthNumber) {
        List<BalanceDtoResponse> balanceDtoResponse = balanceService.getBalanceByMonth(monthNumber);
        return ResponseEntity.ok().body(balanceDtoResponse);
    }
    @PatchMapping("/v1/balance/{balanceId}")
    public ResponseEntity<BalanceDtoResponse> updateBalance(@PathVariable(value = "balanceId") String balanceId, @RequestBody BalanceDto dto) {
        BalanceDtoResponse balanceDtoResponse = balanceService.updateBalance(balanceId, dto);
        return ResponseEntity.ok().body(balanceDtoResponse);
    }
}
