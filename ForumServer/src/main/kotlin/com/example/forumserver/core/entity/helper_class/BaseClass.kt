package com.example.forumserver.core.entity.helper_class

import jakarta.persistence.JoinColumn
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.OneToOne
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseClass (
    open val dateCreated: LocalDateTime,
    open val lastDateModified: LocalDateTime,

    @OneToOne
    @JoinColumn(name = "created_by")
    open val createdBy : User?,
    @OneToOne
    @JoinColumn(name = "last_modified_by")
    open val lastModifiedBy: User?
)