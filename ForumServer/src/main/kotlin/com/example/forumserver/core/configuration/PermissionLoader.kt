package com.example.forumserver.core.configuration

import com.example.forumserver.core.entity.helper_class.PermissionsConfig
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class PermissionLoader {

    @Bean
    fun permissionConfig() : PermissionsConfig {
        val mapper = ObjectMapper()
        val inputSteam = javaClass.getResourceAsStream("/permissionConfig.json")
        val permissions = mapper.readValue(inputSteam, PermissionsConfig::class.java)
        return PermissionsConfig(permissions = permissions.permissions)
    }
}