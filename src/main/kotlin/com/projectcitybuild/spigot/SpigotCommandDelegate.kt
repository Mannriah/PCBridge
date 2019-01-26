package com.projectcitybuild.spigot

import com.projectcitybuild.core.contracts.CommandDelegatable
import com.projectcitybuild.core.contracts.Commandable
import com.projectcitybuild.core.contracts.EnvironmentProvider
import com.projectcitybuild.entities.LogLevel
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.lang.ref.WeakReference

internal class SpigotCommandDelegate constructor(
        val plugin: WeakReference<JavaPlugin>,
        val environment: EnvironmentProvider
    ): CommandDelegatable {

    override fun register(command: Commandable) {
        command.inject(environment)

        command.aliases.plus(command.label).forEach { alias ->
            plugin.get()?.getCommand(alias)?.setExecutor { sender, _, _, args ->
                try {
                    command.execute(
                            sender = sender,
                            args = args ?: arrayOf(),
                            isConsole = sender !is Player
                    )
                } catch (error: Exception) {
                    sender.sendMessage("An error occurred performing a command")
                    error.localizedMessage.let { message -> environment.log(LogLevel.FATAL, message) }
                    true
                }
            }
        }
    }
}