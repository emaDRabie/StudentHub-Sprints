package org.sprints.ui

import jdk.jfr.*
import org.sprints.data.repository.StudentsRepository
import org.sprints.data.repository.UsersRepository
import org.sprints.domain.models.Student
import org.sprints.domain.usecases.GetAllStudentsUseCase
import org.sprints.domain.usecases.LoginUseCase
import org.sprints.domain.usecases.SignupUseCase


class MainScreen {
    private var trials = 0
    private val getAllStudentsUseCase = GetAllStudentsUseCase(StudentsRepository())
    private val loginCase = LoginUseCase(UsersRepository())
    private val signupCase = SignupUseCase(UsersRepository())
    fun home() {
        println("Welcome to Students Management System")
        while(trials < 3){
            if (!login()){
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
                    }
                }
                else{
                    println("Invalid choice")
                }
            }else{
                println(
                    """
                    please select what are you want to do : 
                    0 - Add New Student
                    1 - Remove New Student
                    2 - Search Students
                    3 - Update Student Info
                    4 - Show all Students
                    5 - Exit
                    """.trimIndent()
                )
                val input = readlnOrNull()?.toIntOrNull()
                val option = input?.let { Options.entries.getOrNull(it) }

                when (option) {
                    Options.ADD_STUDENT -> addNewStudent()
                    Options.UPDATE_STUDENT -> updateStudents()
                    Options.REMOVE_STUDENT -> removeStudents()
                    Options.FIND_STUDENT -> filterStudents()
                    Options.GET_STUDENTS -> getStudents()
                    Options.EXIT -> {
                        println("Exiting...")
                        return
                    }

                    else -> println("Invalid option. Try again.")

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

        if(loginCase.login(username, password)){
            println("Logged in as $username")
            return true
        }else{
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
        println("Enter student Name ▶ ")
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
            please select what are you want to do : 
            0 - filter by name
            1 - filter by grade
            2 - filter by status            
            3 - Back
        """.trimIndent()
        )
        val filterOp: Int = readlnOrNull()!!.toInt()

        when (filterOp) {
            0 -> {
                println("Enter student's name: ")
                val name = readlnOrNull()
                // handle filter use case here
            }

            1 -> {
                println("Enter student's grade: ")
                val grade = readlnOrNull()
                // handle filter use case here
            }

            2 -> {
                println("Enter student's status: ")
                val status = readlnOrNull()
                // handle filter use case here
            }
        }

        return listOf()
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

    }

}