package com.issuetracker.IssueTracker.datasource.database


import com.issuetracker.IssueTracker.model.User
import com.issuetracker.IssueTracker.repository.UserRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles
import java.util.UUID

@SpringBootTest
@ActiveProfiles("test")
class UserDataSourceImplTest {

    private val userRepository = mock(UserRepository::class.java)
    private val encoder = mock(PasswordEncoder::class.java)
    private val userDataSource = UserDataSourceImpl(userRepository, encoder)

    @Test
    fun `should retrieve users successfully`() {
        val user = User(id = UUID.randomUUID(), email = "test@example.com", password = "password",)
        `when`(userRepository.findAll()).thenReturn(listOf(user))

        val result = userDataSource.retrieveUsers()

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(user, result.first())
    }

    @Test
    fun `should create user successfully`() {
        val user = User(id = UUID.randomUUID(), email = "new@example.com", password = "password", )
        val encodedPassword = "encodedPassword"
        `when`(userRepository.findByEmail(user.email)).thenReturn(null)
        `when`(encoder.encode(user.password)).thenReturn(encodedPassword)
        `when`(userRepository.save(user.copy(password = encodedPassword))).thenReturn(user.copy(password = encodedPassword))

        val result = userDataSource.createUser(user)

        assertNotNull(result)
        assertEquals(user.email, result?.email)
        assertEquals(encodedPassword, result?.password)
        verify(userRepository, times(1)).save(user.copy(password = encodedPassword))
    }

    @Test
    fun `should return null when creating existing user`() {
        val user = User(id = UUID.randomUUID(), email = "existing@example.com", password = "password", )
        `when`(userRepository.findByEmail(user.email)).thenReturn(user)

        val result = userDataSource.createUser(user)

        assertNull(result)
        verify(userRepository, never()).save(any())
    }

    @Test
    fun `should find user by email successfully`() {
        val user = User(id = UUID.randomUUID(), email = "find@example.com", password = "password", )
        `when`(userRepository.findByEmail(user.email)).thenReturn(user)

        val result = userDataSource.findByEmail(user.email)

        assertNotNull(result)
        assertEquals(user.email, result?.email)
    }

    @Test
    fun `should return null when finding user by email`() {
        val email = "notfound@example.com"
        `when`(userRepository.findByEmail(email)).thenReturn(null)

        val result = userDataSource.findByEmail(email)

        assertNull(result)
    }

    @Test
    fun `should find user by ID successfully`() {
        val userId = UUID.randomUUID()
        val user = User(id = userId, email = "findbyid@example.com", password = "password", )
        `when`(userRepository.findById(userId)).thenReturn(user)

        val result = userDataSource.findById(userId)

        assertNotNull(result)
        assertEquals(userId, result?.id)
    }

    @Test
    fun `should delete user successfully`() {
        val userId = UUID.randomUUID()
        doNothing().`when`(userRepository).deleteById(userId)

        userDataSource.deleteUser(userId)

        verify(userRepository, times(1)).deleteById(userId)
    }
}
