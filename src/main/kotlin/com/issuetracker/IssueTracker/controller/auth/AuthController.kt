package com.issuetracker.IssueTracker.controller.auth

import com.issuetracker.IssueTracker.service.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authenticationService: AuthenticationService
) {
    @PostMapping
    fun authenticate(@RequestBody authRequest:AuthenticationRequest ):AuthenticationResponse {

        val res =authenticationService.authentication(authRequest);
        println("response==========> $res ")
       return  res;
    }

}