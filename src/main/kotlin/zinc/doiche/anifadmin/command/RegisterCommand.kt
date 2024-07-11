package zinc.doiche.anifadmin.command

import io.github.oshai.kotlinlogging.KLogger
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.scheduler.Schedulers
import zinc.doiche.anifadmin.lib.command.CommandFactory
import zinc.doiche.anifadmin.repository.UserRepository
import java.net.URI
import java.time.Duration

//private val requestMap = mutableMapOf<Long, Int>()
private const val PROFILE_REQUEST_URL = "https://api.mojang.com/users/profiles/minecraft/"

internal fun registerCommand(
    userRepository: UserRepository,
    logger: KLogger
) = CommandFactory.create(
    "등록",
    Commands.slash("등록", "마인크래프트 닉네임을 등록할 수 있어요.")
        .addOption(OptionType.STRING, "닉네임", "마인크래프트 닉네임을 입력해주세요.", true)
) { event ->
    val nickname = event.getOption("닉네임")?.asString ?: ""

    if(event.channelType == ChannelType.TEXT) {
//        val answer = Random.nextInt(999999)

//        requestMap[event.user.idLong] = answer

        WebClient.create()
            .get()
            .uri(URI(PROFILE_REQUEST_URL + nickname))
            .retrieve()
            .bodyToMono(Profile::class.java)
            .publishOn(Schedulers.boundedElastic())
            .doOnSuccess {
                val uuid = "${it.id.substring(0, 8)}-${it.id.substring(8, 12)}-${it.id.substring(12, 16)}-${it.id.substring(16, 20)}-${it.id.substring(20)}"
                userRepository.findByUuid(uuid)?.let { user ->
                    user.discordId = event.user.idLong
                    userRepository.save(user)
                } ?: run {
                    event.reply("서버에 등록되지 않은 계정이에요.")
                        .setEphemeral(true)
                        .queue()
                    return@doOnSuccess
                }
                event.reply("등록되었어요!")
                    .setEphemeral(true)
                    .queue()
            }
            .doOnError {
                event.reply("마인크래프트 닉네임이 존재하지 않아요.")
                    .setEphemeral(true)
                    .queue()
            }
            .block(Duration.ofSeconds(5))
//        event.reply("마인크래프트 클라이언트로 전송된 숫자를 '/인증'을 통해 입력해주세요!")


//        Timer().schedule(object : TimerTask() {
//            override fun run() {
//                requestMap.remove(event.user.idLong)
//            }
//        }, 1000 * 30)
    }
}

data class Profile(
    val id: String,
    val name: String
) {
    override fun toString(): String {
        return "Profile(id='$id', name='$name')"
    }
}

//internal fun authenticateCommand() = CommandFactory.create(
//    "인증",
//    Commands.slash("인증", "마인크래프트 닉네임을 인증할 수 있어요.")
//        .addOption(OptionType.INTEGER, "숫자", "마인크래프트 클라이언트로 전송된 숫자를 입력해주세요.", true)
//) { event ->
//    val input = event.getOption("숫자")?.asInt ?: 0
//
//    if(event.channelType == ChannelType.TEXT) {
//        requestMap[event.user.idLong]?.let { answer ->
//            if(answer == input) {
//                event.hook
//                    .sendMessage("마인크래프트 닉네임이 성공적으로 등록되었습니다.")
//                    .setEphemeral(true)
//                    .queue()
//                return@create
//            }
//            event.hook
//                .sendMessage("마인크래프트 클라이언트로 전송된 숫자와 입력한 숫자가 일치하지 않아요.")
//                .setEphemeral(true)
//                .queue()
//        }
//    }
//}