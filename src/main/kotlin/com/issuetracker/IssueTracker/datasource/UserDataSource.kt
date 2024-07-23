package com.issuetracker.IssueTracker.datasource

import com.issuetracker.IssueTracker.model.User

interface UserDataSource {
    fun retrieveUsers():Collection<User>
    fun createUser(user: User): User
    fun findByEmail(email: String): User?
    fun findById(id: Long):User?
    fun deleteUser(id: Long)
}