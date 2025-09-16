package com.example.forumserver.api.mapper

import com.example.forumserver.api.request.LoginRequest
import com.example.forumserver.api.request.RegisterRequest
import com.example.forumserver.api.response.LoginResponse
import com.example.forumserver.core.entity.enums.PermissionLayer
import com.example.forumserver.core.entity.helper_class.Permission
import com.example.forumserver.core.entity.helper_class.PermissionsConfig
import com.example.forumserver.core.service.AuthService
import com.example.forumserver.core.service.PermissionService
import com.example.forumserver.core.service.UserDetailsService
import com.example.forumserver.core.service.UserPermissionService
import org.springframework.stereotype.Component


@Component
class AuthMapper(
    private val userDetailsService: UserDetailsService,
    private val authService: AuthService,
    private val permissionService: PermissionService,
    private val permissionsConfig: PermissionsConfig,
    private val userPermissionService: UserPermissionService
) {

    fun login(loginRequest: LoginRequest): LoginResponse {
        val token = authService.login(loginRequest.username, loginRequest.password)
        val permissions = userPermissionService.getUserPermissions()
        if(permissions.isEmpty()){
            throw RuntimeException("User does not have permissions set in the database")
        }
        //Enum set to integer to avoid parsing the string to number on FE
        val permissionsMap = permissions.associate { it.title to it.permissionLayer.ordinal }
        val loginResponseObject = LoginResponse(
            token = token,
            userPermissions = permissionsMap
        )

        return loginResponseObject
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