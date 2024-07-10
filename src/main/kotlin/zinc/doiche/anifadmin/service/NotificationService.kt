package zinc.doiche.anifadmin.service

import com.google.gson.Gson
import io.github.oshai.kotlinlogging.KLogger
import org.springframework.data.web.PagedModel
import org.springframework.stereotype.Service
import zinc.doiche.anifadmin.domain.notification.Notification
import zinc.doiche.anifadmin.repository.NotificationRepository

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val logger: KLogger
): ReadService<Notification, Long> {
    override fun get(id: Long): Notification? {
        return notificationRepository.findById(id)
    }

    override fun getPage(page: Int, size: Int): PagedModel<Notification> {
        val notificationPage = notificationRepository.findAll(page, size)
        val pagedModel = PagedModel(notificationPage)
        logger.info { Gson().toJson(notificationPage) }
        logger.info { Gson().toJson(pagedModel) }
        return pagedModel
    }
}