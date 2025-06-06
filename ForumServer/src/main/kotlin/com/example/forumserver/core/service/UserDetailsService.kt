package com.example.forumserver.core.service

import com.example.forumserver.core.entity.helper_class.User
import com.example.forumserver.core.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsService(
    private val userRepository: UserRepository,

): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails? {
        return userRepository.findByUsername(username)
    }

    fun loadUserByEmail(email: String): UserDetails? {
        return userRepository.findByEmail(email)
    }


    fun findUserById(userId: Long): User {
        return userRepository.findById(userId)
            .orElseThrow { RuntimeException("User not found exception!") }
    }

}