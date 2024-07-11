package zinc.doiche.anifadmin.lib.command

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.SubscribeEvent
import net.dv8tion.jda.api.interactions.commands.build.CommandData

class CommandFactory {

    @SubscribeEvent
    fun onCommand(event: SlashCommandInteractionEvent) {
        if(event.name in commands) {
            commands[event.name]?.onCommand(event)
        }
    }

    companion object {
        private val commands = HashMap<String, Command>()

        fun register(jda: JDA, command: Command) {
            jda.upsertCommand(command.commandData).queue()
            commands[command.name] = command
        }

        fun create(
            name: String,
            commandData: CommandData,
            onCommand: (SlashCommandInteractionEvent) -> Unit
        ) = object: Command {
            override val name = name
            override val commandData = commandData

            override fun onCommand(event: SlashCommandInteractionEvent) {
                onCommand(event)
            }
        }
    }
}

internal fun Command.register(jda: JDA) {
    CommandFactory.register(jda, this)
}