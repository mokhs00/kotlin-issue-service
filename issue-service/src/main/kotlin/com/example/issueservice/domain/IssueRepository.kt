package com.example.issueservice.domain

import com.example.issueservice.domain.enums.IssueStatus
import org.springframework.data.jpa.repository.JpaRepository

interface IssueRepository : JpaRepository<Issue, Long> {
    fun findAllByStatusOrderByCreatedAtDesc(status: IssueStatus): List<Issue>?
}
