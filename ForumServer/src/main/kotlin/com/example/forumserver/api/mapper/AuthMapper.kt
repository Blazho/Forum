package com.example.forumserver.api.mapper

import com.example.forumserver.api.request.LoginRequest
import com.example.forumserver.api.request.RegisterRequest
import com.example.forumserver.api.response.LoginResponse
import com.example.forumserver.api.response.RegisterResponse
import com.example.forumserver.core.service.AuthService
import com.example.forumserver.core.service.UserDetailsService
import org.springframework.stereotype.Component


@Component
class AuthMapper(
    private val userDetailsService: UserDetailsService,
    private val authService: AuthService,
) {

    fun login(loginRequest: LoginRequest): LoginResponse {
        val token = authService.login(loginRequest.username, loginRequest.password)
        return LoginResponse(token)
    }

    fun register(registerRequest: RegisterRequest): RegisterResponse {
        with(registerRequest) {

            userDetailsService.loadUserByUsername(username)?.let {
                throw Exception("Username already exists")
            }

            userDetailsService.loadUserByEmail(email)?.let {
                throw Exception("Email already exists")
            }

            val token = authService.register(
                username = username,
                password = password,
                firstName = firstName,
                lastName = lastName,
                email = email
            )

            return RegisterResponse(token)
        }
    }

}