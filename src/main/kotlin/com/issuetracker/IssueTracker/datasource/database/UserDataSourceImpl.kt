package com.issuetracker.IssueTracker.datasource.database

import com.issuetracker.IssueTracker.datasource.UserDataSource
import com.issuetracker.IssueTracker.model.User
import com.issuetracker.IssueTracker.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository

@Repository
class UserDataSourceImpl(
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder
): UserDataSource {
    override fun retrieveUsers(): Collection<User> {
     return userRepository.findAll()
    }

    override fun createUser(user: User): User {
        val updatedUser =user.copy(password = encoder.encode(user.password))
      return  userRepository.save(updatedUser)
    }

    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    override fun findById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    override fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }
}