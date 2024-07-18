package com.issuetracker.IssueTracker.dto

import java.time.LocalDateTime


data class IssueDto(
    val id: Long,
    val title: String,
    val description: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)