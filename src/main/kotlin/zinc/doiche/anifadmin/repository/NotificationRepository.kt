package zinc.doiche.anifadmin.repository

import com.google.gson.Gson
import io.github.oshai.kotlinlogging.KLogger
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Repository
import zinc.doiche.anifadmin.domain.notification.Notification
import zinc.doiche.anifadmin.domain.notification.NotificationType

@Repository
class NotificationRepository(
    private val jda: JDA,
    private val userRepository: UserRepository,
    private val logger: KLogger
) {
    @Value("\${discord.channelName}")
    private lateinit var channelName: String

    private fun getThreadById(id: Long): ThreadChannel? {
        return jda.getThreadChannelById(id)
    }

    fun findById(id: Long): Notification? {
        return getThreadById(id)?.let {
            findByThread(it)
        }
    }

    fun findByThread(thread: ThreadChannel): Notification? {
        val user = userRepository.findByDiscordId(thread.ownerIdLong) ?: return null
        val notification = Notification(
            thread.idLong,
            user,
            thread.appliedTags.map { NotificationType.fromForumTag(it) },
            "title",
            thread.timeCreated.toLocalDateTime(),
            thread.retrieveStartMessage().complete().jumpUrl
        )
        logger.info { notification }
        return notification
    }

    fun findAll(page: Int, size: Int): Page<Notification> {
        val skipSize = (page - 1) * size

        return channelName.let { name ->
            logger.info { name }
            jda.getForumChannelsByName(name, false)
                .takeIf {
                    logger.info { it.size }
                    it.isNotEmpty()
                }
                ?.first()
        }?.let { forum ->
            forum.threadChannels.subList(skipSize, skipSize + size)
                .map {
                    findByThread(it)!!
                }
                .let {
                    PageImpl(it)
                }.apply {
                    logger.info { this }
                }
        } ?: Page.empty()
    }
}
