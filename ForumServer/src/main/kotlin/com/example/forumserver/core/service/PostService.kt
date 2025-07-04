package com.example.forumserver.core.service

import com.example.forumserver.api.dto.PostDTO
import com.example.forumserver.core.entity.helper_class.PostEntity
import com.example.forumserver.core.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PostService(
    private val postRepository: PostRepository,
    private val threadService: ThreadService,
    private val authService: AuthService,
) {
    fun findPosts(threadId: Long, pageable: Pageable): Page<PostEntity>{
        val thread = threadService.findThread(threadId)

        return postRepository.findByThread(thread, pageable)
    }

    fun findPost(postId: Long): PostEntity =
        postRepository.findById(postId)
            .orElseThrow { RuntimeException("Post with $postId does not exists exception!") }

    fun createPost(request: PostDTO): PostEntity {
        if(request.html.isBlank()){
            throw RuntimeException("Content provided is blank exception!")
        }
        val createdBy = authService.getCurrentUser()
        val thread = threadService.findThread(request.threadId)

        val postEntity = PostEntity(
            id = null,
            html = request.html,
            thread = thread,
            createdBy = createdBy,
            lastModifiedBy = createdBy,
            dateCreated = LocalDateTime.now(),
            lastDateModified = LocalDateTime.now()
        )

        return postRepository.save(postEntity)
    }

    fun editPost(postBody: PostDTO, oldPost: PostEntity): PostEntity {
        if(postBody.html.isBlank()){
            throw RuntimeException("Content provided is blank exception!")
        }
        val lastModifiedBy = authService.getCurrentUser()

        val editedPost = oldPost.copy(
            html = postBody.html,
            lastDateModified = LocalDateTime.now(),
            lastModifiedBy = lastModifiedBy
        )

        return postRepository.save(editedPost)
    }
}