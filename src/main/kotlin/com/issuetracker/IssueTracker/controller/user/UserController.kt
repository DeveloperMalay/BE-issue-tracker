package com.issuetracker.IssueTracker.controller.user

import com.issuetracker.IssueTracker.model.Role
import com.issuetracker.IssueTracker.model.User
import com.issuetracker.IssueTracker.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@RestController
@RequestMapping("api/user")
class UserController(
    private val service: UserService
) {
    @GetMapping
    fun getUsers():Collection<User> = service.getUsers()

    @PostMapping
    fun createUser(@RequestBody user: UserRequest): UserResponse {
        val createdUser = service.createUser(user=user.toModel())
        return createdUser?.toResponse()?: throw  ResponseStatusException(HttpStatus.BAD_REQUEST, "Can not create user ")
    }

    private fun UserRequest.toModel(): User =User(
        id=UUID.randomUUID(),
        email=this.email,
        password = this.password,
        role = Role.USER
    )
    private fun User.toResponse(): UserResponse = UserResponse(uuid = this.id ?: UUID.randomUUID(), email=this.email)
    @GetMapping("/{id}")
    fun getUser(@PathVariable id: UUID): User?=service.getUser(id)

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id:UUID) = service.deleteUser(id)

    @GetMapping("/{email}")
    fun getUserByEmail(@PathVariable email: String)=service.findUserByEmail(email)
}