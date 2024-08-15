//package com.issuetracker.IssueTracker.controller.user
//
//import com.issuetracker.IssueTracker.model.Role
//import com.issuetracker.IssueTracker.model.User
//import com.issuetracker.IssueTracker.service.UserService
//import org.junit.jupiter.api.Assertions.assertEquals
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.assertThrows
//import org.mockito.InjectMocks
//import org.mockito.Mock
//import org.mockito.Mockito.*
//import org.mockito.junit.jupiter.MockitoExtension
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.http.HttpStatus
//import org.springframework.web.server.ResponseStatusException
//import java.util.*
//import org.junit.jupiter.api.extension.ExtendWith
//import org.springframework.test.context.ActiveProfiles
//import org.springframework.web.bind.annotation.RequestBody
//
//@ExtendWith(MockitoExtension::class)
//@SpringBootTest
//@ActiveProfiles("test")
//class UserControllerTest {
//
//    @Mock
//    private lateinit var service: UserService
//
//    @InjectMocks
//    private lateinit var controller: UserController
//
//    @Test
//    fun `should return list of users`() {
//        val users = listOf(
//            User(UUID.randomUUID(), "test@example.com", "password", Role.USER)
//        )
//        `when`(service.getUsers()).thenReturn(users)
//
//        val result = controller.getUsers()
//
//        assertEquals(users, result)
//    }
//
//    @Test
//    fun `should create a user and return response`() {
//        val userRequest = UserRequest("test@example.com", "password")
//        val createdUser = User(UUID.randomUUID(), "test@example.com", "password", Role.USER)
//        val userResponse = UserResponse(createdUser.id, createdUser.email)
//
//        `when`(service.createUser(any())).thenReturn(createdUser)
//
//        val result = controller.createUser(userRequest)
//
//        assertEquals(userResponse, result)
//    }
//
//    @Test
//    fun `should throw exception when user creation fails`() {
//        val userRequest = UserRequest("test@example.com", "password")
//
//        `when`(service.createUser(any())).thenReturn(null)
//
//        val exception = assertThrows<ResponseStatusException> {
//            controller.createUser(userRequest)
//        }
//
//        assertEquals(HttpStatus.BAD_REQUEST, exception.status)
//        assertEquals("Can not create user ", exception.reason)
//    }
//
//    @Test
//    fun `should return user by id`() {
//        val userId = UUID.randomUUID()
//        val user = User(userId, "test@example.com", "password", Role.USER)
//
//        `when`(service.getUser(userId)).thenReturn(user)
//
//        val result = controller.getUser(userId)
//
//        assertEquals(user, result)
//    }
//
//    @Test
//    fun `should delete user by id`() {
//        val userId = UUID.randomUUID()
//
//        doNothing().`when`(service).deleteUser(userId)
//
//        controller.deleteUser(userId)
//
//        verify(service, times(1)).deleteUser(userId)
//    }
//
//    @Test
//    fun `should return user by email`() {
//        val email = "test@example.com"
//        val user = User(UUID.randomUUID(), email, "password", Role.USER)
//
//        `when`(service.findUserByEmail(email)).thenReturn(user)
//
//        val result = controller.getUserByEmail(email)
//
//        assertEquals(user, result)
//    }
//}
