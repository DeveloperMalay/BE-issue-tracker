package com.issuetracker.IssueTracker.service

import com.issuetracker.IssueTracker.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService (
    jwtProperties: JwtProperties
){
    private val secretKey = Keys.hmacShaKeyFor(
        jwtProperties.key.toByteArray()
    )

    fun  generate(
        userDetails: UserDetails,
        expirationDate: Date,
        additionalClaims: Map<String,Any> = emptyMap()
        ):String =
        Jwts.builder().
        claims().
        subject(userDetails.username).
        issuedAt(Date(System.currentTimeMillis())).
        expiration(expirationDate).
        add(additionalClaims).
        and().
        signWith(secretKey).
        compact()

    fun extractEmail(token: String): String? = getAllClaims(token.trim()).subject

    fun isExpired(token: String):Boolean = getAllClaims(token.trim()).expiration.before(Date(System.currentTimeMillis()))

    fun isValid(token: String,userDetails: UserDetails):Boolean {
        val email=extractEmail(token)
        return  userDetails.username== email && !isExpired(token)
    }

    private fun getAllClaims(token: String): Claims{
        val parser =Jwts.parser().verifyWith(secretKey).build()
        println("secret key------------> $secretKey")
        val claim= parser.parseSignedClaims(token).payload;
        println("claim $claim")
        return  claim;
    }
}