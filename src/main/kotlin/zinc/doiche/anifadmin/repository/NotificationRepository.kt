package zinc.doiche.anifadmin.repository

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import zinc.doiche.anifadmin.domain.notification.Notification
import zinc.doiche.anifadmin.domain.notification.NotificationType

@Repository
class NotificationRepository(
    private val jda: JDA,
    private val userRepository: UserRepository
) {
    @Value("\${discord.channelId}")
    private var channelId: Long? = null

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

        return Notification(
            thread.idLong,
            user,
            thread.appliedTags.map { NotificationType.fromForumTag(it) },
            "title",
            thread.timeCreated.toLocalDateTime(),
            thread.retrieveStartMessage().complete().jumpUrl
        )
    }

    fun findAll(page: Int, size: Int): Page<Notification>? {
        return channelId?.let { id ->
            jda.getForumChannelById(id)
        }?.let { forum ->
            val threadChannelCache = jda.threadChannelCache
            val count = threadChannelCache.applyStream { stream ->
                stream.filter { it.parentChannel == forum }
            }.count()

            threadChannelCache.applyStream { stream ->
                stream.filter {
                    it.parentChannel == forum
                }.map {
                    findByThread(it)
                }
            }.let { stream ->
                PageImpl(stream.toList(), PageRequest.of(page, size), count)
            }
        }
    }
}
