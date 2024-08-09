package com.issuetracker.IssueTracker.datasource.database

import com.issuetracker.IssueTracker.model.Issue
import com.issuetracker.IssueTracker.repository.IssueRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
//import org.mockito.Mockito.mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.util.*


@SpringBootTest
@ActiveProfiles("test")
class IssueDataSourceImplTest {

    private val issueRepository = mock(IssueRepository::class.java)
    private val issueDataSource = IssueDataSourceImpl(issueRepository)

    @Test
    fun `should retrieve issue successfully`() {
        //given
        val issue = Issue(id = 1L, title = "Test Issue", description = "Description", status = "Open")
        `when`(issueRepository.findById(1L)).thenReturn(Optional.of(issue))
        //when
        val result = issueDataSource.retrieveIssue(1L)
        //then
        assertNotNull(result)
        assertEquals(issue, result)
    }

    @Test
    fun `should throw NoSuchElementException when issue not found`() {
        `when`(issueRepository.findById(1L)).thenReturn(Optional.empty())

        val exception = assertThrows<NoSuchElementException> {
            issueDataSource.retrieveIssue(1L)
        }

        assertEquals("Issue not found with id: 1", exception.message)
    }

    @Test
    fun `should delete issue successfully`() {
        `when`(issueRepository.existsById(1L)).thenReturn(true)

        issueDataSource.deleteIssue(1L)

        verify(issueRepository, times(1)).deleteById(1L)
    }

    @Test
    fun `should throw NoSuchElementException when deleting non-existing issue`() {
        `when`(issueRepository.existsById(1L)).thenReturn(false)

        val exception = assertThrows<NoSuchElementException> {
            issueDataSource.deleteIssue(1L)
        }

        assertEquals("Issue not found with id: 1", exception.message)
    }

    @Test
    fun `should create issue successfully`() {
        val issue = Issue(id = 1L, title = "New Issue", description = "Description", status = "Open")
        `when`(issueRepository.save(issue)).thenReturn(issue)

        val result = issueDataSource.createIssue(issue)

        assertEquals(issue, result)
        verify(issueRepository, times(1)).save(issue)
    }

    @Test
    fun `should modify issue successfully`() {
        val existingIssue = Issue(id = 1L, title = "Old Title", description = "Old Description", status = "Open")
        val modifiedIssue = Issue(id = 1L, title = "New Title", description = "New Description", status = "Closed")
        `when`(issueRepository.findById(1L)).thenReturn(Optional.of(existingIssue))
        `when`(issueRepository.save(existingIssue)).thenReturn(modifiedIssue)

        val result = issueDataSource.modifyIssue(modifiedIssue)

        assertEquals(modifiedIssue, result)
        assertEquals("New Title", existingIssue.title)
        assertEquals("New Description", existingIssue.description)
        assertEquals("Closed", existingIssue.status)
    }

    @Test
    fun `should throw NoSuchElementException when modifying non-existing issue`() {
        val issue = Issue(id = 1L, title = "Title", description = "Description", status = "Open")
        `when`(issueRepository.findById(1L)).thenReturn(Optional.empty())

        val exception = assertThrows<NoSuchElementException> {
            issueDataSource.modifyIssue(issue)
        }

        assertEquals("Issue with ID 1 not found", exception.message)
    }

    @Test
    fun `should throw NoSuchElementException when modifying issue with null ID`() {
        val issue = Issue(id = null, title = "Title", description = "Description", status = "Open")

        val exception = assertThrows<NoSuchElementException> {
            issueDataSource.modifyIssue(issue)
        }

        assertEquals("No Issue Id found", exception.message)
    }
}
