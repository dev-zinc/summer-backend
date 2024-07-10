package zinc.doiche.anifadmin.document.user

import org.bson.types.ObjectId
import org.springframework.boot.json.GsonJsonParser
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration
import java.util.UUID

private const val PROFILE_REQUEST_URL = "https://sessionserver.mojang.com/session/minecraft/profile/"

@Document(collection = "user")
class User(
    val id: ObjectId,
    val uuid: UUID,
    val jobStat: JobStat,
    val wallet: Wallet,
    val decoration: Decoration,
    val playerPrefix: PlayerPrefix,
    val playerAutoSeed: PlayerAutoSeed,
    val playerFly: PlayerFly,
    val personalSettings: PersonalSettings,
    val grade: Grade,
    val chatMode: String,
    val mbti: List<String>
) {
    private var name: String? = null
        private set
        get() = field ?: getNameByMojang()

    private fun getNameByMojang(): String? {
        val webClient = WebClient.create();
        val url = PROFILE_REQUEST_URL + uuid.toString()

        val json = webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(String::class.java)
            .block(Duration.ofSeconds(5))

        return json?.let {
            GsonJsonParser().parseMap(it)
        }?.let {
            val name = it["name"] as? String
            this.name = name
            name
        }
    }
}