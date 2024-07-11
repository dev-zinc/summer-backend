package zinc.doiche.anifadmin.service

import io.github.oshai.kotlinlogging.KLogger
import org.springframework.data.domain.PageRequest
import org.springframework.data.web.PagedModel
import org.springframework.stereotype.Service
import zinc.doiche.anifadmin.domain.notification.Notification
import zinc.doiche.anifadmin.domain.notification.NotificationType
import zinc.doiche.anifadmin.repository.NotificationRepository
import zinc.doiche.anifadmin.util.FlutterAdaptor

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val logger: KLogger
): ReadService<Notification, Long> {
    override fun get(id: Long): Notification? {
        return notificationRepository.findById(id).orElse(null)
    }

    override fun getPage(page: Int, size: Int): PagedModel<Notification> {
        val notifications = notificationRepository.findAll(PageRequest.of(page, size))
        val pagedModel = PagedModel(notifications)
        return pagedModel
    }

    fun getList(): List<Notification> {
        return notificationRepository.findAll()
    }

    fun getCounter(): Map<String, Int> {
        val notifications = notificationRepository.findAll()
        val counter = NotificationType.entries.associate { type ->
            notifications.count { notification ->
                notification.notificationTypes.contains(type)
            }.let { count ->
                FlutterAdaptor.toCamelCase(type.name.lowercase()) to count
            }
        }
        return counter
    }
}