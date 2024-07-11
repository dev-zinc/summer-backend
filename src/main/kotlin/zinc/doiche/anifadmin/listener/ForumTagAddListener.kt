package zinc.doiche.anifadmin.listener

import net.dv8tion.jda.api.events.channel.forum.ForumTagAddEvent
import net.dv8tion.jda.api.hooks.SubscribeEvent
import zinc.doiche.anifadmin.domain.notification.NotificationType
import zinc.doiche.anifadmin.repository.NotificationRepository

class ForumTagAddListener(
    private val notificationRepository: NotificationRepository,
) {
    @SubscribeEvent
    fun onForumPostTagUpdate(event: ForumTagAddEvent) {
        val channel = event.channel
        val notification = notificationRepository.findById(channel.idLong).orElse(null) ?: return
        val tag = event.tag
        val type = NotificationType.fromForumTag(tag)

        notification.notificationTypes.add(type)
        notificationRepository.save(notification)
    }
}