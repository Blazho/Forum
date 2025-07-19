package com.example.forumserver.api.mapper

import com.example.forumserver.api.dto.PermissionDTO
import com.example.forumserver.api.dto.toDTO
import com.example.forumserver.api.response.PageResponse
import com.example.forumserver.api.response.toPageResponse
import com.example.forumserver.core.service.PermissionService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class PermissionMapper(
    private val permissionService: PermissionService
) {

    fun createPermission(request: PermissionDTO): PermissionDTO {
        //permission

        return permissionService.createPermission(request).toDTO()
    }

    fun editPermission(request: PermissionDTO, permissionId: Long): PermissionDTO {
        //permission

        return permissionService.editPermission(request, permissionId).toDTO()
    }

    fun listPermissions(pageSize: Int, pageNumber: Int): PageResponse<PermissionDTO> {
        //permission

        val pageable: Pageable = PageRequest.of(pageNumber, pageSize)

        return permissionService.listPermissions(pageable).toPageResponse { it.toDTO() }
    }

    fun findPermission(permissionId: Long): PermissionDTO? {
        //permission

        return permissionService.findPermission(permissionId).toDTO()
    }
}