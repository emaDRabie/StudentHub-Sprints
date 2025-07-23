package org.sprints.domain.usecases

import org.sprints.data.repository.UsersRepository
import org.sprints.domain.models.User

class SignupUseCase(private val repo: UsersRepository) {
    fun signup(username: String, password: String): Boolean {
        return repo.signup(username, password)
    }
}