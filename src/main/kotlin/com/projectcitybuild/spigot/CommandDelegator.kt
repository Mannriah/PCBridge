package com.projectcitybuild.spigot

import com.projectcitybuild.core.protocols.Commandable
import com.projectcitybuild.core.protocols.Environment
import org.bukkit.plugin.java.JavaPlugin
import java.lang.ref.WeakReference

internal class CommandDelegator constructor(val plugin: WeakReference<JavaPlugin>,
                                            val environment: Environment) {

    fun register(command: Commandable) {
        command.inject(environment)

        command.aliases.plus(command.label).forEach { alias ->
            plugin.get()?.getCommand(alias)?.setExecutor { sender, cmd, label, args ->
                command.execute(sender = sender, command = cmd, label = label, args = args)
            }
        }
    }

}