package com.example.forumserver.core.service

import com.example.forumserver.core.entity.helper_class.PostEntity
import com.example.forumserver.core.repository.PostRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PostService(
    private val postRepository: PostRepository
) {
    fun findPosts(thread: Any, pageable: Pageable): Page<PostEntity>{ //todo implement Thread class
        return postRepository.findAll(pageable)
    }
}