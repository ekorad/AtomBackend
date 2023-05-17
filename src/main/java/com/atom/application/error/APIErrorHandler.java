package com.atom.application.error;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.UnsatisfiedServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * <b>Handler for exceptions produced by the APIs</b>
 */
@ControllerAdvice
public class APIErrorHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "HTTP message is not readable";
        String error = ex.getLocalizedMessage();
        APIError apiError = new APIError(status, message, error);
        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "Request method is not supported";
        String error = ex.getLocalizedMessage();
        APIError apiError = new APIError(status, message, error);
        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String message = "Path variable is missing";
        String error = ex.getLocalizedMessage();
        APIError apiError = new APIError(status, message, error);
        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "Specified media type is not supported";
        String error = ex.getLocalizedMessage();
        APIError apiError = new APIError(status, message, error);
        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "Request parameter is missing";
        String error = ex.getLocalizedMessage();
        APIError apiError = new APIError(status, message, error);
        return handleExceptionInternal(ex, apiError, headers, status, request);
    }

    @ExceptionHandler({ UnsatisfiedServletRequestParameterException.class })
    protected ResponseEntity<Object> handleUnsatisfiedServletRequestParameter(
            UnsatisfiedServletRequestParameterException ex, WebRequest request) {
        String message = "Request parameters missing";
        Stream.of(ex.getParamConditions()).map(param -> "Missing request parameter '" + param + "'").toArray();
        List<String> errors = Stream.of(ex.getParamConditions())
                .map(param -> "Missing request parameter '" + param + "'").collect(Collectors.toList());
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, message, errors);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        String message = "Constraint violation has occured: specified variable values do not respect imposed constraints";
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> cv : ex.getConstraintViolations()) {
            errors.add(cv.getRootBeanClass().getName() + " - " + cv.getPropertyPath() + ": " + cv.getMessage());
        }

        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, message, errors);
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        String message = "One ore more entities have not been found using the provided query parameters";
        APIError apiError = new APIError(HttpStatus.NOT_FOUND, message, ex.getLocalizedMessage());
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    @ExceptionHandler({ IllegalArgumentException.class })
    protected ResponseEntity<Object> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        String message = "Invalid value passed as request parameter";
        APIError apiError = new APIError(HttpStatus.BAD_REQUEST, message, ex.getLocalizedMessage());
        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    @ExceptionHandler({ DataIntegrityViolationException.class })
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
            WebRequest request) {
        String message = "";
        String error = "";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
            org.hibernate.exception.ConstraintViolationException cause = (org.hibernate.exception.ConstraintViolationException) ex
                    .getCause();
            SQLException sqlEx = cause.getSQLException();
            switch (sqlEx.getErrorCode()) {
                case 1062: // duplicate entry
                    message = "An entity already exists with the same values for its unique attributes";
                    break;
                default:
                    message = "Unkown error";
            }
            error = sqlEx.getLocalizedMessage();
            status = HttpStatus.BAD_REQUEST;
        } else {
            error = ex.getLocalizedMessage();
        }

        APIError apiError = new APIError(status, message, error);

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

}
