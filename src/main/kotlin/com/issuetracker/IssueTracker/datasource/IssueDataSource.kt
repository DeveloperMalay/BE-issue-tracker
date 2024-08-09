package com.issuetracker.IssueTracker.datasource

import com.issuetracker.IssueTracker.model.Issue
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface IssueDataSource {
    fun retrieveIssues(pageable: Pageable): Page<Issue>
    fun createIssue(issue: Issue):Issue
    fun retrieveIssue(id: Long): Issue?
    fun deleteIssue(id: Long)
    fun modifyIssue( issue: Issue): Issue?
}