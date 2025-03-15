package com.example.forumserver.core.filter


import com.example.forumserver.core.configuration.JwtUtil
import com.example.forumserver.core.service.UserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component

@Component
class JwtFilter(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: UserDetailsService,
    authenticationManager: AuthenticationManager,
) : UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun doFilter(req: ServletRequest, res: ServletResponse, chain: FilterChain) {
        val request = req as HttpServletRequest
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            val token = authHeader.substring(7)

            if (SecurityContextHolder.getContext().authentication == null) {
                val username = jwtUtil.extractUsername(token)
                val userDetails = userDetailsService.loadUserByUsername(username)
                    ?: throw UsernameNotFoundException("User not found")

                if (jwtUtil.validateToken(token, userDetails)) {
                    val authToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                    authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                    SecurityContextHolder.getContext().authentication = authToken
                }
            }
        }
        chain.doFilter(req, res)
    }


}