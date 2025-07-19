package com.example.forumserver.api.dto

import com.example.forumserver.core.entity.helper_class.Permission
import java.time.LocalDateTime


data class PermissionDTO(
    val id: Long,
    val title: String,
    val description: String,
    val dateCreated: LocalDateTime?,
    val lastDateModified: LocalDateTime?,
    val createdBy: Long?,
    val createdByUsername: String?,
    val lastModifiedBy: Long?
) {
}

fun Permission.toDTO(): PermissionDTO = PermissionDTO(
    id = this.id!!,
    title = this.title,
    description = this.description,
    dateCreated = this.dateCreated,
    lastDateModified = this.lastDateModified,
    createdBy = this.createdBy?.id,
    createdByUsername = this.createdBy?.username,
    lastModifiedBy = this.lastModifiedBy?.id,
)