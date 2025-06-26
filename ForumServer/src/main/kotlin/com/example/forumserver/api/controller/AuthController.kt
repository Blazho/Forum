package com.example.forumserver.api.controller

import com.example.forumserver.api.mapper.AuthMapper
import com.example.forumserver.api.request.LoginRequest
import com.example.forumserver.api.request.RegisterRequest
import com.example.forumserver.api.response.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authMapper: AuthMapper,
) {

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<ApiResponse<String>> {
        return try {
            ResponseEntity.ok(ApiResponse(data = authMapper.login(loginRequest)))
        }catch (ex: RuntimeException){
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse(error = true, errorMessage = ex.message))
        }
    }

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegisterRequest): ResponseEntity<ApiResponse<String>> {
        return try {
            ResponseEntity.ok(ApiResponse(data = authMapper.register(registerRequest)))
        }catch (ex: RuntimeException){
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse(error = true, errorMessage = ex.message))
        }
    }
}