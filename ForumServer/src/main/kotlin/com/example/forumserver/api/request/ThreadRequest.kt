package com.example.forumserver.api.request

data class ThreadRequest(
    val threadId: Long,
    val pageSize: Int = 10,
    val pageNumber: Int = 0
)
