package org.sprints.domain.usecases

import org.sprints.data.repository.UsersRepository

class LoginUseCase(private val repo: UsersRepository) {
    fun login(username: String, password: String): Boolean {
        return repo.login(username, password)
    }
}