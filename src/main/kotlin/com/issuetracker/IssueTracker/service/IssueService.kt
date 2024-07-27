package com.issuetracker.IssueTracker.service

import com.issuetracker.IssueTracker.datasource.IssueDataSource
import com.issuetracker.IssueTracker.model.Issue
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class IssueService( @Qualifier("database") private val dataSource: IssueDataSource) {
    fun getIssues():Collection<Issue> = dataSource.retrieveIssues()
    fun createIssue(issue: Issue):Issue=dataSource.createIssue(issue)
    fun getIssue(id:Long): Issue? =dataSource.retrieveIssue(id)
    fun deleteIssue(id: Long) = dataSource.deleteIssue(id)
    fun updateIssue(issue: Issue): Issue? =dataSource.modifyIssue(issue)
}