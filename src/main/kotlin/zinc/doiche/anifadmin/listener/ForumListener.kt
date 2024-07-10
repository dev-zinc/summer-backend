package zinc.doiche.anifadmin.listener

import io.github.oshai.kotlinlogging.KLogger
import net.dv8tion.jda.api.entities.channel.ChannelType
import net.dv8tion.jda.api.events.channel.ChannelCreateEvent
import net.dv8tion.jda.api.hooks.SubscribeEvent

class ForumListener(private val logger: KLogger) {

    @SubscribeEvent
    fun onForumPostUpdated(event: ChannelCreateEvent) {
        val channel = event.channel

        if (channel.type.isThread && channel.asThreadChannel().parentChannel.type == ChannelType.FORUM) {
            val threadChannel = channel.asThreadChannel()
            val forum = threadChannel.parentChannel
            val title = threadChannel.name
            val tags = threadChannel.appliedTags
            val createdDateTime = channel.timeCreated.toLocalDateTime()

            logger.info { "Forum Post Updated: $createdDateTime, $title, $tags" }
            logger.info { forum }
        }
    }
}