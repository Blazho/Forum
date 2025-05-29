package com.example.forumserver.api.response

data class ApiResponse<T>(
    val data: T? = null,
    val error: Boolean = false,
    val errorMessage: String? = null
)
