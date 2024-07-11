package zinc.doiche.anifadmin.domain.notification

import org.springframework.data.mongodb.core.mapping.Document
import zinc.doiche.anifadmin.domain.user.User
import java.time.LocalDateTime

@Document(collection = "notification")
class Notification(
    private val id: Long,
    private val user: User?,
    private val notificationTypes: List<NotificationType>,
    private val title: String,
    private val createdDateTime: LocalDateTime,
    private val discordUrl: String,
) {
    override fun toString(): String {
        return "Notification(id=$id, user=$user, notificationTypes=$notificationTypes, title='$title', createdDateTime=$createdDateTime, discordUrl='$discordUrl')"
    }
}