package com.example.forumserver.api.request

data class PostRequest(
    val threadId: Long,
    val pageSize: Int = 10,
    val pageNumber: Int = 0
)
