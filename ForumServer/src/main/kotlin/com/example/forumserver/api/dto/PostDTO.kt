package com.example.forumserver.api.dto

import com.example.forumserver.core.entity.helper_class.PostEntity
import java.time.LocalDateTime

data class PostDTO(
    val id: Long,
    val html: String,
    val threadId: Long,
    val dateCreated: LocalDateTime?,
    val lastDateModified: LocalDateTime?,
    val createdBy: Long?,
    val createdByUsername: String?,
    val lastModifiedBy: Long?
)

fun PostEntity.toDTO(): PostDTO = PostDTO(
    id = this.id!!, //todo id must exist and should be hashed
    html = this.html,
    threadId = this.thread.id!!, //thread id must exist
    dateCreated = this.dateCreated,
    lastDateModified = this.lastDateModified,
    createdBy = this.createdBy?.id,
    createdByUsername = this.createdBy?.username,
    lastModifiedBy = this.lastModifiedBy?.id,

)