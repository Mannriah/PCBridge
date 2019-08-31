package com.projectcitybuild.spigot.modules.ranks.listeners

import com.projectcitybuild.core.contracts.EnvironmentProvider
import com.projectcitybuild.core.contracts.Listenable
import com.projectcitybuild.entities.models.ApiResponse
import com.projectcitybuild.entities.models.AuthPlayerGroups
import com.projectcitybuild.spigot.modules.ranks.RankMapper
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.player.PlayerLoginEvent
import retrofit2.Response
import java.util.*

class SyncRankLoginListener : Listenable<PlayerLoginEvent> {
    override var environment: EnvironmentProvider? = null

    @EventHandler(priority = EventPriority.HIGH)
    override fun observe(event: PlayerLoginEvent) {
        syncRankWithServer(event.player)
    }

    private fun syncRankWithServer(player: Player) {
        val environment = environment ?: throw Exception("EnvironmentProvider has already been deallocated")
        val permissions = environment.permissions ?: throw Exception("Permission plugin is null")

        getPlayerGroups(playerId = player.uniqueId) { result ->
            val json = result.body()
            if (json?.data == null) {
                return@getPlayerGroups
            }

            if (json?.error != null) {
                if (json.error.id != "account_not_linked") {
                    environment.sync {
                        player.sendMessage("Failed to sync rank: Trouble communicating with the authentication server...")
                    }
                }
                return@getPlayerGroups
            }

            environment.sync {
                // Remove all groups from the player before syncing
                permissions.getPlayerGroups(player).forEach { group ->
                    permissions.playerRemoveGroup(player, group)
                }

                val permissionGroups = RankMapper.mapGroupsToPermissionGroups(json.data.groups)
                permissionGroups.forEach { group ->
                    if (!permissions.playerInGroup(player, group)) {
                        permissions.playerAddGroup(null, player, group)
                    }
                }
            }
        }
    }

    private fun getPlayerGroups(playerId: UUID, completion: (Response<ApiResponse<AuthPlayerGroups>>) -> Unit) {
        val environment = environment ?: throw Exception("EnvironmentProvider is null")
        val authApi = environment.apiClient.authApi

        environment.async<Response<ApiResponse<AuthPlayerGroups>>> { resolve ->
            val request = authApi.getUserGroups(uuid = playerId.toString())
            val response = request.execute()

            resolve(response)
        }.startAndSubscribe(completion)
    }
}