package com.projectcitybuild.spigot

import com.projectcitybuild.core.contracts.Environment
import com.projectcitybuild.core.contracts.Listenable
import org.bukkit.event.Event
import org.bukkit.plugin.java.JavaPlugin
import java.lang.ref.WeakReference

class ListenerDelegate constructor(val plugin: WeakReference<JavaPlugin>, val environment: Environment) {
    fun register(listener: Listenable<Event>) {
        listener.inject(environment)
        plugin.get()?.server?.pluginManager?.registerEvents(listener, plugin.get())
    }
}