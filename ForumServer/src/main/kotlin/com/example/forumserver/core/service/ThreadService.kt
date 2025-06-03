package com.example.forumserver.core.service

import com.example.forumserver.api.dto.ThreadDTO
import com.example.forumserver.core.entity.helper_class.ThreadEntity
import com.example.forumserver.core.entity.projection.ThreadEntityPairProjection
import com.example.forumserver.core.repository.ThreadRepository
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.data.domain.Pageable
import java.time.LocalDateTime

@Service
class ThreadService(
    private val threadRepository: ThreadRepository,
    private val userDetailsService: UserDetailsService
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

    fun create(threadDTO: ThreadDTO): ThreadEntity {
        if(invalidThreadFields(threadDTO)){
            throw RuntimeException("Invalid data provided exception!")
        }


        with(threadDTO){
            val parentThread = parentThreadId?.let {
                threadRepository.findById(it)
                    .orElseThrow { RuntimeException("Parent thread not found exception!") }
            }
            val createdBy = createdBy?.let {
                userDetailsService.findUserById(it)
            }
            if(threadRepository.existsByTitleIgnoreCase(title!!)){
                throw RuntimeException("Thread with the same title already exists exception!")
            }

            val threadEntity = ThreadEntity(
                title = title,
                status = status,
                parentThread = parentThread,
                description = description!!,
                dateCreated = LocalDateTime.now(),
                lastDateModified = LocalDateTime.now(),
                createdBy = createdBy,
                lastModifiedBy = createdBy
            )

            return threadRepository.save(threadEntity)
        }
    }

    fun listAllParentableThreads(): List<ThreadEntityPairProjection> {
        return threadRepository.findByParentThreadNull()
    }

    private fun invalidThreadFields(threadDTO: ThreadDTO) : Boolean {
        with(threadDTO){
            return createdBy == null
                    || title.isNullOrBlank()
                    || description.isNullOrBlank()
        }
    }


}