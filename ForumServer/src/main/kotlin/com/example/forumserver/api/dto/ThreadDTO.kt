package com.example.forumserver.api.dto

import com.example.forumserver.core.entity.helper_class.ThreadEntity
import java.time.LocalDateTime

data class ThreadDTO(
    val id: Long?,
    val dateCreated: LocalDateTime?,
    val lastDateModified: LocalDateTime?,
    val createdBy: String?,
    val lastModifiedBy: Long?,
    val title: String?,
    val status: String?,
    val parentThreadId: Long?,
    val description: String?,
)

fun ThreadEntity.toDTO(): ThreadDTO  =
    ThreadDTO(
        id = this.id!!, //todo id must exist and should be hashed
        dateCreated = this.dateCreated,
        lastDateModified = this.lastDateModified,
        createdBy = this.createdBy?.username,
        lastModifiedBy = this.lastModifiedBy?.id,
        title = this.title,
        status = this.status,
        description = this.description,
        parentThreadId = this.parentThread?.id
    )