package com.issuetracker.IssueTracker.service

import com.issuetracker.IssueTracker.datasource.IssueDataSource
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class IssueServiceTest{
    private val dataSource:IssueDataSource= mockk(relaxed = true)
    private val issueService=IssueService(dataSource)
    @Test
    fun `should call it's data source to retrieve banks`(){
        //given

        //when
        issueService.getIssues()
        //then
        verify (exactly = 1){ dataSource.retrieveIssues() }
    }
}