package com.issuetracker.IssueTracker.controller.issue

import com.issuetracker.IssueTracker.model.Issue

data class IssueResponse(
    val totalPages: Int,
    val currentPageNumber: Int,
    val numberOfElements: Int,
    val empty: Boolean,
    val content: List<Issue>,

)