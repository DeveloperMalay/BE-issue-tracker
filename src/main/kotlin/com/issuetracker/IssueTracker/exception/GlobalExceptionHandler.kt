package com.issuetracker.IssueTracker.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler


@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(JwtTokenException::class)
    fun handleJwtTokenException(e: JwtTokenException): ResponseEntity<String> {
        return ResponseEntity("JWT error: ${e.message}", HttpStatus.UNAUTHORIZED)
    }
}