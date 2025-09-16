package com.example.forumserver.core.repository

import com.example.forumserver.core.entity.enums.EntityStatus
import com.example.forumserver.core.entity.helper_class.Permission
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PermissionRepository : JpaRepository<Permission, Long>{

    fun findByEntityStatus(entityStatus: EntityStatus, pageable: Pageable): Page<Permission>


    fun findByTitleIgnoreCase(title: String): Permission?

}