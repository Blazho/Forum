package com.example.forumserver.api.mapper

import com.example.forumserver.api.dto.ThreadDTO
import com.example.forumserver.api.dto.toDTO
import com.example.forumserver.api.response.PageResponse
import com.example.forumserver.api.response.toPageResponse
import com.example.forumserver.core.configuration.THREAD_CHILD_PERMISSION
import com.example.forumserver.core.configuration.THREAD_PARENT_PERMISSION
import com.example.forumserver.core.entity.enums.PermissionLayer
import com.example.forumserver.core.entity.projection.ThreadEntityPairProjection
import com.example.forumserver.core.service.AuthService
import com.example.forumserver.core.service.ThreadService
import com.example.forumserver.core.service.UserPermissionService
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component

@Component
class ThreadMapper(
    private val threadService: ThreadService,
    private val userPermissionService: UserPermissionService,
    private val authService: AuthService
) {
    fun findThread(threadId: Long): ThreadDTO {
        //check permission
        val thread = threadService.findThread(threadId)
        return thread.toDTO(threadService.hasChildren(thread))
    }

    fun findThreads(pageNum: Int, pageSize: Int): PageResponse<ThreadDTO> {
        if (!userPermissionService.havePermission(THREAD_PARENT_PERMISSION, PermissionLayer.VIEW)){
            throw RuntimeException("User does not have view permission for parent threads")
        }

        val pageable = PageRequest.of(pageNum, pageSize)
        return threadService.findParentThreads(pageable).toPageResponse { it.toDTO(null) }
    }

    fun findChildThreads(threadId: Long, pageNum: Int, pageSize: Int): PageResponse<ThreadDTO> {
        if (!userPermissionService.havePermission(THREAD_CHILD_PERMISSION, PermissionLayer.VIEW)){
            throw RuntimeException("User does not have view permission for child threads")
        }

        val pageable = PageRequest.of(pageNum, pageSize)
        return threadService.findChildThreads(threadId, pageable).toPageResponse { it.toDTO(null) }
    }

    fun createThread(threadDTO: ThreadDTO): ThreadDTO? {
        if(threadDTO.parentThreadId == null){
            if (!userPermissionService.havePermission(THREAD_PARENT_PERMISSION, PermissionLayer.CREATE)){
                throw RuntimeException("User does not have create permission for parent threads")
            }
        }else{
            if (!userPermissionService.havePermission(THREAD_CHILD_PERMISSION, PermissionLayer.CREATE)){
                throw RuntimeException("User does not have create permission for child threads")
            }
        }

        return threadService.create(threadDTO).toDTO(null)
    }

    fun listParentableThreads(): List<ThreadEntityPairProjection> {
        return threadService.listAllParentableThreads()
    }

    fun editThread(threadDTO: ThreadDTO, threadId: Long): ThreadDTO? {
        val authentication = authService.getCurrentUser()
        val oldThread = threadService.findThread(threadId)

        if(authentication != oldThread.createdBy){
            throw RuntimeException("User does not have permission to edit not his own threads")
        }

        if(oldThread.parentThread == null){
            if (!userPermissionService.havePermission(THREAD_PARENT_PERMISSION, PermissionLayer.EDIT)){
                throw RuntimeException("User does not have edit permission for parent threads")
            }
        }else{
            if (!userPermissionService.havePermission(THREAD_CHILD_PERMISSION, PermissionLayer.EDIT)){
                throw RuntimeException("User does not have edit permission for child threads")
            }
        }

        return threadService.edit(threadDTO, threadId).toDTO(null)
    }
}