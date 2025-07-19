package com.example.forumserver.core.service

import com.example.forumserver.core.entity.enums.PermissionLayer
import com.example.forumserver.core.entity.helper_class.Permission
import com.example.forumserver.core.entity.helper_class.User
import com.example.forumserver.core.entity.helper_class.UserPermisson
import com.example.forumserver.core.repository.UserPermissionRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserPermissionService(
    private val userPermissionRepository: UserPermissionRepository
) {
    fun addPermission(forUser: User, permission: Permission, permissionLayer: PermissionLayer, authenticatedUser: User) {
        userPermissionRepository.save(UserPermisson(
            id = null,
            user = forUser,
            permission = permission,
            permissionLayer = permissionLayer,
            dateCreated = LocalDateTime.now(),
            lastDateModified = LocalDateTime.now(),
            createdBy = authenticatedUser,
            lastModifiedBy = authenticatedUser,
        ))
    }

    fun addPermissions(forUser: User, permissions: Map<Permission, PermissionLayer>, authenticatedUser: User){

        val userPermissionList = mutableListOf<UserPermisson>()

        permissions.forEach { (key, value) ->
            userPermissionList.add(UserPermisson(
                id = null,
                user = forUser,
                permission = key,
                permissionLayer = value,
                dateCreated = LocalDateTime.now(),
                lastDateModified = LocalDateTime.now(),
                createdBy = authenticatedUser,
                lastModifiedBy = authenticatedUser,
            ))
        }

        userPermissionRepository.saveAll(userPermissionList)
    }




}