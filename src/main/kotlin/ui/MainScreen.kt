package org.sprints.ui

import org.sprints.data.repository.StudentsRepository
import org.sprints.data.repository.UsersRepository
import org.sprints.domain.models.Student
import org.sprints.domain.usecases.FilterStudentsUseCase
import org.sprints.domain.usecases.GetAllStudentsUseCase
import org.sprints.domain.usecases.LoginUseCase
import org.sprints.domain.usecases.SignupUseCase

import org.sprints.domain.usecases.UpdateStudentInfoUseCase
import kotlin.math.max
import kotlin.math.min

class MainScreen {
    private var trials = 0
    private val getAllStudentsUseCase = GetAllStudentsUseCase(StudentsRepository())
    private val filterStudentsUseCase = FilterStudentsUseCase(StudentsRepository())
    private val loginCase = LoginUseCase(UsersRepository())
    private val updateStudentInfoUseCase = UpdateStudentInfoUseCase(StudentsRepository())
    private val signupCase = SignupUseCase(UsersRepository())
    fun home() {
        println("Welcome to Students Management System")
        var signed = false
        while(trials < 3){
            if (!signed && !login()){
                trials++
                println("Wrong username or password \n" +
                        "You have ${3 - trials} of your attempts!")
                println("1- Try again!    2- Sign Up")
                val choice = readlnOrNull()
                if(choice == "1"){
                    continue
                }else if(choice == "2"){
                    if(!signup()){
                        println("Sign Up failed")
                        println("Username and password must be unique")
                    }else{
                        signed = true
                    }
                }
                else{
                    println("Invalid choice")
                }
            }else{
                while (true){
                    println(
                        """
                    please select what are you want to do : 
                    0 - Add New Student
                    1 - Remove New Student
                    2 - Search Students
                    3 - Update Student Info
                    4 - Show all Students
                    5 - Logout
                    """.trimIndent()
                    )
                    val input = readlnOrNull()?.toIntOrNull()
                    val option = input?.let { Options.entries.getOrNull(it) }

                    when (option) {
                        Options.ADD_STUDENT -> addNewStudent()
                        Options.UPDATE_STUDENT -> updateStudents()
                        Options.REMOVE_STUDENT -> removeStudents()
                        Options.FILTER_STUDENT -> filterStudents()
                        Options.GET_STUDENTS -> getStudents()
                        Options.EXIT -> {
                            println("logout...")
                            break
                        }

                        else -> println("Invalid option. Try again.")

                    }
                }

            }

        }

    }

    private fun signup() : Boolean{
        println("please enter your username")
        val username = readlnOrNull()
        println("Enter your password")
        val password = readlnOrNull()
        if (username == null || password == null) return false
        if(signupCase.signup(username, password)){
            println("Signed up successfully")
            return true
        }else{
            return false
        }
    }

    private fun login(): Boolean {
        println("please enter your username")
        val username = readlnOrNull()
        println("Enter your password")
        val password = readlnOrNull()
        if (username == null || password == null) return false
        // handle use case here

        if (loginCase.login(username, password)) {
            println("Logged in as $username")
            return true
        } else {
            return false
        }
    }

    private fun addNewStudent(): Boolean {
        println("Enter student Name ▶ ")
        val name = readlnOrNull()
        println("Enter student grade ▶ ")
        val grade = readlnOrNull()
        println("Enter student GPA ▶ ")
        val gpa = readlnOrNull()
        println("Enter student Note ▶ ")
        val note = readlnOrNull()
        println("Enter student status ▶ ")
        val status = readlnOrNull()
        // handle add student use case here


        return false
    }

    private fun removeStudents(): Boolean {
        // show all students
        print("Enter student ID ▶ ")
        val id = readlnOrNull()?.toIntOrNull()

        return false
    }

