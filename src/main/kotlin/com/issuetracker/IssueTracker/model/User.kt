package com.issuetracker.IssueTracker.model

import jakarta.persistence.Entity
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null,
    val email: String,
    val password: String,
    val role: Role
)

enum class Role{
    ADMIN , USER
}