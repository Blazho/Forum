package com.example.forumserver.api.dto

import com.example.forumserver.core.entity.helper_class.ThreadEntity
import java.time.LocalDateTime

data class ThreadDTO(
    val id: Long?,
    val dateCreated: LocalDateTime?,
    val lastDateModified: LocalDateTime?,
    val createdBy: Long?,
    val createdByUsername: String?,
    val lastModifiedBy: Long?,
    val lastModifiedByUsername: String?,
    val title: String?,
    val status: String?,
    val parentThreadId: Long?,
    val description: String?,
    val hasChildren: Boolean?
)

fun ThreadEntity.toDTO(hasChildren: Boolean?): ThreadDTO  =
    ThreadDTO(
        id = this.id!!, //todo id must exist and should be hashed
        dateCreated = this.dateCreated,
        lastDateModified = this.lastDateModified,
        createdBy = this.createdBy?.id,
        createdByUsername = this.createdBy?.username,
        lastModifiedBy = this.lastModifiedBy?.id,
        lastModifiedByUsername = this.lastModifiedBy?.username,
        title = this.title,
        status = this.status,
        description = this.description,
        parentThreadId = this.parentThread?.id,
        hasChildren = hasChildren
    )