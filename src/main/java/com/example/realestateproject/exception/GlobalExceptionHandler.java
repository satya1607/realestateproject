package com.example.realestateproject.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public String handleResourceNotFound(ResourceNotFoundException ex, Model model){
//        model.addAttribute("error",ex.getMessage());
//        return "error";
//    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);

    }


    @ExceptionHandler(EmailAlreadyExistException.class)
    public String handleEmailExist(EmailAlreadyExistException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        return "error";
    }

//    @ExceptionHandler(Exception.class)
//    public String handleglobalexception(Exception ex, Model model){
//        model.addAttribute("error","SOmething went wrong."+ex.getMessage());
//        return "error";
//    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleglobalexception(Exception ex) {
        return new ResponseEntity<>("An error occuried " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
