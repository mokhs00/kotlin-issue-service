package com.example.issueservice.domain.enums

enum class IssueStatus {
    TODO, IN_PROGRESS, RESOLVED;

    companion object {
        operator fun invoke(type: String) = IssueType.valueOf(type.uppercase())
    }
}
