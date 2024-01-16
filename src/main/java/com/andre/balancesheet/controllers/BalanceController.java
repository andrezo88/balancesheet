package com.andre.balancesheet.controllers;

import com.andre.balancesheet.dtos.BalanceDto;
import com.andre.balancesheet.dtos.BalanceDtoResponse;
import com.andre.balancesheet.services.BalanceService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.data.domain.Sort.Direction.ASC;

@RestController
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PostMapping("/v1/balance")
    public ResponseEntity<BalanceDtoResponse> saveBalance(@RequestBody BalanceDto dto) {
        BalanceDtoResponse saved = balanceService.save(dto);

        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{balanceId}")
                .buildAndExpand(saved.getId()).toUri();

         return ResponseEntity.created(location)
                .build();
    }

    @GetMapping("/v1/balance")
    public ResponseEntity<Page<BalanceDtoResponse>> getBalance(@Parameter(hidden = true)
                                                               @PageableDefault(
                                                                          page = 0,
                                                                          size = 10,
                                                                          sort = "id",
                                                                          direction = ASC
                                                               ) Pageable pageable) {
        Page<BalanceDtoResponse> balanceDtoResponse = balanceService.getBalacePaged(pageable);
        return ResponseEntity.ok().body(balanceDtoResponse);
    }

    @GetMapping("/v1/balance/{balanceId}")
    public ResponseEntity<BalanceDtoResponse> getBalanceById(@PathVariable(value = "balanceId") String balanceId) {
        BalanceDtoResponse balanceDtoResponse = balanceService.getBalanceById(balanceId);
        return ResponseEntity.ok().body(balanceDtoResponse);
    }

    @GetMapping("/v1/balance/filter/{startDate}/{endDate}")
    public ResponseEntity<Page<BalanceDtoResponse>> getBalanceByMonth(@Parameter(hidden = true)
                                                                          @PageableDefault(
                                                                                  page = 0,
                                                                                  size = 10,
                                                                                  sort = "id",
                                                                                  direction = ASC
                                                                          )
                                                                          Pageable pageable,
                                                                      @PathVariable(value = "startDate") String startDate,
                                                                      @PathVariable(value = "endDate") String endDate) {
        Page<BalanceDtoResponse> balanceDtoResponse = balanceService.getBalanceByMonth(pageable, startDate, endDate);
        return ResponseEntity.ok().body(balanceDtoResponse);
    }

    @Transactional
    @PatchMapping("/v1/balance/{balanceId}")
    public ResponseEntity<BalanceDtoResponse> updateBalance(@PathVariable(value = "balanceId") String balanceId, @RequestBody BalanceDto dto) {
        BalanceDtoResponse balanceDtoResponse = balanceService.updateBalance(balanceId, dto);
        return ResponseEntity.ok().body(balanceDtoResponse);
    }
}
