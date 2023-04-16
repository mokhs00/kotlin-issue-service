package com.example.issueservice.service

import com.example.issueservice.domain.Issue
import com.example.issueservice.domain.IssueRepository
import com.example.issueservice.domain.enums.IssueStatus
import com.example.issueservice.exception.NotFoundException
import com.example.issueservice.model.IssueRequest
import com.example.issueservice.model.IssueResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class IssueService(
    private val issueRepository: IssueRepository,
) {
    @Transactional
    fun create(userId: Long, request: IssueRequest): IssueResponse {
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

    fun getAll(status: IssueStatus) =
        issueRepository.findAllByStatusOrderByCreatedAtDesc(status)
            ?.map { IssueResponse.of(it) }

    fun get(id: Long): IssueResponse {
        val issue = issueRepository.findByIdOrNull(id) ?: throw NotFoundException("이슈가 존재하지 않습니다 id: '$id'")
        return IssueResponse.of(issue)
    }


}
