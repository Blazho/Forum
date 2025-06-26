package com.example.forumserver.api.mapper

import com.example.forumserver.api.request.LoginRequest
import com.example.forumserver.api.request.RegisterRequest
import com.example.forumserver.core.service.AuthService
import com.example.forumserver.core.service.UserDetailsService
import org.springframework.stereotype.Component


@Component
class AuthMapper(
    private val userDetailsService: UserDetailsService,
    private val authService: AuthService,
) {

    fun login(loginRequest: LoginRequest): String {
        return authService.login(loginRequest.username, loginRequest.password)
    }

    fun register(registerRequest: RegisterRequest): String {
        with(registerRequest) {

            userDetailsService.loadUserByUsername(username)?.let {
                throw RuntimeException("Username already exists")
            }

            userDetailsService.loadUserByEmail(email)?.let {
                throw RuntimeException("Email already exists")
            }

            val token = authService.register(
                username = username,
                password = password,
                firstName = firstName,
                lastName = lastName,
                email = email
            )

            return token
        }
    }

}