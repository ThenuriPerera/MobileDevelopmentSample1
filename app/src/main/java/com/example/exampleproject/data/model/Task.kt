package com.example.exampleproject.data.model

data class Task(
    val id: Int = 0,
    val title: String,
    val description: String = "",
    val category: String = "General",
    val dueDate: Long? = null,
    val isCompleted: Boolean = false
)