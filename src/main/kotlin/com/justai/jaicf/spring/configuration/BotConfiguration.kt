package com.justai.jaicf.spring.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "bot")
data class BotConfiguration(
    var accessToken: String = "",
    var mongoCollection: String = "",
    var helloImageUrl: String = "",
    var byeImageUrl: String = ""
)
