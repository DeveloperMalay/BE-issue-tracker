package com.issuetracker.IssueTracker.service

import com.issuetracker.IssueTracker.datasource.UserDataSource
import com.issuetracker.IssueTracker.model.User
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class UserService( private val dataSource: UserDataSource) {
    fun getUsers():Collection<User> = dataSource.retrieveUsers()
    fun createUser(user: User): User =dataSource.createUser(user)
    fun getUser(id:Long): User? =dataSource.findById(id)
    fun deleteUser(id: Long) = dataSource.deleteUser(id)
    fun findUserByEmail(email: String) =dataSource.findByEmail(email)
}