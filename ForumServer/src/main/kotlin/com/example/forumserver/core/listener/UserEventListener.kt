package com.example.forumserver.core.listener

import com.example.forumserver.core.entity.events.UserCreatedEvent
import com.example.forumserver.core.service.UserPermissionService
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserEventListener(
    private val userPermissionService: UserPermissionService
)
{

    @EventListener
    fun handleUserCreated(event: UserCreatedEvent){

        userPermissionService.addPermissions(event.user, event.permissions, event.user)
    }
}