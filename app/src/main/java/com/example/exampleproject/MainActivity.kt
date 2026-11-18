package com.example.exampleproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.exampleproject.data.model.Task
import com.example.exampleproject.ui.screens.AddTaskScreen
import com.example.exampleproject.ui.screens.EditTaskScreen
import com.example.exampleproject.ui.screens.HomeScreen
import com.example.exampleproject.ui.theme.ExampleProjectTheme
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ExampleProjectTheme {

                var showAddTaskScreen by remember {
                    mutableStateOf(false)
                }

                var selectedTask by remember {
                    mutableStateOf<Task?>(null)
                }

                val tasks = remember {
                    mutableStateListOf(
                        Task(
                            id = 1,
                            title = "Complete Android Assignment",
                            description = "Finish the mobile development assignment",
                            category = "Assignment"
                        ),
                        Task(
                            id = 2,
                            title = "Study Database Management",
                            description = "Review database lecture notes",
                            category = "Study"
                        ),
                        Task(
                            id = 3,
                            title = "Review Kotlin Notes",
                            description = "Study Kotlin programming concepts",
                            category = "Study",
                            isCompleted = true
                        )
                    )
                }

                if (selectedTask != null) {

                    val taskToEdit = selectedTask!!

                    EditTaskScreen(
                        task = taskToEdit,

                        onUpdate = { title, description, category, dueDate ->

                            val parsedDueDate = parseDueDate(dueDate)

                            val index = tasks.indexOfFirst {
                                it.id == taskToEdit.id
                            }

                            if (index != -1) {
                                tasks[index] = tasks[index].copy(
                                    title = title,
                                    description = description,
                                    category = category,
                                    dueDate = parsedDueDate
                                )
                            }

                            selectedTask = null
                        },

                        onCancel = {
                            selectedTask = null
                        }
                    )

                } else if (showAddTaskScreen) {

                    AddTaskScreen(
                        onSave = { title, description, category, dueDate ->

                            val newTask = Task(
                                id = (tasks.maxOfOrNull { it.id } ?: 0) + 1,
                                title = title,
                                description = description,
                                category = category,
                                dueDate = parseDueDate(dueDate)
                            )

                            tasks.add(newTask)

                            showAddTaskScreen = false
                        },

                        onCancel = {
                            showAddTaskScreen = false
                        }
                    )

                } else {

                    HomeScreen(
                        tasks = tasks,

                        onAddTaskClick = {
                            showAddTaskScreen = true
                        },

                        onEditTaskClick = { task ->
                            selectedTask = task
                        },

                        onDeleteTaskClick = { taskId ->
                            tasks.removeAll { it.id == taskId }
                        },

                        onCompletionChange = { taskId, isCompleted ->

                            val index = tasks.indexOfFirst {
                                it.id == taskId
                            }

                            if (index != -1) {
                                tasks[index] = tasks[index].copy(
                                    isCompleted = isCompleted
                                )
                            }
                        }
                    )
                }
            }
        }
    }

    private fun parseDueDate(date: String): Long? {
        if (date.isBlank()) return null

        return runCatching {
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.getDefault()
            ).apply {
                isLenient = false
            }.parse(date)?.time
        }.getOrNull()
    }
}
