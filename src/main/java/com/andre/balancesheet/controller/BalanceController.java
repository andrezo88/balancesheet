package com.andre.balancesheet.controller;

import com.andre.balancesheet.dto.BalanceDto;
import com.andre.balancesheet.dto.BalanceDtoResponse;
import com.andre.balancesheet.service.BalanceService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
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
@RequestMapping("/api/v1")
public class BalanceController {

    private final BalanceService balanceService;

    public BalanceController(BalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @PostMapping("/balance")
    public ResponseEntity<BalanceDtoResponse> saveBalance(@Valid @RequestBody BalanceDto dto) {
        BalanceDtoResponse saved = balanceService.save(dto);

        URI location= ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{balanceId}")
                .buildAndExpand(saved.getId()).toUri();

         return ResponseEntity.created(location)
                .build();
    }


    @GetMapping("/balance")
    public ResponseEntity<Page<BalanceDtoResponse>> getBalance(@Parameter(hidden = true)
                                                               @PageableDefault(
                                                                          size = 10,
                                                                          sort = "id",
                                                                          direction = ASC
                                                               ) Pageable pageable) {
        Page<BalanceDtoResponse> balanceDtoResponse = balanceService.getBalancePaged(pageable);
        return ResponseEntity.ok().body(balanceDtoResponse);
    }

    @GetMapping("/balance/{balanceId}")
    public ResponseEntity<BalanceDtoResponse> getBalanceById(@PathVariable(value = "balanceId") String balanceId) {
        BalanceDtoResponse balanceDtoResponse = balanceService.getBalanceById(balanceId);
        return ResponseEntity.ok().body(balanceDtoResponse);
    }

    @GetMapping("/balance/filter")
    public ResponseEntity<Page<BalanceDtoResponse>> getBalanceByMonthRange(@Parameter(hidden = true)
                                                                          @PageableDefault(
                                                                                  size = 10,
                                                                                  sort = "id",
                                                                                  direction = ASC
                                                                          )
                                                                          Pageable pageable,
                                                                      @RequestParam(value = "startDate") String startDate,
                                                                      @RequestParam(value = "endDate") String endDate) {
        Page<BalanceDtoResponse> balanceDtoResponse = balanceService.getBalanceByMonthRange(pageable, startDate, endDate);
        return ResponseEntity.ok().body(balanceDtoResponse);
    }

    @Transactional
    @PatchMapping("/balance/{balanceId}")
    public ResponseEntity<BalanceDtoResponse> updateBalance(@PathVariable(value = "balanceId") String balanceId, @RequestBody BalanceDto dto) {
        BalanceDtoResponse balanceDtoResponse = balanceService.updateBalance(balanceId, dto);
        return ResponseEntity.ok().body(balanceDtoResponse);
    }
}
