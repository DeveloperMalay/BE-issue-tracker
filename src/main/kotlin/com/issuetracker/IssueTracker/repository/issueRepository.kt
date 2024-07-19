package com.issuetracker.IssueTracker.repository

import com.issuetracker.IssueTracker.model.Issue
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IssueRepository : JpaRepository<Issue, Long>