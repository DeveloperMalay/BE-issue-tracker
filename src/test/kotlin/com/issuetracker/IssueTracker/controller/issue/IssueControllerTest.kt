package com.issuetracker.IssueTracker.controller.issue

import com.fasterxml.jackson.databind.ObjectMapper
import com.issuetracker.IssueTracker.config.JwtAuthenticationFilter
import com.issuetracker.IssueTracker.model.Issue
import com.issuetracker.IssueTracker.service.IssueService
import com.issuetracker.IssueTracker.service.TokenService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@WebMvcTest(IssueController::class)
@AutoConfigureMockMvc(addFilters = false)
class IssueControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @MockBean
    lateinit var tokenService: TokenService

    @MockBean
    lateinit var jwtAuthenticationFilter: JwtAuthenticationFilter

    @MockBean
    private lateinit var issueService: IssueService
    val token =
      "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJuZXdVc2VyMUBleGFtcGxlLmNvbSIsImlhdCI6MTcyMzU0OTA0OSwiZXhwIjoxNzIzNTUyNjQ5fQ.zZcl_r9BsoacT1Y6EtDjzDs1ywGlBDEC-DqPaV198AyRwGuWIn7ZXSHlwH-Xah973WmQhs84EOr0B_qTLYmquw"




    @Test
    fun `should create a new issue`() {
        val issue = Issue(
            id = 1L,
            title = "Sample Issue",
            description = "This is a sample issue.",
            status = "OPEN"
        )

        every { issueService.createIssue(any()) } returns issue

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/issues")
                .content(objectMapper.writeValueAsString(issue))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer $token")
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(issue.id))
            .andExpect(MockMvcResultMatchers.jsonPath("$.title").value(issue.title))
            .andExpect(MockMvcResultMatchers.jsonPath("$.description").value(issue.description))
            .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(issue.status))
    }
        @Test
        fun `should return all banks`(){
            // Arrange
            val issues = listOf(
                Issue(1L, "Issue 1", "Description 1", "Open"),
                Issue(2L, "Issue 2", "Description 2", "Closed")
            )
            val pageable = PageRequest.of(0, 10)
            val issuePage = PageImpl(issues, pageable, issues.size.toLong())


            // Act & Assert
            mockMvc.perform(
                MockMvcRequestBuilders.get("/api/issues")
                    .header("Authorization", "Bearer $token")
                    .accept(MediaType.APPLICATION_JSON)
            )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray)
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(issues.size))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(issues[0].id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].title").value(issues[0].title))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].description").value(issues[0].description))
}

//    @Test
//    fun `should retrieve issues successfully`() {
//        val pageOfIssues = PageImpl(listOf(issue), PageRequest.of(0, 10), 1)
//        `when`(issueService.getIssues(any())).thenReturn(pageOfIssues)
//
//        mockMvc.perform(get("/api/issues"))
//            .andExpect(status().isOk)
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.totalPages").value(1))
//            .andExpect(jsonPath("$.content[0].title").value("Test Issue"))
//    }
//
//    @Test
//    fun `should create issue successfully`() {
//        `when`(issueService.createIssue(any())).thenReturn(issue)
//
//        mockMvc.perform(post("/api/issues")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(objectMapper.writeValueAsString(issue)))
//            .andExpect(status().isCreated)
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.title").value("Test Issue"))
//    }
//
//    @Test
//    fun `should get issue by ID successfully`() {
//        `when`(issueService.getIssue(1L)).thenReturn(issue)
//
//        mockMvc.perform(get("/api/issues/1"))
//            .andExpect(status().isOk)
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.title").value("Test Issue"))
//    }
//
//    @Test
//    fun `should update issue successfully`() {
//        val updatedIssue = issue.copy(title = "Updated Title")
//        `when`(issueService.updateIssue(any())).thenReturn(updatedIssue)
//
//        mockMvc.perform(patch("/api/issues")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(objectMapper.writeValueAsString(updatedIssue)))
//            .andExpect(status().isOk)
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//            .andExpect(jsonPath("$.title").value("Updated Title"))
//    }
//
//    @Test
//    fun `should return not found when updating non-existent issue`() {
//        `when`(issueService.updateIssue(any())).thenReturn(null)
//
//        mockMvc.perform(patch("/api/issues")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(objectMapper.writeValueAsString(issue)))
//            .andExpect(status().isNotFound)
//    }
//
//    @Test
//    fun `should delete issue successfully`() {
//        doNothing().`when`(issueService).deleteIssue(1L)
//
//        mockMvc.perform(delete("/api/issues/1"))
//            .andExpect(status().isOk)
//    }
//
//    @Test
//    fun `should handle NoSuchElementException`() {
//        val exception = NoSuchElementException("Issue not found")
//        `when`(issueService.getIssue(any())).thenThrow(exception)
//
//        mockMvc.perform(get("/api/issues/1"))
//            .andExpect(status().isNotFound)
//            .andExpect(content().string("Issue not found"))
//    }
//
//    @Test
//    fun `should handle IllegalArgumentException`() {
//        val exception = IllegalArgumentException("Invalid argument")
//        `when`(issueService.createIssue(any())).thenThrow(exception)
//
//        mockMvc.perform(post("/api/issues")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(objectMapper.writeValueAsString(issue)))
//            .andExpect(status().isBadRequest)
//            .andExpect(content().string("Invalid argument"))
//    }
//
//    @Test
//    fun `should handle JwtTokenException`() {
//        val exception = JwtTokenException("Invalid JWT token")
//        `when`(issueService.getIssues(any())).thenThrow(exception)
//
//        mockMvc.perform(get("/api/issues"))
//            .andExpect(status().isUnauthorized)
//            .andExpect(jsonPath("$.error").value("Invalid JWT token: Invalid JWT token"))
//    }
//
//    @Test
//    fun `should handle AuthenticationException`() {
//        val exception = AuthenticationException("Authentication failed")
//        `when`(issueService.getIssues(any())).thenThrow(exception)
//
//        mockMvc.perform(get("/api/issues"))
//            .andExpect(status().isUnauthorized)
//            .andExpect(jsonPath("$.error").value("Authentication failed: Authentication failed"))
//    }
}
