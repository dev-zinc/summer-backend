package zinc.doiche.anifadmin.service

import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import zinc.doiche.anifadmin.domain.notification.Notification
import zinc.doiche.anifadmin.repository.NotificationRepository

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository
): ReadService<Notification, Long> {
    override fun get(id: Long): Notification? {
        return notificationRepository.findById(id)
    }

    override fun getPage(page: Int, size: Int): Page<Notification> {
        return notificationRepository.findAll(page, size) ?: Page.empty()
    }
}