package zinc.doiche.anifadmin.controller

import io.github.oshai.kotlinlogging.KLogger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/*")
class MainController(
    private val logger: KLogger
) {
    @GetMapping("/test")
    fun ping(): Test {
        logger.info { "ping" }
        return Test("doiche", 20)
    }
}

data class Test(val name: String, val age: Int)