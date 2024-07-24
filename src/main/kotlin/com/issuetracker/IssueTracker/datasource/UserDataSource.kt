package com.issuetracker.IssueTracker.datasource

import com.issuetracker.IssueTracker.model.User
import java.util.UUID

interface UserDataSource {
    fun retrieveUsers():Collection<User>
    fun createUser(user: User): User?
    fun findByEmail(email: String): User?
    fun findById(id: UUID):User?
    fun deleteUser(id: UUID)
}