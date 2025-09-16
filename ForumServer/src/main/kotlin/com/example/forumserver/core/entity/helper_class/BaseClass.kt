package com.example.forumserver.core.entity.helper_class

import com.example.forumserver.core.entity.enums.EntityStatus
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.OneToOne
import java.time.LocalDateTime

@MappedSuperclass
abstract class BaseClass (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long? = null,

    open val dateCreated: LocalDateTime,
    open val lastDateModified: LocalDateTime,

    @OneToOne
    @JoinColumn(name = "created_by")
    open val createdBy : User?,
    @OneToOne
    @JoinColumn(name = "last_modified_by")
    open val lastModifiedBy: User?,

    @Enumerated(EnumType.STRING)
    open val entityStatus: EntityStatus = EntityStatus.PROCESSING
)