package com.issuetracker.IssueTracker.model
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
data class Issue(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: String="",
    val title: String,
    val description: String,
    val status:String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)