    private fun filterStudents(): List<Student> {
        println(
            """
        Please select a filtering criteria ▶
        0 ▶ Filter by name
        1 ▶ Filter by grade
        2 ▶ Filter by status
        3 ▶ Filter by GPA: e.g. (3 ≤..≤ 4)
        4 ▶ Back ↺
        """.trimIndent()
        )
        val filterOp = readlnOrNull()?.toIntOrNull()
        if (filterOp == null) {
            println("Invalid input. Returning to home...")
            return emptyList()
        }

        val filteredStudents = when (filterOp) {
            0 -> {
                print("Enter student's name ▶ ")
                val name = readlnOrNull()
                filterStudentsUseCase.filterByName(name)
            }

            1 -> {
                println("Enter student's grade ▶ ")
                val grade = readlnOrNull()
                filterStudentsUseCase.filterByGrade(grade)
            }

            2 -> {
                println("Enter student's status ▶ ")
                val status = readlnOrNull()
                filterStudentsUseCase.filterByStatus(status)
            }

            3 -> {
                println("Enter minimum GPA: ")
                val minGPA = readlnOrNull()?.toDoubleOrNull()
                if (minGPA == null) {
                    println("Invalid minimum GPA. Returning to home...")
                    return emptyList()
                }
                println("Enter maximum GPA: ")
                val maxGPA = readlnOrNull()?.toDoubleOrNull()
                if (maxGPA == null) {
                    println("Invalid maximum GPA. Returning to home...")
                    return emptyList()
                }
                filterStudentsUseCase.filterByGPA(min(maxGPA, minGPA), max(maxGPA, minGPA))
            }

            4 -> {
                println("Returning to home ↺")
                return emptyList()
            }

            else -> {
                println("Invalid choice :(")
                return emptyList()
            }
        }

        if (filteredStudents.isEmpty()) {
            println("No students found matching the criteria.")
        } else {
            val idWidth = 5
            val nameWidth = 20
            val gradeWidth = 16
            val gpaWidth = 12
            val statusWidth = 14
            val notesWidth = 30
            println(
                String.format(
                    "%-${idWidth}s %-${nameWidth}s %-${gradeWidth}s %-${gpaWidth}s %-${statusWidth}s %-${notesWidth}s",
                    "ID", "Name", "Grade", "GPA", "Status", "Notes"
                )
            )
            println("-".repeat(idWidth + nameWidth + gradeWidth + gpaWidth + statusWidth + notesWidth + 5))

            filteredStudents.forEach {
                println(
                    String.format(
                        "%-${idWidth}d %-${nameWidth}s %-${gradeWidth}s %-${gpaWidth}.2f %-${statusWidth}s %-${notesWidth}s",
                        it.id, it.name, it.grade, it.gpa ?: 0.0, it.status, it.notes ?: ""
                    )
                )
            }
        }

        return filteredStudents
    }

    private fun getStudents() {
        val idWidth = 5
        val nameWidth = 20
        val gradeWidth = 16
        val gpaWidth = 12
        val statusWidth = 14
        val notesWidth = 30
        println(
            String.format(
                "%-${idWidth}s %-${nameWidth}s %-${gradeWidth}s %-${gpaWidth}s %-${statusWidth}s %-${notesWidth}s",
                "ID", "Name", "Grade", "GPA", "Status", "Notes"
            )
        )
        println("-".repeat(idWidth + nameWidth + gradeWidth + gpaWidth + statusWidth + notesWidth + 5))

        getAllStudentsUseCase.getAllStudents().forEach {
            println(
                String.format(
                    "%-${idWidth}d %-${nameWidth}s %-${gradeWidth}s %-${gpaWidth}.2f %-${statusWidth}s %-${notesWidth}s",
                    it.id, it.name, it.grade, it.gpa, it.status, it.notes ?: ""
                )
            )
        }
    }

    private fun updateStudents() {
        getStudents()
        println("Enter student ID ▶ ")
        val id = readlnOrNull()?.toIntOrNull()

        if (id == null) {
            println("Invalid ID")
            return
        }

        // Find the student to show their current info
        val studentToUpdate = StudentsRepository().findStudentById(id)
        if (studentToUpdate == null) {
            println("❌ Student with ID $id not found.")
            return
        }

        println("Updating student: ${studentToUpdate.name}.")

        print("Enter new name [${studentToUpdate.name}] ▶ ")
        val newName = readlnOrNull().takeIf { !it.isNullOrBlank() } ?: studentToUpdate.name

        print("Enter new grade [${studentToUpdate.grade}] ▶ ")
        val newGrade = readlnOrNull().takeIf { !it.isNullOrBlank() } ?: studentToUpdate.grade

        print("Enter new status [${studentToUpdate.status}] ▶ ")
        val newStatus = readlnOrNull().takeIf { !it.isNullOrBlank() } ?: studentToUpdate.status

        print("Enter new GPA [${studentToUpdate.gpa ?: "N/A"}] ▶ ")
        val newGpa = readlnOrNull()?.toDoubleOrNull() ?: studentToUpdate.gpa

        print("Enter new notes [${studentToUpdate.notes ?: "N/A"}] ▶ ")
        val newNotes = readlnOrNull().takeIf { !it.isNullOrBlank() } ?: studentToUpdate.notes

        val result = updateStudentInfoUseCase(
            id = id,
            newName = newName,
            newGrade = newGrade,
            newStatus = newStatus,
            newGpa = newGpa,
            newNotes = newNotes
        )

        result.onSuccess { updateStudents ->
            println("Student updated successfully!")
            println(updateStudents)
        }.onFailure { error ->
            println("Error updating student: ${error.message}")
        }
    }
}