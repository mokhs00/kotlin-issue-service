package com.example.issueservice.service

import com.example.issueservice.domain.Issue
import com.example.issueservice.domain.IssueRepository
import com.example.issueservice.model.IssueRequest
import com.example.issueservice.model.IssueResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IssueService(
    private val issueRepository: IssueRepository,
) {
    @Transactional
    fun create(userId: Long,  request: IssueRequest): IssueResponse {
        val issue = Issue(
            summary = request.summary,
            description = request.description,
            userId = userId,
            type = request.type,
            priority = request.priority,
            status = request.status,
        )

        return IssueResponse.of(issueRepository.save(issue))
    }

}
