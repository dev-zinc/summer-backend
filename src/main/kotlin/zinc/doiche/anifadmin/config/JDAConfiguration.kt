package zinc.doiche.anifadmin.config

import io.github.oshai.kotlinlogging.KLogger
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.hooks.AnnotatedEventManager
import net.dv8tion.jda.api.requests.GatewayIntent
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import zinc.doiche.anifadmin.listener.ForumListener

@Configuration
class JDAConfiguration(private val logger: KLogger) {

    @Value("\${discord.token}")
    private lateinit var token: String

    @Bean
    fun jda() = JDABuilder.createDefault(token)
        .setEventManager(AnnotatedEventManager())
        .addEventListeners(
            ForumListener(logger)
        )
        .enableIntents(GatewayIntent.MESSAGE_CONTENT)
        .build()
        .awaitReady()
}