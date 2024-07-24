package com.issuetracker.IssueTracker.repository

import com.issuetracker.IssueTracker.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long>{
    @Query("SELECT u FROM User u WHERE u.email = :email")
    fun findByEmail(@Param("email") email: String): User?

    @Query("SELECT u FROM User u WHERE u.id = :id")
    fun findById(id: UUID): User?
}