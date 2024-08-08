package com.issuetracker.IssueTracker.service

import com.issuetracker.IssueTracker.config.JwtProperties
import com.issuetracker.IssueTracker.exception.JwtTokenException
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
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
       try {
           val parser =Jwts.parser().verifyWith(secretKey).build()
           println("secret key------------> $secretKey")
           val claim= parser.parseSignedClaims(token).payload;
           println("claim $claim")
           return  claim;
       }  catch (e: SignatureException) {
           throw JwtTokenException("Invalid JWT signature: ${e.message}")
       }catch (e: ExpiredJwtException) {
           throw JwtTokenException("JWT token is expired: ${e.message}")
       } catch (e: MalformedJwtException) {
           throw JwtTokenException("Malformed JWT token: ${e.message}")
       } catch (e: UnsupportedJwtException) {
           throw JwtTokenException("Unsupported JWT token: ${e.message}")
       } catch (e: IllegalArgumentException) {
           throw JwtTokenException("JWT claims string is empty: ${e.message}")
       }catch (e: JwtTokenException) {
           throw JwtTokenException("Invalid JWT signature: ${e.message}")
       }
    }
}