package com.example.forumserver.api.response

data class LoginResponse(
    val token: String,
    val userPermissions: Map<String, Int>
)
