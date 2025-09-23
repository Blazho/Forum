package com.example.forumserver.core.entity.tables

import com.example.forumserver.core.entity.enums.EntityStatus
import com.example.forumserver.core.entity.enums.PermissionLayer
import com.example.forumserver.core.entity.helpers.BaseClass
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_permissions", schema = "forum_post")
data class UserPermission(

    override val id: Long?,

    @ManyToOne
    val user: User,

    @ManyToOne
    val permission: Permission,

    @Enumerated(EnumType.STRING)
    val permissionLayer: PermissionLayer,

    override val dateCreated: LocalDateTime,

    override val lastDateModified: LocalDateTime,

    override val createdBy: User? = null,

    override val lastModifiedBy: User? = null,

    override val entityStatus: EntityStatus
): BaseClass(
    id = id,
    dateCreated = dateCreated,
    lastDateModified = lastDateModified,
    createdBy = createdBy,
    lastModifiedBy = lastModifiedBy
)