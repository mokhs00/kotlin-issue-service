package com.example.issueservice.model

import com.fasterxml.jackson.annotation.JsonFormat
import com.example.issueservice.domain.Issue
import com.example.issueservice.domain.enums.IssuePriority
import com.example.issueservice.domain.enums.IssueStatus
import com.example.issueservice.domain.enums.IssueType
import java.time.LocalDateTime

data class IssueRequest(
    val summary: String,
    val description: String,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
)

data class IssueResponse(
    val id: Long,
    val userId: Long,
    val summary: String,
    val description: String,
    val type: IssueType,
    val priority: IssuePriority,
    val status: IssueStatus,
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    val createdAt: LocalDateTime?,
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    val updatedAt: LocalDateTime?,
) {
    companion object {
        fun of(issue: Issue) = IssueResponse(
            id= issue.id!!,
            userId = issue.userId,
            summary = issue.summary,
            description = issue.description,
            type = issue.type,
             priority = issue.priority,
            status = issue.status,
            createdAt = issue.createdAt,
            updatedAt = issue.updatedAt,
        )
    }
}
