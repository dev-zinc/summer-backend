package zinc.doiche.anifadmin.domain.notification

import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel
import zinc.doiche.anifadmin.domain.user.User
import java.time.LocalDateTime

class Notification(
    private val id: Long,
    private val user: User,
    private val notificationTypes: List<NotificationType>,
    private val title: String,
    private val createdDateTime: LocalDateTime,
    private val discordUrl: String,
) {

}