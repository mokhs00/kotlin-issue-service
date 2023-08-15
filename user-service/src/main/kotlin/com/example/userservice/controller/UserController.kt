package com.example.userservice.controller

import com.example.userservice.model.AuthToken
import com.example.userservice.model.SignInRequest
import com.example.userservice.model.SignInResponse
import com.example.userservice.model.SignUpRequest
import com.example.userservice.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/signup")
    suspend fun signUp(@RequestBody request: SignUpRequest) {
        userService.signUp(request)
    }

    @PostMapping("signin")
    suspend fun signIn(@RequestBody request: SignInRequest): SignInResponse {
        return userService.signIn(request)
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun logout(@AuthToken token: String) {
        userService.logout(token)
    }
}
