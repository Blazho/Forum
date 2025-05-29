package com.example.forumserver.core.service

import com.example.forumserver.core.entity.helper_class.ThreadEntity
import com.example.forumserver.core.repository.ThreadRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.data.domain.Pageable

@Service
class ThreadService(
    private val threadRepository: ThreadRepository
) {
    fun findParentThreads(pageable: Pageable) : Page<ThreadEntity> {
        return threadRepository.findByParentThreadNull(pageable)
    }

    fun findThread(threadId: Long): ThreadEntity {
        return threadRepository.findById(threadId)
            .orElseThrow { RuntimeException("Thread not found") }

    }

    fun findChildThreads(threadId: Long, pageable: Pageable): Page<ThreadEntity>{
        val parentThread = findThread(threadId)
        return threadRepository.findByParentThread(parentThread, pageable)
    }



}