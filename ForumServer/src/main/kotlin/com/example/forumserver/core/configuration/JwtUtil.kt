package com.example.forumserver.core.configuration

import com.example.forumserver.core.entity.helper_class.User
import io.jsonwebtoken.*
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil() {

    private val secretKey: SecretKey = Jwts.SIG.HS256.key().build()


    fun generateToken(userDetails: UserDetails): String {
        val claims: MutableMap<String, Any> = HashMap()
        val user = userDetails as User
        claims["userId"] = user.id as Any
        return Jwts.builder()
            .claims(claims)
            .subject(user.username)
            .issuedAt(Date())
            .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
            .signWith(secretKey)
            .compact()
    }

    fun extractUsername(token: String): String {
        //todo add check for when it is missing
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).payload.subject
    }

    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return Jwts.parser().verifyWith(secretKey).build()
            .parseSignedClaims(token)
            .payload.expiration.before(Date())
    }
}