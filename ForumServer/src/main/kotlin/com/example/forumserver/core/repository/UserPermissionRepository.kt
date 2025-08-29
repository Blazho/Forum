package com.example.forumserver.core.repository

import com.example.forumserver.core.entity.helper_class.Permission
import com.example.forumserver.core.entity.helper_class.User
import com.example.forumserver.core.entity.helper_class.UserPermisson
import com.example.forumserver.core.entity.projection.UserPermissionProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserPermissionRepository : JpaRepository<UserPermisson, Long>{

    fun findByUserAndPermission(user: User, permission: Permission): UserPermisson?

    @Query("""
        SELECT p.title AS title, up.permissionLayer AS permissionLayer
        FROM UserPermisson up
        JOIN up.permission p
        WHERE up.user.id = :userId
    """)
    fun findUserPermissions(@Param("userId") userId: Long): List<UserPermissionProjection>
}