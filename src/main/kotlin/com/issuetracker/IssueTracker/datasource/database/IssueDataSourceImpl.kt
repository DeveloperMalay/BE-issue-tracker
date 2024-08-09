package com.issuetracker.IssueTracker.datasource.database

import com.issuetracker.IssueTracker.datasource.IssueDataSource
import com.issuetracker.IssueTracker.exception.JwtTokenException
import com.issuetracker.IssueTracker.model.Issue
import com.issuetracker.IssueTracker.repository.IssueRepository
import io.jsonwebtoken.security.SignatureException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository("database")
class IssueDataSourceImpl(private val issueRepository: IssueRepository): IssueDataSource {
    override fun retrieveIssues(pageable: Pageable): Page<Issue> {
        return issueRepository.findAll(pageable)
    }

    override fun createIssue(issue: Issue): Issue {
        try {
            return issueRepository.save(issue)
        }catch (e: SignatureException){
            throw JwtTokenException("Invalid JWT signature: ${e.message}")
        }
    }

    override fun retrieveIssue(id: Long): Issue? {
        return issueRepository.findById(id)
            .orElseThrow { NoSuchElementException("Issue not found with id: $id") }
    }

    override fun deleteIssue(id: Long) {
        if (issueRepository.existsById(id)) {
            issueRepository.deleteById(id)
        } else {
            throw NoSuchElementException("Issue not found with id: $id")
        }
    }

    override fun modifyIssue(issue: Issue): Issue? {
        if (issue.id!=null) {
            val existingIssue:Issue =issueRepository.findById(issue.id).orElseThrow{
                NoSuchElementException("Issue with ID ${issue.id} not found")
            }
            existingIssue.title=issue.title;
            existingIssue.description=issue.description
            existingIssue.status=issue.status
            issueRepository.save(existingIssue)
            return issue
        } else {
            throw NoSuchElementException("No Issue Id found")
        }
    }


}