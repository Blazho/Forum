package com.example.forumserver.core.entity.helper_class

import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "threads", schema = "forum_post")
data class ThreadEntity(

    override val id: Long?,

    val title: String,

    val status: String?,

    @ManyToOne
    @JoinColumn(name = "parent_thread")
    val parentThread: ThreadEntity?,

    val description: String,

    override val dateCreated: LocalDateTime,

    override val lastDateModified: LocalDateTime,

    @ManyToOne
    @JoinColumn(name = "created_by")
    override val createdBy: User? = null,

    @ManyToOne
    @JoinColumn(name = "last_modified_by")
    override val lastModifiedBy: User? = null
) : BaseClass(
    id = id,
    dateCreated = dateCreated,
    lastDateModified = lastDateModified,
    createdBy = createdBy,
    lastModifiedBy = lastModifiedBy
)
