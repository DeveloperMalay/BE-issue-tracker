package com.issuetracker.IssueTracker.controller.auth

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String
)

