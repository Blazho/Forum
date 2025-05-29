package com.example.forumserver.core.entity.helper_class

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

    @ManyToOne
    @JoinColumn(name = "created_by")
    override val createdBy: User? = null,

    @ManyToOne
    @JoinColumn(name = "last_modified_by")
    override val lastModifiedBy: User? = null

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
    ROLE_USER,
    ROLE_ADMIN;

    override fun getAuthority() = name
}