package com.example.forumserver.core.service

import com.example.forumserver.core.configuration.JwtUtil
import com.example.forumserver.core.entity.helper_class.Role
import com.example.forumserver.core.entity.helper_class.User
import com.example.forumserver.core.repository.UserRepository
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
    private val userRepository: UserRepository
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
                 email: String,): String {
        var user = User(
            username = username,
            password = passwordEncoder.encode(password),
            firstName = firstName,
            lastName = lastName,
            email = email,
            role = Role.ROLE_USER,
            dateCreated = LocalDateTime.now(),
            lastDateModified = LocalDateTime.now(),
        )

        user = userRepository.save(user)
        return jwtUtil.generateToken(user) // Might be redundant generating token
    }
}