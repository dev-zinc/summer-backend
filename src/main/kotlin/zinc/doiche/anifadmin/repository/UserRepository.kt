package zinc.doiche.anifadmin.repository

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.MongoRepository
import zinc.doiche.anifadmin.domain.user.User

interface UserRepository: MongoRepository<User, String> {
    fun findByUuid(uuid: String): User?
    fun findByDiscordId(discordId: Long): User?
}