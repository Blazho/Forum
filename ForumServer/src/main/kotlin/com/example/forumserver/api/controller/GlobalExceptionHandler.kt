package com.example.forumserver.api.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

//@RestControllerAdvice
class GlobalExceptionHandler {

    //todo make use of ErrorResponse
    @ExceptionHandler()
    fun handleException(e: Exception): ResponseEntity<Map<String, String>> {
        val response = mapOf("error" to e.message!!)
        return ResponseEntity(response, HttpStatus.CONFLICT)
    }
}