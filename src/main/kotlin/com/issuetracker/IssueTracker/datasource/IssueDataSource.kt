package com.issuetracker.IssueTracker.datasource

import com.issuetracker.IssueTracker.model.Issue

interface IssueDataSource {
    fun retrieveIssues():Collection<Issue>
    fun createIssue(issue: Issue):Issue
}