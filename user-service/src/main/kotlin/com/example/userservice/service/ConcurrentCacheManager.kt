package com.example.userservice.service

import org.springframework.stereotype.Component
import java.time.Duration
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap


@Component
class ConcurrentCacheManager<T> {
    private val localCache = ConcurrentHashMap<String, CacheWrapper<T>>()

    suspend fun awaitPut(key: String, value: T, ttl: Duration) {
        localCache[key] = CacheWrapper(value, Instant.now().plusMillis(ttl.toMillis()))
    }

    suspend fun awaitEvict(key: String) {
        localCache.remove(key)
    }

    suspend fun awaitGetOrPut(
        key: String,
        ttl: Duration? = Duration.ofMinutes(5),
        supplier: suspend () -> T,
    ): T {
        val now = Instant.now()
        val existingCacheWrapper = localCache[key]

        if (existingCacheWrapper == null) {
            val newCacheWrapper = CacheWrapper(
                cached = supplier(),
                ttl = now.plusMillis(ttl!!.toMillis()),
            ).also {
                localCache[key] = it
            }

            return newCacheWrapper.cached
        }

        if (now.isAfter(existingCacheWrapper.ttl)) {
            localCache.remove(key)
            val newCacheWrapper = CacheWrapper(
                cached = supplier(),
                ttl = now.plusMillis(ttl!!.toMillis()),
            ).also {
                localCache[key] = it
            }

            return newCacheWrapper.cached
        }


        return existingCacheWrapper.cached

    }

    data class CacheWrapper<T>(val cached: T, val ttl: Instant)
}