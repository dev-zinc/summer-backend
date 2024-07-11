package zinc.doiche.anifadmin.listener

import io.github.oshai.kotlinlogging.KLogger
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent
import net.dv8tion.jda.api.hooks.SubscribeEvent
import zinc.doiche.anifadmin.domain.notification.Notification
import zinc.doiche.anifadmin.domain.notification.NotificationType
import zinc.doiche.anifadmin.repository.NotificationRepository
import zinc.doiche.anifadmin.repository.UserRepository

class ForumListener(
    private val logger: KLogger,
    private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository,
) {

    @SubscribeEvent
    fun onForumPostUpdated(event: ChannelCreateEvent) {
        val channel = event.channel

        if (channel.type.isThread && channel.asThreadChannel().parentChannel.type == ChannelType.FORUM) {
            val threadChannel = channel.asThreadChannel()
            val forum = threadChannel.parentChannel
            val title = threadChannel.name
            val tags = threadChannel.appliedTags
            val createdDateTime = channel.timeCreated.toLocalDateTime()
            val user = userRepository.findByDiscordId(threadChannel.ownerIdLong)

//            logger.info { "Forum Post Updated: $createdDateTime, $title, $tags" }
//            logger.info { forum }
//            logger.info { threadChannel.ownerIdLong }

            val notification = Notification(
                threadChannel.idLong,
                user,
                tags.map { NotificationType.fromForumTag(it) },
                title,
                createdDateTime,
                threadChannel.retrieveStartMessage().complete().jumpUrl
            )

            notificationRepository.save(notification)
        }
    }
}