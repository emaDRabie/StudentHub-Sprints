package org.sprints.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username:String = "admin",
    val password:String = "admin",
)
