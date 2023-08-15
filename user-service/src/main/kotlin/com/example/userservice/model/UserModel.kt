package com.example.userservice.model

import com.example.userservice.domain.entity.User
import java.time.LocalDateTime


data class UserInfoResponse(
    val email: String,
    val username: String,
    val profileUrl: String?,
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?,
) {
    companion object {
        private const val IMAGE_STORAGE_HOST: String = ""

        operator fun invoke(user: User) = with(user) {
            UserInfoResponse(
                profileUrl = if (profileUrl.isNullOrEmpty()) null else "$IMAGE_STORAGE_HOST/$profileUrl",
                username = username,
                email = email,
                createdAt = createdAt,
                updatedAt = updatedAt,
            )
        }
    }
}