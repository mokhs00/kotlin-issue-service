package com.example.userservice.service

import com.example.userservice.config.JWTProperties
import com.example.userservice.domain.entity.User
import com.example.userservice.domain.repository.UserRepository
import com.example.userservice.exception.PasswordNotMatchedException
import com.example.userservice.exception.UserExistException
import com.example.userservice.exception.UserNotFoundException
import com.example.userservice.model.SignInRequest
import com.example.userservice.model.SignInResponse
import com.example.userservice.model.SignUpRequest
import com.example.userservice.utils.BCryptUtils
import com.example.userservice.utils.JWTUtils
import com.example.userservice.utils.JwtClaim
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtProperties: JWTProperties,
    private val cacheManager: CoroutineCacheManager<User>,
) {
    companion object {
        private val CACHE_TTL = Duration.ofMinutes(1)
    }

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

    suspend fun signIn(request: SignInRequest): SignInResponse {
        return with(userRepository.findByEmail(request.email) ?: throw UserNotFoundException()) {
            if (!BCryptUtils.verify(password = request.password, hashedPassword = password)) {
                throw PasswordNotMatchedException()
            }

            val jwtClaim = JwtClaim(
                userId = id!!,
                email = email,
                profileUrl = profileUrl,
                username = username
            )

            val token = JWTUtils.createToken(jwtClaim, jwtProperties)

            cacheManager.awaitPut(key = token, value = this, ttl = CACHE_TTL)

            SignInResponse(
                email = email,
                username = username,
                token = token,
            )
        }
    }
}