package org.sprints.domain.models

data class Student(
    val id: Int,
    val name: String,
    val grade: String,
    val status: String,
    val gpa: Double?,
    val notes : String?,
)
