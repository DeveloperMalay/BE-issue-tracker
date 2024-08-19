package com.issuetracker.IssueTracker.controller.issue


import com.issuetracker.IssueTracker.exception.JwtTokenException
import com.issuetracker.IssueTracker.model.Issue
import com.issuetracker.IssueTracker.service.IssueService
import io.jsonwebtoken.security.SignatureException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.naming.AuthenticationException

@RestController
@RequestMapping("api/issues")
class IssueController(private val service:IssueService) {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(e:NoSuchElementException):ResponseEntity<String> = ResponseEntity(e.message, HttpStatus.NOT_FOUND)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleNotFound(e:IllegalArgumentException):ResponseEntity<String> = ResponseEntity(e.message, HttpStatus.BAD_REQUEST)

    @ExceptionHandler(JwtTokenException::class)
    fun handleJwtTokenException(
        ex: JwtTokenException,
        request: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        return ResponseEntity(
            mapOf("error" to "Invalid JWT token: ${ex.message}"),
            HttpStatus.UNAUTHORIZED
        )
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(
        ex: AuthenticationException,
        request: HttpServletRequest
    ): ResponseEntity<Map<String, String>> {
        return ResponseEntity(
            mapOf("error" to "Authentication failed: ${ex.message}"),
            HttpStatus.UNAUTHORIZED
        )
    }


    @GetMapping
    suspend fun getIssues(@RequestParam(defaultValue = "0") page: Int,
                  @RequestParam(defaultValue = "10") size: Int): IssueResponse {
        val pageable: Pageable = PageRequest.of(page, size)
        val pageOfIssues: Page<Issue> = service.getIssues(pageable)
        println("issues--->$pageOfIssues")
        return pageOfIssues.toIssueResponse()
    }

    @PostMapping
    fun createIssue(@RequestBody issue: Issue): ResponseEntity<Issue> {
        val createdIssue = service.createIssue(issue)
        return ResponseEntity(createdIssue, HttpStatus.CREATED)
    }

    @GetMapping("/{id}")
    fun getIssue(@PathVariable id: Long):Issue?=service.getIssue(id)

    @PatchMapping
    fun updateIssue(@RequestBody issue: Issue): ResponseEntity<Issue> {
        val updatedIssue = service.updateIssue(issue)
        return if (updatedIssue != null) {
            ResponseEntity(updatedIssue, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
    @DeleteMapping("/{id}")
    fun deleteIssue(@PathVariable id:Long) = service.deleteIssue(id)

    private fun Page<Issue>.toIssueResponse(): IssueResponse {
        return IssueResponse(
            totalPages = this.totalPages,
            currentPageNumber = this.number,
            numberOfElements = this.numberOfElements,
            empty = this.isEmpty,
            content = this.content
        )
    }

}