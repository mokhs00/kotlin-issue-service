package com.example.issueservice.domain.enums

enum class IssuePriority {
    LOW, MEDIUM, HIGH;

    companion object {
        operator fun invoke(type: String) = IssueType.valueOf(type.uppercase())
    }
}
