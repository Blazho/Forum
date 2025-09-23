package com.example.forumserver.core.entity.tables

import com.example.forumserver.core.entity.enums.EntityStatus
import com.example.forumserver.core.entity.helpers.BaseClass
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime

@Entity
@Table(name = "forum_users", schema = "forum_post")
data class User(

    @Column(unique = true)
    private val username: String,

    private val password: String,

    val firstName: String,

    val lastName: String,

    @Column(unique = true)
    val email: String,

    //todo change to db table
    @Enumerated(EnumType.STRING)
    val role: Role,

    override val dateCreated: LocalDateTime,

    override val lastDateModified: LocalDateTime,

    override val createdBy: User? = null,

    override val lastModifiedBy: User? = null,

    override val entityStatus: EntityStatus

) : BaseClass(
    dateCreated = dateCreated,
    lastDateModified = lastDateModified,
    createdBy = createdBy,
    lastModifiedBy = lastModifiedBy
), UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf(role)

    override fun getPassword(): String = password

    override fun getUsername(): String = username

}

enum class Role: GrantedAuthority{
    ROLE_ANONYMOUS,
    ROLE_BASIC_USER,
    ROLE_INTERMEDIATE_USER,
    ROLE_ADVANCED_USER,
    ROLE_ADMIN,
    ROLE_HEAD_ADMIN;

    override fun getAuthority() = name
}