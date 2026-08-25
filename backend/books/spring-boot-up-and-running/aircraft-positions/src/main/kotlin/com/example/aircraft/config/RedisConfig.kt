package com.example.aircraft.config

import com.example.aircraft.infrastructure.redis.entity.AircraftCache
import org.springframework.cache.CacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import tools.jackson.databind.ObjectMapper
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator
import java.time.Duration

@Configuration
class RedisConfig {

    @Bean
    fun cacheManager(factory: RedisConnectionFactory): CacheManager {
        val ptv = BasicPolymorphicTypeValidator.builder()
            .allowIfBaseType(Any::class.java)
            .build()

        val serializer = GenericJacksonJsonRedisSerializer.builder()
            .enableDefaultTyping(ptv)
            .build()
        
        val cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofSeconds(60))
            .disableCachingNullValues()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(StringRedisSerializer()))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer))

        return RedisCacheManager.builder(factory)
            .cacheDefaults(cacheConfig)
            .build()
    }

    @Bean
    fun redisTemplate(factory: RedisConnectionFactory, objectMapper: ObjectMapper): RedisTemplate<String, AircraftCache> {
        val serializer = JacksonJsonRedisSerializer(objectMapper, AircraftCache::class.java)

        return RedisTemplate<String, AircraftCache>().apply {
            connectionFactory = factory
            keySerializer = StringRedisSerializer()
            valueSerializer = serializer
            hashKeySerializer = StringRedisSerializer()
            hashValueSerializer = serializer
            afterPropertiesSet()
        }
    }
}
