package com.issuetracker.IssueTracker.datasource.database

import com.issuetracker.IssueTracker.datasource.UserDataSource
import com.issuetracker.IssueTracker.model.User
import com.issuetracker.IssueTracker.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class UserDataSourceImpl(
    private val userRepository: UserRepository,
    private val encoder: PasswordEncoder
): UserDataSource {
    override fun retrieveUsers(): Collection<User> {
     return userRepository.findAll()
    }

    override fun createUser(user: User): User? {
        val found =userRepository.findByEmail(user.email)
        return if(found==null){

        val updatedUser =user.copy(password = encoder.encode(user.password))
            return  userRepository.save(updatedUser)
        }else null
    }

    override fun findByEmail(email: String): User? {
        println("email: $email")
        val user =  userRepository.findByEmail(email)
        if (user != null) {
            println("User fetched from repository: ${user.email}")
        } else {
            println("No user found with email: $email")
        }
        return  user;
    }

    override fun findById(id: UUID): User? {
        return userRepository.findById(id)
    }

    override fun deleteUser(id: UUID) {
//        userRepository.deleteById(id)
    }
}