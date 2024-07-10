package zinc.doiche.anifadmin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * 1. 디스코드 알림 퍼나르기
 * 2. 유저 정보 퍼나르기
 */

@SpringBootApplication
class AnifAdminApplication

fun main(args: Array<String>) {
    runApplication<AnifAdminApplication>(*args)
}


