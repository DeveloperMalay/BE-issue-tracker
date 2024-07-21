package com.issuetracker.IssueTracker.controller


import com.issuetracker.IssueTracker.dto.IssueDto
import com.issuetracker.IssueTracker.model.Issue
import com.issuetracker.IssueTracker.service.IssueService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/issues")
class IssueController(private val service:IssueService) {

    @GetMapping
    fun getIssues():Collection<Issue> = service.getIssues()

    @PostMapping
    fun createIssue(@RequestBody issue: Issue): ResponseEntity<Issue> {
        val createdIssue = service.createIssue(issue)
        return ResponseEntity(createdIssue, HttpStatus.CREATED)
    }

}