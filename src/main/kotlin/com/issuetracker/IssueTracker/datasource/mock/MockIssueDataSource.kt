package com.issuetracker.IssueTracker.datasource.mock

import com.issuetracker.IssueTracker.datasource.IssueDataSource
import com.issuetracker.IssueTracker.model.Issue
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository("mock")
class MockIssueDataSource: IssueDataSource {
    val issueList=listOf(
        Issue(
            id = 1,
            title = "Bug in login feature",
            description = "Users are unable to login with their credentials",
            status = "OPEN",
            createdAt = LocalDateTime.of(2023, 7, 1, 12, 0),
            updatedAt = LocalDateTime.of(2023, 7, 1, 12, 0)
        ),
        Issue(
            id = 2,
            title = "Page not found error",
            description = "404 error on accessing the user profile page",
            status = "OPEN",
            createdAt = LocalDateTime.of(2023, 7, 2, 14, 30),
            updatedAt = LocalDateTime.of(2023, 7, 2, 14, 30)
        ),
        Issue(
            id = 3,
            title = "UI glitch on dashboard",
            description = "Graphs not rendering correctly on the dashboard",
            status = "OPEN",
            createdAt = LocalDateTime.of(2023, 7, 3, 9, 15),
            updatedAt = LocalDateTime.of(2023, 7, 3, 9, 15)
        ),
        Issue(
            id = 4,
            title = "Performance issue on search",
            description = "Search functionality is taking too long to return results",
            status = "OPEN",
            createdAt = LocalDateTime.of(2023, 7, 4, 10, 45),
            updatedAt = LocalDateTime.of(2023, 7, 4, 10, 45)
        ),
        Issue(
            id = 5,
            title = "Security vulnerability in payment gateway",
            description = "Potential SQL injection vulnerability in payment processing",
            status = "OPEN",
            createdAt = LocalDateTime.of(2023, 7, 5, 16, 20),
            updatedAt = LocalDateTime.of(2023, 7, 5, 16, 20)
        )
    )
    override fun retrieveIssues(): Collection<Issue> = issueList;
    override fun createIssue(issue: Issue): Issue {
        TODO("Not yet implemented")
    }

    override fun retrieveIssue(id: Long): Issue? {
        TODO("Not yet implemented")
    }

    override fun deleteIssue(id: Long) {
        TODO("Not yet implemented")
    }

    override fun modifyIssue(issue: Issue): Issue {
        TODO("Not yet implemented")
    }


}