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

    @Column(nullable = false, unique = true)
    val email: String = "",

    @Column(nullable = false)
    val password: String = "",

    @Enumerated(EnumType.ORDINAL)
    val role: Role = Role.USER
)

enum class Role {
    ADMIN,
    USER
}
