package com.example.forumserver.core.entity.helper_class

import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "posts", schema = "forum_post")
data class PostEntity(

    override val id: Long?,

    val html: String,
    @ManyToOne
    @JoinColumn(name = "thread")
    val thread: ThreadEntity,

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
