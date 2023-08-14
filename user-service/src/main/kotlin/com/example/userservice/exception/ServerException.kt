package com.example.userservice.exception

sealed class ServerException(
    val code: Int,
    override val message: String,
) : RuntimeException(message)

data class UserExistException(
    override val message: String = "이미 존재하는 유저입니다."
) : ServerException(409, message = message)