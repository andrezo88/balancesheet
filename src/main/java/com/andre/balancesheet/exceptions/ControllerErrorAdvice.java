package com.andre.balancesheet.exceptions;

import com.andre.balancesheet.dtos.ErrorResponse;
import com.andre.balancesheet.exceptions.service.IdNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class ControllerErrorAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ErrorResponse err = ErrorResponse.builder()
                .message("Requisicao invalida.")
                .status(400)
                .errors(getErrors(ex))
                .path(ex.getBindingResult().getObjectName())
                .build();
        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorResponse> idNotFoundException(IdNotFoundException ex, HttpServletRequest request){
        ErrorResponse err = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    private List<String> getErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }
}
