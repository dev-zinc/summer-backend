package zinc.doiche.anifadmin.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import zinc.doiche.anifadmin.domain.notification.Notification
import java.util.*

@Repository
interface NotificationRepository: MongoRepository<Notification, Long> {
    override fun findById(id: Long): Optional<Notification>
}
