package com.example.forumserver.core.entity.helper_class

import com.example.forumserver.core.entity.enums.PermissionLayer
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "user_permissions", schema = "forum_post")
data class UserPermisson(

    override val id: Long?,

    @ManyToOne
    val user: User,

    @ManyToOne
    val permission: Permission,

    @Enumerated(EnumType.STRING)
    val permissionLayer: PermissionLayer,

    override val dateCreated: LocalDateTime,

    override val lastDateModified: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "created_by") //todo move to base class
    override val createdBy: User? = null,

    @ManyToOne
    @JoinColumn(name = "last_modified_by") //todo move to base class
    override val lastModifiedBy: User? = null

): BaseClass(
    id = id,
    dateCreated = dateCreated,
    lastDateModified = lastDateModified,
    createdBy = createdBy,
    lastModifiedBy = lastModifiedBy
)
