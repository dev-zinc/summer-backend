package zinc.doiche.anifadmin.lib.command

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

interface Command {
    val name: String
    val commandData: CommandData

    fun onCommand(event: SlashCommandInteractionEvent)
}