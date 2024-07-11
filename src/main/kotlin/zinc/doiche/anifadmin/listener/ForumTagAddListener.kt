package zinc.doiche.anifadmin.listener

import net.dv8tion.jda.api.events.channel.update.ChannelUpdateAppliedTagsEvent
import net.dv8tion.jda.api.hooks.SubscribeEvent
import zinc.doiche.anifadmin.domain.notification.NotificationType
import zinc.doiche.anifadmin.repository.NotificationRepository

class ForumTagAddListener(
    private val notificationRepository: NotificationRepository,
) {
    @SubscribeEvent
    fun onForumPostTagUpdate(event: ChannelUpdateAppliedTagsEvent) {
        val channel = event.channel
        val notification = notificationRepository.findById(channel.idLong).orElse(null) ?: return
        val tags = event.addedTags
        val types = tags.map { NotificationType.fromForumTag(it) }

        notification.notificationTypes.addAll(types)
        notificationRepository.save(notification)
    }
}