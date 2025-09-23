package com.example.forumserver.core.repository

import com.example.forumserver.core.entity.tables.Permission
import com.example.forumserver.core.entity.tables.User
import com.example.forumserver.core.entity.tables.UserPermission
import com.example.forumserver.core.entity.projections.UserPermissionProjection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserPermissionRepository : JpaRepository<UserPermission, Long>{

    fun findByUserAndPermission(user: User, permission: Permission): UserPermission?

    @Query("""
        SELECT p.title AS title, up.permissionLayer AS permissionLayer
        FROM UserPermission up
        JOIN up.permission p
        WHERE up.user.id = :userId
    """)
    fun findUserPermissions(@Param("userId") userId: Long): List<UserPermissionProjection>
}