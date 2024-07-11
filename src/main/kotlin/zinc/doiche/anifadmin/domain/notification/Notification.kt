package zinc.doiche.anifadmin.domain.notification

import org.springframework.data.mongodb.core.mapping.Document
import zinc.doiche.anifadmin.domain.user.User
import java.time.LocalDateTime

@Document(collection = "notification")
class Notification(
    val id: Long,
    val user: User?,
    val notificationTypes: MutableList<NotificationType>,
    val title: String,
    val createdDateTime: LocalDateTime,
    val discordUrl: String,
) {
    override fun toString(): String {
        return "Notification(id=$id, user=$user, notificationTypes=$notificationTypes, title='$title', createdDateTime=$createdDateTime, discordUrl='$discordUrl')"
    }
}