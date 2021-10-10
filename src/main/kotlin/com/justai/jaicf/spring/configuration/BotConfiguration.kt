package com.justai.jaicf.spring.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "bot")
data class BotConfiguration(
    val accessToken: String,
    val mongoCollection: String,
    val helloImageUrl: String,
    val byeImageUrl: String
)
