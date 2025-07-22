package com.example.forumserver.api.mapper

import com.example.forumserver.api.request.LoginRequest
import com.example.forumserver.api.request.RegisterRequest
import com.example.forumserver.core.entity.enums.PermissionLayer
import com.example.forumserver.core.entity.helper_class.Permission
import com.example.forumserver.core.entity.helper_class.PermissionsConfig
import com.example.forumserver.core.service.AuthService
import com.example.forumserver.core.service.PermissionService
import com.example.forumserver.core.service.UserDetailsService
import org.springframework.stereotype.Component
import kotlin.collections.forEach


@Component
class AuthMapper(
    private val userDetailsService: UserDetailsService,
    private val authService: AuthService,
    private val permissionService: PermissionService,
    private val permissionsConfig: PermissionsConfig
) {

    fun login(loginRequest: LoginRequest): String {
        return authService.login(loginRequest.username, loginRequest.password)
    }

    fun register(registerRequest: RegisterRequest): String {
        with(registerRequest) {

            userDetailsService.loadUserByUsername(username)?.let {
                throw RuntimeException("Username already exists")
            }

            userDetailsService.loadUserByEmail(email)?.let {
                throw RuntimeException("Email already exists")
            }
            val permissionsFromFile = permissionsConfig.permissions["ROLE_BASIC_USER"] ?: throw RuntimeException("Role basic user is not found exception")
            val permissionsForRole = mutableMapOf<Permission, PermissionLayer>()
            val allPermissions = permissionService.listPermissions().associateBy { it.title }

            permissionsFromFile.forEach { (title, layer) ->
                val curPerm = allPermissions[title] ?: throw RuntimeException("Permission title is missing in database exception")
                permissionsForRole[curPerm] = layer
            }

            val token = authService.register(
                username = username,
                password = password,
                firstName = firstName,
                lastName = lastName,
                email = email,
                permissions = permissionsForRole
            )

            return token
        }
    }

}