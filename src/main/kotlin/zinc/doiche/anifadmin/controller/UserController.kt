package zinc.doiche.anifadmin.controller

import io.github.oshai.kotlinlogging.KLogger
import org.springframework.data.domain.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zinc.doiche.anifadmin.domain.user.NotExist
import zinc.doiche.anifadmin.domain.user.User
import zinc.doiche.anifadmin.service.user.UserService
import java.util.*

@RestController
@RequestMapping("/api/v1/user/*")
class UserController(
    private val logger: KLogger,
    private val userService: UserService
) {
    @GetMapping("/{uuid}")
    fun user(@PathVariable uuid: UUID): Any {
        return userService.get(uuid) ?: NotExist("User", "존재하지 않는 유저입니다.")
    }

    @GetMapping("/{page}/{size}")
    fun userPage(@PathVariable page: Int, @PathVariable size: Int): Page<User> {
        return userService.getPage(page, size)
    }
}