package com.projectcitybuild.spigot

import com.projectcitybuild.core.contracts.EnvironmentProvider
import com.projectcitybuild.core.contracts.Listenable
import com.projectcitybuild.core.contracts.ListenerDelegatable
import com.projectcitybuild.entities.LogLevel
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import java.lang.ref.WeakReference

class SpigotListenerDelegate constructor(
        val plugin: WeakReference<JavaPlugin>,
        val environment: EnvironmentProvider
    ): ListenerDelegatable {

    override fun register(listener: Listenable<*>) {
        val plugin = plugin.get() ?: throw Exception("Failed to register listener: Plugin is deallocated")
        environment.log(LogLevel.VERBOSE, "Beginning listener registration...")

        listener.inject(environment)
        plugin.server?.pluginManager?.registerEvents(listener, plugin).let {
            environment.log(LogLevel.VERBOSE, "Registered listener: ${listener::class}")
        }
    }

    override fun unregisterAll() {
        HandlerList.unregisterAll()
        environment.log(LogLevel.VERBOSE, "Unregistered all listeners")
    }
}