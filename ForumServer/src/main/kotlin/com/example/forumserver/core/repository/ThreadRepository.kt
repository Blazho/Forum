package com.example.forumserver.core.repository

import com.example.forumserver.core.entity.helper_class.ThreadEntity
import com.example.forumserver.core.entity.projection.ThreadEntityPairProjection
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ThreadRepository : JpaRepository<ThreadEntity, Long> {
    fun findByParentThread(thread: ThreadEntity, pageable: Pageable): Page<ThreadEntity>


    @Query("select t from ThreadEntity t where t.parentThread is null")
    fun findByParentThreadNull(): List<ThreadEntityPairProjection>

    fun findByParentThreadNull(pageable: Pageable): Page<ThreadEntity>


    fun existsByTitleIgnoreCase(title: String): Boolean

}