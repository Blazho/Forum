package com.example.forumserver.core.repository

import com.example.forumserver.core.entity.enums.EntityStatus
import com.example.forumserver.core.entity.helper_class.PostEntity
import com.example.forumserver.core.entity.helper_class.ThreadEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository: JpaRepository<PostEntity, Long> {

    fun findByThreadAndEntityStatus(
        thread: ThreadEntity,
        entityStatus: EntityStatus,
        pageable: Pageable
    ): Page<PostEntity>


}