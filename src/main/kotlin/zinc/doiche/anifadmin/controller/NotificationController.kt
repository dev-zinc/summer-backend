package zinc.doiche.anifadmin.controller

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KLogger
import org.springframework.data.web.PagedModel
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zinc.doiche.anifadmin.domain.notification.Notification
import zinc.doiche.anifadmin.domain.user.NotExist
import zinc.doiche.anifadmin.service.NotificationService

@RestController
@RequestMapping("/api/v1/notification/*")
class NotificationController(
    private val logger: KLogger,
    private val notificationService: NotificationService,
    private val jacksonObjectMapper: ObjectMapper
) {
    @GetMapping("/{id}")
    fun notification(@PathVariable id: Long): Any {
        return notificationService.get(id) ?: NotExist("Notification", "존재하지 않는 알림입니다.")
    }

//    @GetMapping("/{type}/{page}/{size}")
//    fun notificationPageByType(@PathVariable type: NotificationType, @PathVariable page: Int, @PathVariable size: Int): PagedModel<Notification> {
//        return notificationService.getPage(page, size)
//    }

    @GetMapping("/list")
    fun notificationList(): List<Notification> {
        val notificationList = notificationService.getList()
        return notificationList
    }

    @GetMapping("/list/{page}/{size}")
    fun notificationPage(@PathVariable page: Int, @PathVariable size: Int): PagedModel<Notification> {
        val notificationPage = notificationService.getPage(page, size)
        return notificationPage
    }

//    @GetMapping("/counter")
//    fun notificationCounter(): Any {
//        val counter = notificationService.getCounter()
//        return counter
//    }
}