package com.example.forumserver.core.entity.helper_class

import com.example.forumserver.core.entity.enums.EntityStatus
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "permissions", schema = "forum_post")
data class Permission(

    override val id: Long?,

    val title: String,

    val description: String,

    override val dateCreated: LocalDateTime,

    override val lastDateModified: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "created_by") //todo move to base class
    override val createdBy: User? = null,

    @ManyToOne
    @JoinColumn(name = "last_modified_by") //todo move to base class
    override val lastModifiedBy: User? = null,

    override val entityStatus: EntityStatus
): BaseClass(
    id = id,
    dateCreated = dateCreated,
    lastDateModified = lastDateModified,
    createdBy = createdBy,
    lastModifiedBy = lastModifiedBy
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Permission

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}
