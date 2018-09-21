package com.projectcitybuild.spigot.extensions

import com.projectcitybuild.core.contracts.Environment
import com.projectcitybuild.core.extensions.toDashFormattedUUID
import com.projectcitybuild.spigot.modules.bans.actions.GetMojangPlayerAction
import org.bukkit.Server
import java.util.*

fun Server.getOfflinePlayer(name: String, environment : Environment) : UUID? {
    val player = this.getOnlinePlayer(name)
    if (player != null) {
        return player.uniqueId
    } else {
        val mojangPlayerAction = GetMojangPlayerAction(environment)
        val result = mojangPlayerAction.execute(playerName = name)
        if (result is GetMojangPlayerAction.Result.FAILED) {
            return null
        }
        if (result is GetMojangPlayerAction.Result.SUCCESS) {
            return UUID.fromString(result.player.uuid.toDashFormattedUUID())
        }
    }
}