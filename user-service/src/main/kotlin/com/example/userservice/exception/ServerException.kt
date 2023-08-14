package com.example.userservice.exception

sealed class ServerException(
    val code: Int,
    override val message: String,
) : RuntimeException(message)

data class UserExistException(
    override val message: String = "이미 존재하는 유저입니다."
) : ServerException(409, message = message)

data class UserNotFoundException(
    override val message: String = "존재하지 않는 유저입니다."
) : ServerException(404, message = message)


data class PasswordNotMatchedException(
    override val message: String = "패스워드가 잘못되었습니다."
): ServerException(400, message = message)