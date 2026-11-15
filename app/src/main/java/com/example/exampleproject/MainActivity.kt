package com.example.exampleproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.exampleproject.data.model.Task
import com.example.exampleproject.ui.screens.AddTaskScreen
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

                if (showAddTaskScreen) {

                    AddTaskScreen(
                        onSave = { title, description, category, dueDate ->

                            val parsedDueDate = if (dueDate.isBlank()) {
                                null
                            } else {
                                runCatching {
                                    SimpleDateFormat(
                                        "yyyy-MM-dd",
                                        Locale.getDefault()
                                    ).parse(dueDate)?.time
                                }.getOrNull()
                            }

                            val newTask = Task(
                                id = (tasks.maxOfOrNull { it.id } ?: 0) + 1,
                                title = title,
                                description = description,
                                category = category,
                                dueDate = parsedDueDate
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
}

