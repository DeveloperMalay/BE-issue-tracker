package com.issuetracker.IssueTracker.datasource.database

import com.issuetracker.IssueTracker.model.User
import com.issuetracker.IssueTracker.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.Test

@SpringBootTest
@ActiveProfiles("test")
class UserDataSourceImplTest{
    @Test
    fun `should find user by email`() {
        // Given
        val userRepository = mock(UserRepository::class.java)
        val email = "email2@gmail.com"
        val user = User(id = 1L, email = email, password = "test@123")

        `when`(userRepository.findByEmail(email)).thenReturn(user)

        // When
        val foundUser = userRepository.findByEmail(email)

        // Then
        assertEquals(user, foundUser)
    }
}