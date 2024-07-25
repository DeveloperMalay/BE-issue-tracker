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

    override fun retrieveIssue(id: Long): Issue? {
        val issue = issueRepository.findById(id)
        return if (issue.isPresent) issue.get() else null
    }

    override fun deleteIssue(id: Long) {
        if (issueRepository.existsById(id)) {
            issueRepository.deleteById(id)
        } else {
            throw NoSuchElementException("Issue not found with id: $id")
        }
    }

    override fun modifyIssue(issue: Issue): Issue? {
        if (issueRepository.existsById(issue.id!!)) {
            issueRepository.deleteById(issue.id)
            issueRepository.save(issue)
            return issue
        } else {
            throw NoSuchElementException("Issue not found with id: ${issue.id}")
        }
    }


}