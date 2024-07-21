package com.issuetracker.IssueTracker.datasource.database

import com.issuetracker.IssueTracker.datasource.IssueDataSource
import com.issuetracker.IssueTracker.model.Issue
import com.issuetracker.IssueTracker.repository.IssueRepository
import org.springframework.stereotype.Repository

@Repository("database")
class DataBaseDataSource(  private val issueRepository: IssueRepository): IssueDataSource {
    override fun retrieveIssues(): Collection<Issue> {
        return issueRepository.findAll()
    }

    override fun createIssue(issue: Issue): Issue {
        return issueRepository.save(issue)
    }
}