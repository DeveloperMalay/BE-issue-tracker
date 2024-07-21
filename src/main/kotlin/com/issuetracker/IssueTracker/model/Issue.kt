package com.issuetracker.IssueTracker.model
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "Issue")
data class Issue(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val description: String,
    val status:String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
){
    constructor() : this(null, "", "", "", LocalDateTime.now(), LocalDateTime.now())
}