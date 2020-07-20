package com.lambdaschool.schools.handlers;


import com.lambdaschool.schools.exceptions.ResourceFoundException;
import com.lambdaschool.schools.exceptions.ResourceNotFoundException;
import com.lambdaschool.schools.models.ErrorDetails;
import com.lambdaschool.schools.services.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{
    @Autowired
    private HelperFunctions helperFunctions;

    //default constuctor, add super just incase you add stuff later, not required but helpful sometimes
    public RestExceptionHandler()
    {
        super();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe)
    {
        ErrorDetails errorDetail = new ErrorDetails();

        errorDetail.setTitle("Resource Not Found");
        errorDetail.setTimestamp(new Date());
        errorDetail.setDetail(rnfe.getMessage());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());

        errorDetail.setErrors(helperFunctions.getConstraintViolation(rnfe));
        return new ResponseEntity<>(errorDetail, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceFoundException.class)
    public ResponseEntity<?> handleResourceFoundException(ResourceNotFoundException rfe)
    {
        ErrorDetails errorDetail = new ErrorDetails();

        errorDetail.setTitle("Resource Not Found");
        errorDetail.setTimestamp(new Date());
        errorDetail.setDetail(rfe.getMessage());
        errorDetail.setStatus(HttpStatus.BAD_REQUEST.value());
        errorDetail.setDeveloperMessage(rfe.getClass().getName());

        errorDetail.setErrors(helperFunctions.getConstraintViolation(rfe));
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return super.handleExceptionInternal(ex, body, headers, status, request);
        ErrorDetails errorDetail = new ErrorDetails();
        errorDetail.setTimestamp(new Date());
        errorDetail.setStatus(status.value());
        errorDetail.setDetail(ex.getMessage());
        errorDetail.setTitle("Rest Internal Exception");
        errorDetail.setDeveloperMessage(ex.getClass().getName());

        errorDetail.setErrors(helperFunctions.getConstraintViolation(ex));
        return new ResponseEntity<>(errorDetail, null, status);
    }
}
