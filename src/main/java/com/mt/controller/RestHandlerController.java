package com.mt.controller;

import com.mt.exception.AlreadyException;
import com.mt.exception.BedRequestException;
import com.mt.exception.NotFoundException;
import com.mt.model.response.error.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestHandlerController {
    @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse>handlerGlobleException(Exception exception){
            ErrorResponse err = new ErrorResponse(exception.getMessage(), (short) 500);

            return ResponseEntity.status(500).body(err);
        }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse>handlerNotFoundException(NotFoundException exception){
        ErrorResponse err = new ErrorResponse(exception.getMessage(), (short) 404);

        return ResponseEntity.status(404).body(err);
    }



    @ExceptionHandler(AlreadyException.class)
    public ResponseEntity<ErrorResponse>handlerAlreadyException(AlreadyException exception){
        ErrorResponse err = new ErrorResponse(exception.getMessage(), (short) 409);

        return ResponseEntity.status(409).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>handlerBedRequestException(MethodArgumentNotValidException exception){
        String message = "Invalid value";
        for (ObjectError err: exception.getBindingResult().getAllErrors()){
            message = err.getDefaultMessage();
        }
        ErrorResponse err = new ErrorResponse(message, (short) 400);

        return ResponseEntity.status(400).body(err);
    }

}
