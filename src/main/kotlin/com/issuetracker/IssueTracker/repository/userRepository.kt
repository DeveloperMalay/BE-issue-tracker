package com.issuetracker.IssueTracker.repository

import com.issuetracker.IssueTracker.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long>{
    @Query("SELECT u FROM User u WHERE u.email = :email")
    fun findByEmail(@Param("email") email: String): User?

    @Query("SELECT u FROM User u WHERE u.id = :id")
    fun findById(@Param("id") id: UUID): User?

    @Modifying
    @Query("DELETE FROM User u WHERE u.id = :id")
    fun deleteById(@Param("id") id: UUID)
}