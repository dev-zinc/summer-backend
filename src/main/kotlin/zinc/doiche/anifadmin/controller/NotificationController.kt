package zinc.doiche.anifadmin.controller

import io.github.oshai.kotlinlogging.KLogger
import net.dv8tion.jda.api.JDA
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/notification/*")
class NotificationController(
    private val logger: KLogger,
    private val jda: JDA
) {

}