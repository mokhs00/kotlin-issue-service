package com.example.userservice.service

import com.example.userservice.domain.entity.User
import com.example.userservice.domain.repository.UserRepository
import com.example.userservice.exception.UserExistException
import com.example.userservice.model.SignUpRequest
import com.example.userservice.utils.BCryptUtils
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    suspend fun signUp(signUpRequest: SignUpRequest) {
        with(signUpRequest) {
            userRepository.findByEmail(email = email)?.let {
                throw UserExistException()
            }
            val user = User(
                email = email,
                password = BCryptUtils.hash(password),
                username = username,
            )

            userRepository.save(user)
        }
    }
}