package com.projectcitybuild.spigot.modules.bans.actions

import com.projectcitybuild.core.contracts.Environment
import com.projectcitybuild.entities.models.MojangPlayer

class GetMojangPlayerAction(private val environment: Environment) {
    sealed class Result {
        class SUCCESS(val player: MojangPlayer) : Result()
        class FAILED(val reason: Failure) : Result()
    }

    enum class Failure {
        DESERIALIZE_FAILED,
    }

    fun execute(playerName: String, at: Long? = null) : Result {
        val mojangApi = environment.mojangClient.mojangApi

        val request = mojangApi.getMojangPlayer(playerName, timestamp = at)
        val response = request.execute()
        val player = response.body()

        if (player == null) {
            return Result.FAILED(reason = Failure.DESERIALIZE_FAILED)
        }
        return Result.SUCCESS(player)
    }
}
