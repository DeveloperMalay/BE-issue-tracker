package com.issuetracker.IssueTracker.controller.issue


import com.issuetracker.IssueTracker.model.Issue
import com.issuetracker.IssueTracker.service.IssueService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/issues")
class IssueController(private val service:IssueService) {

    @GetMapping
    fun getIssues(@RequestParam(defaultValue = "0") page: Int,
                  @RequestParam(defaultValue = "10") size: Int): IssueResponse {
        val pageable: Pageable = PageRequest.of(page, size)
        val pageOfIssues: Page<Issue> = service.getIssues(pageable)
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
            content = this.content,
            currentPageNumber = this.number,
            numberOfElements = this.numberOfElements,
            empty = this.isEmpty
        )
    }

}