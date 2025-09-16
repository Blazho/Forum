package com.example.forumserver.core.service

import com.example.forumserver.core.configuration.JwtUtil
import com.example.forumserver.core.entity.enums.EntityStatus
import com.example.forumserver.core.entity.enums.PermissionLayer
import com.example.forumserver.core.entity.events.UserCreatedEvent
import com.example.forumserver.core.entity.helper_class.Permission
import com.example.forumserver.core.entity.helper_class.Role
import com.example.forumserver.core.entity.helper_class.User
import com.example.forumserver.core.repository.UserRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthService(
    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val userRepository: UserRepository,
    private val eventPublisher: ApplicationEventPublisher
) {

    fun login(username: String, password: String): String {
        val user = userDetailsService.loadUserByUsername(username)
            ?: throw UsernameNotFoundException("User not found")

        val authentication = authenticationManager.authenticate(UsernamePasswordAuthenticationToken(username, password))

        SecurityContextHolder.getContext().authentication = authentication

        return jwtUtil.generateToken(user)
    }

    fun register(username: String,
                 password: String,
                 firstName: String,
                 lastName: String,
                 email: String,
                 permissions: Map<Permission, PermissionLayer>): String {
        var user = User(
            username = username,
            password = passwordEncoder.encode(password),
            firstName = firstName,
            lastName = lastName,
            email = email,
            role = Role.ROLE_BASIC_USER,
            dateCreated = LocalDateTime.now(),
            lastDateModified = LocalDateTime.now(),
            entityStatus = EntityStatus.ACTIVE
        )

        user = userRepository.save(user)

        eventPublisher.publishEvent(UserCreatedEvent(this, user, permissions))

        return jwtUtil.generateToken(user) // Might be redundant generating token
    }

    fun getCurrentUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("No authentication found")
        return authentication.principal as? User
            ?: throw IllegalStateException("Principal is not of type User")
    }
}