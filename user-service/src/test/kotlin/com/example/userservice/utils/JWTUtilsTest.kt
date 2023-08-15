package com.example.userservice.utils

import com.example.userservice.config.JWTProperties
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class JWTUtilsTest {

    @Test
    fun createToken() {
        val jwtClaim = JwtClaim(
            userId = 1,
            email = "mokhs00@naver.com",
            profileUrl = "profile.jpg",
            username = "mohks00",
        )

        val properties = JWTProperties(
            issuer = "mokhs",
            subject = "auth",
            expiresTime = 3600,
            secret = "temp-secret",
        )

        val token = JWTUtils.createToken(claim = jwtClaim, properties = properties)

        assertNotNull(token)
    }

    @Test
    fun decode() {
        val jwtClaim = JwtClaim(
            userId = 1,
            email = "mokhs00@naver.com",
            profileUrl = "profile.jpg",
            username = "mohks00",
        )

        val properties = JWTProperties(
            issuer = "mokhs",
            subject = "auth",
            expiresTime = 3600,
            secret = "temp-secret",
        )

        val token = JWTUtils.createToken(claim = jwtClaim, properties = properties)

        val decode = JWTUtils.decode(token = token, secret = properties.secret, issuer = properties.issuer)

        with(decode) {
            assertEquals(claims["userId"]!!.asLong(), jwtClaim.userId)
            assertEquals(claims["email"]!!.asString(), jwtClaim.email)
            assertEquals(claims["profileUrl"]!!.asString(), jwtClaim.profileUrl)
            assertEquals(claims["username"]!!.asString(), jwtClaim.username)
        }
    }
}