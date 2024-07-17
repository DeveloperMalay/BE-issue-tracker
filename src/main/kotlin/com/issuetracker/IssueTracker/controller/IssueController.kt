package com.issuetracker.IssueTracker.controller


import com.issuetracker.IssueTracker.dto.IssueDto
import com.issuetracker.IssueTracker.model.Issue
import com.issuetracker.IssueTracker.service.IssueService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/issues")
class IssueController(private val service:IssueService) {

    @GetMapping
    fun getIssues():Collection<Issue> = service.getIssues()


}