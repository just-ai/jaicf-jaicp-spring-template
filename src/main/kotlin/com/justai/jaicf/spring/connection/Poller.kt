package com.justai.jaicf.spring.connection

import com.justai.jaicf.api.BotApi
import com.justai.jaicf.channel.jaicp.JaicpPollingConnector
import com.justai.jaicf.channel.jaicp.channels.ChatWidgetChannel
import com.justai.jaicf.spring.configuration.BotConfiguration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("dev")
class Poller(
    private val botApi: BotApi,
    private val botConfiguration: BotConfiguration
): ApplicationRunner, CoroutineScope {
    override val coroutineContext = Dispatchers.Default

    override fun run(args: ApplicationArguments?) {
        launch {
            JaicpPollingConnector(
                botApi = botApi,
                accessToken = botConfiguration.accessToken,
                channels = listOf(ChatWidgetChannel)
            ).runBlocking()
        }
    }
}