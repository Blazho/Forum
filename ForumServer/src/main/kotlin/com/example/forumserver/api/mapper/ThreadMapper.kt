package com.example.forumserver.api.mapper

import com.example.forumserver.api.dto.ThreadDTO
import com.example.forumserver.api.dto.toDTO
import com.example.forumserver.api.response.PageResponse
import com.example.forumserver.api.response.toPageResponse
import com.example.forumserver.core.service.ThreadService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class ThreadMapper(
    private val threadService: ThreadService
) {
    fun findThread(threadId: Long): ThreadDTO {
        //check permission

        return threadService.findThread(threadId).toDTO()
    }

    fun findThreads(pageNum: Int, pageSize: Int): PageResponse<ThreadDTO> {
        val pageable = PageRequest.of(pageNum, pageSize)
        return threadService.findParentThreads(pageable).toPageResponse { it.toDTO() }
    }

    fun findChildThreads(threadId: Long, pageNum: Int, pageSize: Int): PageResponse<ThreadDTO> {
        val pageable = PageRequest.of(pageNum, pageSize)
        return threadService.findChildThreads(threadId, pageable).toPageResponse { it.toDTO() }
    }
}