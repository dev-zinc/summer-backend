package zinc.doiche.anifadmin.repository

import io.github.oshai.kotlinlogging.KLogger
import net.dv8tion.jda.api.JDA
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class NotificationRepositoryTest {

    @Autowired
    private lateinit var notificationRepository: NotificationRepository

    @Autowired
    private lateinit var jda: JDA

    @Autowired
    private lateinit var logger: KLogger

    @Test
    fun findByIdTest() {
        assert(notificationRepository.findById(1260507648466616320) != null)
    }

    @Test
    fun findByThread() {
        findByIdTest()
    }

    @Test
    fun findAll() {
        val findAll = notificationRepository.findAll(1, 2)
        logger.info { "=================$findAll" }
        assert(findAll.hasContent())
    }
}