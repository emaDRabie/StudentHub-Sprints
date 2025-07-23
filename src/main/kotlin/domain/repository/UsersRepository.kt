package org.sprints.domain.repository

import org.sprints.domain.models.User

interface UsersRepository {
    fun login(username: String, password: String) : Boolean
}