package com.andre.balancesheet.exceptions;

import com.andre.balancesheet.dto.ErrorResponse;
import com.andre.balancesheet.exceptions.service.BadRequestException;
import com.andre.balancesheet.exceptions.service.IdNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.zip.DataFormatException;

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

    @Test
    void shouldReturnDataFormatException() {
        DataFormatException exception = new DataFormatException("Id not found");

        ResponseEntity<ErrorResponse> errorResponseEntity = controllerErrorAdvice.handleDataFormatException(exception);
        ErrorResponse errorResponse = errorResponseEntity.getBody();

        assert errorResponse != null;
        assert errorResponse.getStatus() == 400;

        assertEquals( 400, errorResponseEntity.getStatusCode().value());
    }

    @Test
    void shouldReturnBadRequestException() {
        BadRequestException exception = new BadRequestException("Id not found");

        ResponseEntity<ErrorResponse> errorResponseEntity = controllerErrorAdvice.handleDataAfterDateNowException(exception);
        ErrorResponse errorResponse = errorResponseEntity.getBody();

        assert errorResponse != null;
        assert errorResponse.getStatus() == 400;

        assertEquals( 400, errorResponseEntity.getStatusCode().value());
    }

}
