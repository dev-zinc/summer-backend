package zinc.doiche.anifadmin.repository

import org.springframework.data.mongodb.repository.MongoRepository
import zinc.doiche.anifadmin.domain.user.User
import java.util.*

interface UserRepository: MongoRepository<User, UUID> {
    fun findByUuid(uuid: UUID): User?
    fun findByDiscordId(discordId: Long): User?
}