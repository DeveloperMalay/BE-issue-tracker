package com.issuetracker.IssueTracker.controller.issue

import com.fasterxml.jackson.databind.ObjectMapper
import com.issuetracker.IssueTracker.datasource.IssueDataSource
import com.issuetracker.IssueTracker.exception.JwtTokenException
import com.issuetracker.IssueTracker.model.Issue
import com.issuetracker.IssueTracker.service.IssueService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import javax.naming.AuthenticationException

@WebMvcTest(IssueController::class)
class IssueControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var issueService: IssueService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val issue = Issue(id = 1L, title = "Test Issue", description = "Test Description", status = "OPEN")

    @BeforeEach
    fun setup() {
        // Any necessary setup before each test
    }

    @Test
    fun `should retrieve issues successfully`() {
        val pageOfIssues = PageImpl(listOf(issue), PageRequest.of(0, 10), 1)
        `when`(issueService.getIssues(any())).thenReturn(pageOfIssues)

        mockMvc.perform(get("/api/issues"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.totalPages").value(1))
            .andExpect(jsonPath("$.content[0].title").value("Test Issue"))
    }

    @Test
    fun `should create issue successfully`() {
        `when`(issueService.createIssue(any())).thenReturn(issue)

        mockMvc.perform(post("/api/issues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(issue)))
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.title").value("Test Issue"))
    }

    @Test
    fun `should get issue by ID successfully`() {
        `when`(issueService.getIssue(1L)).thenReturn(issue)

        mockMvc.perform(get("/api/issues/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.title").value("Test Issue"))
    }

    @Test
    fun `should update issue successfully`() {
        val updatedIssue = issue.copy(title = "Updated Title")
        `when`(issueService.updateIssue(any())).thenReturn(updatedIssue)

        mockMvc.perform(patch("/api/issues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(updatedIssue)))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.title").value("Updated Title"))
    }

    @Test
    fun `should return not found when updating non-existent issue`() {
        `when`(issueService.updateIssue(any())).thenReturn(null)

        mockMvc.perform(patch("/api/issues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(issue)))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should delete issue successfully`() {
        doNothing().`when`(issueService).deleteIssue(1L)

        mockMvc.perform(delete("/api/issues/1"))
            .andExpect(status().isOk)
    }

    @Test
    fun `should handle NoSuchElementException`() {
        val exception = NoSuchElementException("Issue not found")
        `when`(issueService.getIssue(any())).thenThrow(exception)

        mockMvc.perform(get("/api/issues/1"))
            .andExpect(status().isNotFound)
            .andExpect(content().string("Issue not found"))
    }

    @Test
    fun `should handle IllegalArgumentException`() {
        val exception = IllegalArgumentException("Invalid argument")
        `when`(issueService.createIssue(any())).thenThrow(exception)

        mockMvc.perform(post("/api/issues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(issue)))
            .andExpect(status().isBadRequest)
            .andExpect(content().string("Invalid argument"))
    }

    @Test
    fun `should handle JwtTokenException`() {
        val exception = JwtTokenException("Invalid JWT token")
        `when`(issueService.getIssues(any())).thenThrow(exception)

        mockMvc.perform(get("/api/issues"))
            .andExpect(status().isUnauthorized)
            .andExpect(jsonPath("$.error").value("Invalid JWT token: Invalid JWT token"))
    }

    @Test
    fun `should handle AuthenticationException`() {
        val exception = AuthenticationException("Authentication failed")
        `when`(issueService.getIssues(any())).thenThrow(exception)

        mockMvc.perform(get("/api/issues"))
            .andExpect(status().isUnauthorized)
            .andExpect(jsonPath("$.error").value("Authentication failed: Authentication failed"))
    }
}
