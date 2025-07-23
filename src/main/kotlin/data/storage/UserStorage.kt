package org.sprints.data.storage

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.sprints.domain.models.User
import java.io.File

object UserStorage {
    private val file = File("admins.json")

    fun saveUsers(users: List<User>) {
        val jsonString = Json.encodeToString(users)
        file.writeText(jsonString)
    }
    fun getUsers(): MutableList<User> {
        val jsonString = file.readText()
        return if (jsonString.isBlank()) mutableListOf<User>()
        else Json.decodeFromString(jsonString)
    }
}