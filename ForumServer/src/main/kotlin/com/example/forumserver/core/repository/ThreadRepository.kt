package com.example.forumserver.core.repository

import com.example.forumserver.core.entity.helper_class.ThreadEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ThreadRepository : JpaRepository<ThreadEntity, Long> {
    fun findByParentThread(thread: ThreadEntity, pageable: Pageable): Page<ThreadEntity>

    fun findByParentThreadNotNull(pageable: Pageable): Page<ThreadEntity>

    fun findByParentThreadNull(pageable: Pageable): Page<ThreadEntity>


}