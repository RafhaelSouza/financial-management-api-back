package com.studies.financialmanagement.api.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @Getter
    @AllArgsConstructor
    public static class ErrorMessages {

        private String userMessage;
        private String developerMessage;

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        String userMessage = messageSource.getMessage("message.invalid", null, LocaleContextHolder.getLocale());
        String developerMessage = ex.getCause() != null ? ex.getCause().toString() : ex.toString();

        List<ErrorMessages> errorMessagesList = Arrays.asList(new ErrorMessages(userMessage, developerMessage));

        return handleExceptionInternal(ex, errorMessagesList, headers, HttpStatus.BAD_REQUEST, request);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        List<ErrorMessages> errorMessagesList = createErrorsList(ex.getBindingResult());

        return handleExceptionInternal(ex, errorMessagesList, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
                                                                       WebRequest request) {

        String userMessage = messageSource.getMessage("resource.not-found", null, LocaleContextHolder.getLocale());
        String developerMessage = ex.toString();

        List<ErrorMessages> errorMessagesList = Arrays.asList(new ErrorMessages(userMessage, developerMessage));

        return handleExceptionInternal(ex, errorMessagesList, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                        WebRequest request) {

        String userMessage = messageSource.getMessage("resource.not-allowed-operation", null, LocaleContextHolder.getLocale());
        String developerMessage = ExceptionUtils.getRootCauseMessage(ex);

        List<ErrorMessages> errorMessagesList = Arrays.asList(new ErrorMessages(userMessage, developerMessage));

        return handleExceptionInternal(ex, errorMessagesList, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    private List<ErrorMessages> createErrorsList(BindingResult bindingResult) {

        List<ErrorMessages> errorMessages = new ArrayList<>();

        for (FieldError fieldError: bindingResult.getFieldErrors()) {
            String userMessage = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            String developerMessage = fieldError.toString();
            errorMessages.add(new ErrorMessages(userMessage, developerMessage));
        }

        return errorMessages;

    }

}
