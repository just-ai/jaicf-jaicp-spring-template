package com.justai.jaicf.spring.configuration

import com.justai.jaicf.BotEngine
import com.justai.jaicf.activator.caila.CailaIntentActivator
import com.justai.jaicf.activator.caila.CailaNLUSettings
import com.justai.jaicf.activator.regex.RegexActivator
import com.justai.jaicf.api.BotApi
import com.justai.jaicf.channel.jaicp.JaicpWebhookConnector
import com.justai.jaicf.channel.jaicp.channels.ChatWidgetChannel
import com.justai.jaicf.channel.jaicp.logging.JaicpConversationLogger
import com.justai.jaicf.context.manager.mongo.MongoBotContextManager
import com.justai.jaicf.logging.Slf4jConversationLogger
import com.justai.jaicf.spring.scenario.MainScenario
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDatabaseFactory

@Configuration
class ApplicationConfiguration(
    private val botConfiguration: BotConfiguration,
    private val mongoDatabaseFactory: MongoDatabaseFactory
) {

    @Bean
    fun botApi(mainScenario: MainScenario) = BotEngine(
        scenario = mainScenario,
        defaultContextManager = mongoDatabaseFactory.createContextManager(botConfiguration.mongoCollection),
        activators = arrayOf(RegexActivator, createCailaForToken(botConfiguration.accessToken)),
        conversationLoggers = arrayOf(
            JaicpConversationLogger(botConfiguration.accessToken),
            Slf4jConversationLogger()
        )
    )

    @Bean
    fun jaicpWebhookConnector(botApi: BotApi) = JaicpWebhookConnector(
        botApi = botApi,
        accessToken = botConfiguration.accessToken,
        channels = listOf(ChatWidgetChannel)
    )

    companion object {
        private fun createCailaForToken(token: String) = CailaIntentActivator.Factory(CailaNLUSettings(token))

        private fun MongoDatabaseFactory.createContextManager(collection: String) =
            MongoBotContextManager(mongoDatabase.getCollection(collection))
    }
}