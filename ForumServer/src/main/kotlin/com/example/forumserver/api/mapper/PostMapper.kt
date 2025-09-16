package com.example.forumserver.api.mapper

import com.example.forumserver.api.dto.PostDTO
import com.example.forumserver.api.dto.toDTO
import com.example.forumserver.api.request.PostRequest
import com.example.forumserver.api.response.PageResponse
import com.example.forumserver.api.response.toPageResponse
import com.example.forumserver.core.configuration.POST_PERMISSION
import com.example.forumserver.core.entity.enums.EntityStatus
import com.example.forumserver.core.entity.enums.PermissionLayer
import com.example.forumserver.core.service.AuthService
import com.example.forumserver.core.service.PostService
import com.example.forumserver.core.service.UserPermissionService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
class PostMapper(
    private val postService: PostService,
    private val userPermissionService: UserPermissionService,
    private val authService: AuthService
) {
    fun findPosts(postRequest: PostRequest) : PageResponse<PostDTO> {
        if(!userPermissionService.havePermission(POST_PERMISSION, PermissionLayer.VIEW)){
            throw RuntimeException("User does not have permission to view posts")
        }

        val pageable: Pageable = PageRequest
            .of(postRequest.pageNumber, postRequest.pageSize, Sort.by("dateCreated").ascending())
        //todo Optimise: make custom query for pagination
        return postService.findThreadActivePosts(postRequest.threadId, pageable).toPageResponse { it.toDTO() }
    }

    fun createPost(request: PostDTO): PostDTO? {
        if(!userPermissionService.havePermission(POST_PERMISSION, PermissionLayer.CREATE)){
            throw RuntimeException("User does not have permission to create posts")
        }
        return postService.createPost(request).toDTO()
    }

    fun getPost(postId: Long): PostDTO {
        if(!userPermissionService.havePermission(POST_PERMISSION, PermissionLayer.VIEW)){
            throw RuntimeException("User does not have permission to view posts")
        }
        return postService.findPost(postId).toDTO()
    }

    fun editPost(postBody: PostDTO, postId: Long): PostDTO? {
        val authentication = authService.getCurrentUser()
        val oldPost = postService.findPost(postId)

        if(oldPost.entityStatus == EntityStatus.DELETED || oldPost.entityStatus == EntityStatus.SUSPENDED){
            throw RuntimeException("Post deleted or suspended")
        }

        if(!userPermissionService.havePermission(POST_PERMISSION, PermissionLayer.EDIT)
            || oldPost.createdBy != authentication ){
            throw RuntimeException("User does not have permission to edit posts")
        }

        return postService.editPost(postBody, oldPost).toDTO()
    }

    fun deletePost(postId: Long): String {
        val post = postService.findPost(postId)

        if(post.entityStatus == EntityStatus.DELETED){
            throw RuntimeException("Post already deleted")
        }

        if(!userPermissionService.havePermission(POST_PERMISSION, PermissionLayer.DELETE)){
            throw RuntimeException("User does not have permission to delete or is not author of the post")
        }

        return postService.deletePost(post)
    }


}