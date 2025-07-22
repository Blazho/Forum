package com.example.forumserver.core.service

import com.example.forumserver.api.dto.PermissionDTO
import com.example.forumserver.core.entity.helper_class.Permission
import com.example.forumserver.core.repository.PermissionRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PermissionService(
    private val permissionRepository: PermissionRepository,
    private val authService: AuthService
) {
    fun findPermission(permissionId: Long): Permission =
        permissionRepository.findById(permissionId)
            .orElseThrow { RuntimeException("Permission with $permissionId does not exist exception!") }

    fun findPermissionByTitle(permissionTitle: String): Permission =
        permissionRepository.findByTitleIgnoreCase(permissionTitle)
            ?: throw RuntimeException("Permission with title $permissionTitle does not exist exception")

    fun createPermission(request: PermissionDTO): Permission {
        if(request.title.isBlank() || request.description.isBlank()){
            throw RuntimeException("Title or descriptions is blank exception!")
        }

        val createdBy = authService.getCurrentUser()

        val permission = Permission(
            id = null,
            title = request.title,
            description = request.description,
            createdBy = createdBy,
            lastModifiedBy = createdBy,
            dateCreated = LocalDateTime.now(),
            lastDateModified = LocalDateTime.now()
        )

        return permissionRepository.save(permission)
    }

    fun editPermission(request: PermissionDTO, permissionId: Long): Permission {
        val oldPermission = findPermission(permissionId)
        if(request.title.isBlank() || request.description.isBlank()){
            throw RuntimeException("Title or descriptions is blank exception!")
        }
        val lastModifiedBy = authService.getCurrentUser()

        val editedPermission = oldPermission.copy(
            title = request.title,
            description = request.description,
            lastDateModified = LocalDateTime.now(),
            lastModifiedBy = lastModifiedBy
        )

        return permissionRepository.save(editedPermission)
    }

    fun listPermissions(pageable: Pageable): Page<Permission> {

        return permissionRepository.findAll(pageable)
    }

    fun listPermissions(): List<Permission>{
        return permissionRepository.findAll()
    }
}