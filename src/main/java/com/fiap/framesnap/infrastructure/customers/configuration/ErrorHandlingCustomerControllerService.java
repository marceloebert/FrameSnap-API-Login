package com.fiap.framesnap.infrastructure.customers.configuration;


import com.fiap.framesnap.crosscutting.exception.NotFoundException;
import com.fiap.framesnap.crosscutting.exception.ValidationErrorResponse;
import com.fiap.framesnap.crosscutting.exception.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ErrorHandlingCustomerControllerService {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse notFoundException(NotFoundException e) {

        ValidationErrorResponse error = new ValidationErrorResponse();
        error.add(new Violation());

        return error;
    }
}
