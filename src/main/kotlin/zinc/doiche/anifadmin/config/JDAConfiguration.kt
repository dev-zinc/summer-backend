package zinc.doiche.anifadmin.config

import io.github.oshai.kotlinlogging.KLogger
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.hooks.AnnotatedEventManager
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag
import okhttp3.OkHttpClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import zinc.doiche.anifadmin.command.registerCommand
import zinc.doiche.anifadmin.lib.command.CommandFactory
import zinc.doiche.anifadmin.lib.command.register
import zinc.doiche.anifadmin.listener.ForumListener
import zinc.doiche.anifadmin.listener.ForumTagAddListener
import zinc.doiche.anifadmin.repository.NotificationRepository
import zinc.doiche.anifadmin.repository.UserRepository
import java.time.Duration

@Configuration
class JDAConfiguration(
    private val logger: KLogger,
    private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository
) {
    @Value("\${discord.token}")
    private lateinit var token: String

    @Bean
    fun jda(): JDA {
        val jda = JDABuilder.createDefault(token)
            .setEventManager(AnnotatedEventManager())
            .addEventListeners(
                ForumListener(logger, userRepository, notificationRepository),
                ForumTagAddListener(notificationRepository),
                CommandFactory()
            )
            .setHttpClientBuilder(
                OkHttpClient.Builder()
                .callTimeout(Duration.ofMinutes(1))
                .connectTimeout(Duration.ofMinutes(1))
                .readTimeout(Duration.ofMinutes(1))
                .writeTimeout(Duration.ofMinutes(1)))
            .enableIntents(GatewayIntent.MESSAGE_CONTENT)
            .enableCache(CacheFlag.FORUM_TAGS)
            .build()
            .awaitReady()

        registerCommand(userRepository, logger).register(jda)

        return jda
    }
}