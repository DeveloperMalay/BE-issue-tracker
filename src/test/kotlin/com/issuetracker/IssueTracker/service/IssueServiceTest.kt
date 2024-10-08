package com.issuetracker.IssueTracker.service

import com.issuetracker.IssueTracker.datasource.database.IssueDataSourceImpl
import com.issuetracker.IssueTracker.model.Issue
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

internal class IssueServiceTest {

    private val dataSource: IssueDataSourceImpl = mockk(relaxed = true)

    private val issueService = IssueService(dataSource = dataSource)

    @Test
    fun `should call its data source to retrieve issues`() {
        // given
        val pageable: PageRequest = PageRequest.of(0, 10)
        // when
         issueService.getIssues(pageable)
        // then
        verify(exactly = 1) { dataSource.retrieveIssues(pageable) }
    }
}