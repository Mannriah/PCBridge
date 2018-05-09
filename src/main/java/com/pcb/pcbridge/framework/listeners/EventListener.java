package com.pcb.pcbridge.framework.listeners;

import org.bukkit.plugin.Plugin;

import java.lang.ref.WeakReference;

public class EventListener {

    private final WeakReference<Plugin> plugin;

    public EventListener(Plugin plugin) {
        this.plugin = new WeakReference<>(plugin);
    }

    /**
     * Logic to run when the listener
     * is first allocated and registered
     */
    public void onRegister() {}

    /**
     * Logic to run when the listener
     * is unregistered and about to be
     * deallocated
     */
    public void onDeregister() {}

}
