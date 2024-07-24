package com.issuetracker.IssueTracker.service

import com.issuetracker.IssueTracker.config.JwtProperties
import com.issuetracker.IssueTracker.controller.auth.AuthenticationRequest
import com.issuetracker.IssueTracker.controller.auth.AuthenticationResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import java.util.Date

@Service
class AuthenticationService (
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties
){
    fun authentication(authRequest: AuthenticationRequest):AuthenticationResponse {
        println("request===========>${authRequest.email} ${authRequest.password}")
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.email,
                authRequest.password,
            )
        )

        val user =userDetailsService.loadUserByUsername(authRequest.email)
        println("user -------> $user")
        val accessToken= tokenService.generate(
            userDetails = user,
            expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
        )
        return AuthenticationResponse(
            accessToken = accessToken
        )
    }

}
