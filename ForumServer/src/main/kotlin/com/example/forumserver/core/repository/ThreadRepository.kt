package com.example.forumserver.core.repository

import com.example.forumserver.core.entity.enums.EntityStatus
import com.example.forumserver.core.entity.helper_class.ThreadEntity
import com.example.forumserver.core.entity.projection.ThreadEntityPairProjection
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ThreadRepository : JpaRepository<ThreadEntity, Long> {
    fun findByParentThreadAndEntityStatus(
        parentThread: ThreadEntity,
        entityStatus: EntityStatus,
        pageable: Pageable
    ): Page<ThreadEntity>

    @Query("select (count(t) > 0) from ThreadEntity t where t.parentThread = :parentThread and t.entityStatus = :status")
    fun threadHasChildren(@Param("parentThread")parentThread: ThreadEntity, @Param("status") status: EntityStatus): Boolean

    /***
     * Returns all projections with id - title pairs where thread does not have parent
     ***/
    @Query("select t from ThreadEntity t where t.parentThread is null and t.entityStatus = :status")
    fun findByParentThreadNull(@Param("status") status: EntityStatus): List<ThreadEntityPairProjection>

    fun findByParentThreadNullAndEntityStatus(entityStatus: EntityStatus, pageable: Pageable): Page<ThreadEntity>

    //todo TBD if entity status need to be checked before returning ThreadEntity
    fun existsByTitleIgnoreCase(title: String): Boolean

}