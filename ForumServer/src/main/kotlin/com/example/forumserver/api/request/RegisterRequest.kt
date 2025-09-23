package com.example.forumserver.api.request

import com.example.forumserver.core.entity.tables.Role

data class RegisterRequest (
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val role: Role?,
)