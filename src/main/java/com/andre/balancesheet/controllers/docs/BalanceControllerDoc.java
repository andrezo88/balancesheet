package com.andre.balancesheet.controllers.docs;

import com.andre.balancesheet.dtos.BalanceDtoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BalanceControllerDoc {

    @Operation(summary = "Get all balances")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode="200", description = "Get all balances with success")
            })
    ResponseEntity<List<BalanceDtoResponse>> getAllBalances();

    @Operation(summary = "Get balance by id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode="200", description = "Get balance by id with success"),
                    @ApiResponse(responseCode="404", description = "Balance not found"),
                    @ApiResponse(responseCode="500", description = "Internal server error")
            })
    ResponseEntity<BalanceDtoResponse> getBalanceById(Long id);

    @Operation(summary = "Insert Balance")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode="201", description = "Balance inserted with success"),
                    @ApiResponse(responseCode="400", description = "Bad request, any field was wrongly sent"),
                    @ApiResponse(responseCode="500", description = "Internal server error")
            })
    ResponseEntity<Void> insertBalance(BalanceDtoResponse balanceDtoResponse);

    @Operation(summary = "Update balance")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode="200", description = "Balance updated with success"),
                    @ApiResponse(responseCode="400", description = "Bad request, any field was wrongly sent"),
                    @ApiResponse(responseCode="404", description = "Balance not found"),
                    @ApiResponse(responseCode="500", description = "Internal server error")
            })
    ResponseEntity<Void> updateBalance(BalanceDtoResponse balanceDtoResponse);

}
