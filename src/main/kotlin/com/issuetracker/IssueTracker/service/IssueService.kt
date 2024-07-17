package com.issuetracker.IssueTracker.service

import com.issuetracker.IssueTracker.datasource.IssueDataSource
import com.issuetracker.IssueTracker.model.Issue
import org.springframework.stereotype.Service

@Service
class IssueService(private val dataSource: IssueDataSource) {
    fun getIssues():Collection<Issue> = dataSource.retrieveIssues()
}