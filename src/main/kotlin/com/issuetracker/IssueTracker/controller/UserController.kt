package com.issuetracker.IssueTracker.controller

import com.issuetracker.IssueTracker.model.Issue
import com.issuetracker.IssueTracker.model.User
import com.issuetracker.IssueTracker.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/users")
class UserController(
    private val service: UserService
) {
    @GetMapping
    fun getUsers():Collection<User> = service.getUsers()

    @PostMapping
    fun createIssue(@RequestBody user: User): ResponseEntity<User> {
        val createdIssue = service.createUser(user)
        return ResponseEntity(createdIssue, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getIssue(@PathVariable id: Long): User?=service.getUser(id)

    @DeleteMapping("/{id}")
    fun deleteIssue(@PathVariable id:Long) = service.deleteUser(id)

    @GetMapping("/{email}")
    fun getByEmail(@PathVariable email: String)=service.findUserByEmail(email)
}