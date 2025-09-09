package com.example.forumserver.core.service

import com.example.forumserver.core.entity.enums.EntityStatus
import com.example.forumserver.core.entity.enums.PermissionLayer
import com.example.forumserver.core.entity.helper_class.Permission
import com.example.forumserver.core.entity.helper_class.User
import com.example.forumserver.core.entity.helper_class.UserPermisson
import com.example.forumserver.core.entity.projection.UserPermissionProjection
import com.example.forumserver.core.repository.UserPermissionRepository
import org.springframework.stereotype.Service
import java.lang.RuntimeException
import java.time.LocalDateTime

@Service
class UserPermissionService(
    private val userPermissionRepository: UserPermissionRepository,
    private val permissionService: PermissionService,
    private val authService: AuthService
) {
    fun findUserPermission(permission: Permission): UserPermisson {
        val authentication = authService.getCurrentUser()
        return userPermissionRepository.findByUserAndPermission(authentication, permission)
            ?: throw RuntimeException("Permission for user is not found exception")
    }

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
            entityStatus = EntityStatus.ACTIVE
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
                entityStatus = EntityStatus.ACTIVE
            ))
        }

        userPermissionRepository.saveAll(userPermissionList)
    }

    fun havePermission(permissionTitle: String, permissionLayer: PermissionLayer): Boolean {
        val permission = permissionService.findPermissionByTitle(permissionTitle)
        val userPermission = findUserPermission(permission)
        return userPermission.permissionLayer >= permissionLayer
    }

    fun getUserPermissions(): List<UserPermissionProjection> {
        val user = authService.getCurrentUser()
        if(user.id == null){
            throw RuntimeException("User id is null exception")
        }
        return userPermissionRepository.findUserPermissions(user.id)
    }


}