package com.example.forumserver.core.repository

import com.example.forumserver.core.entity.helper_class.Permission
import com.example.forumserver.core.entity.helper_class.User
import com.example.forumserver.core.entity.helper_class.UserPermisson
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserPermissionRepository : JpaRepository<UserPermisson, Long>{

    fun findByUserAndPermission(user: User, permission: Permission): UserPermisson?

}