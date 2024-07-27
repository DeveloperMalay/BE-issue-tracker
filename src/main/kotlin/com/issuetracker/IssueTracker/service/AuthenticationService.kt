package com.issuetracker.IssueTracker.service

import com.issuetracker.IssueTracker.config.JwtProperties
import com.issuetracker.IssueTracker.controller.auth.AuthenticationRequest
import com.issuetracker.IssueTracker.controller.auth.AuthenticationResponse
import com.issuetracker.IssueTracker.controller.auth.TokenResponse
import com.issuetracker.IssueTracker.repository.RefreshTokenRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date

@Service
class AuthenticationService (
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
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
        val accessToken= generateAccessToken(user)
        println("access token $accessToken")
        val refreshToken=generateRefreshToken(user)
        refreshTokenRepository.save(refreshToken,user)
        return AuthenticationResponse(
            accessToken = accessToken,
            refreshToken=refreshToken,
        )
    }

    fun refreshAccessToken(token: String): String? {
        val extractEmail =tokenService.extractEmail(token)
        return extractEmail?.let { email->
            val currentUserDetails =userDetailsService.loadUserByUsername(email)
            val refreshTokenUserDetails= refreshTokenRepository.findUserDetailsByToken(token)

            if(!tokenService.isExpired(token)&& currentUserDetails.username==refreshTokenUserDetails?.username)
                generateAccessToken(currentUserDetails)
            else null
        }
    }

    private fun generateAccessToken(user: UserDetails)=tokenService.generate(
        userDetails = user,
        expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)
    )

    private fun generateRefreshToken(user: UserDetails)=tokenService.generate(
        userDetails = user,
        expirationDate = Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
    )

}
