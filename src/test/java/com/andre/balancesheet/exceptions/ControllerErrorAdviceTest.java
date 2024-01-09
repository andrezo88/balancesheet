package com.andre.balancesheet.exceptions;

import com.andre.balancesheet.dtos.ErrorResponse;
import com.andre.balancesheet.exceptions.service.IdNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ControllerErrorAdviceTest {

    @InjectMocks
    ControllerErrorAdvice controllerErrorAdvice;

    @Test
    void shouldReturnIdNotFoundException() {
        IdNotFoundException exception = new IdNotFoundException("Id not found");

        ResponseEntity<ErrorResponse> errorResponseEntity = controllerErrorAdvice.handleIdNotFoundException(exception);
        ErrorResponse errorResponse = errorResponseEntity.getBody();

        assert errorResponse != null;
        assert errorResponse.getMessage().equals("Id not found");
        assert errorResponse.getStatus() == 404;

        assertEquals( 404, errorResponseEntity.getStatusCode().value());
    }

}
