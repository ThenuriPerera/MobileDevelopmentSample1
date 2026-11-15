package com.example.exampleproject.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exampleproject.data.model.Task

@Composable
fun HomeScreen() {

    // Sample task data
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

    // Calculate task counts automatically
    val totalTasks = tasks.size
    val completedTasks = tasks.count { it.isCompleted }
    val pendingTasks = totalTasks - completedTasks

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            Text(
                text = "Student Task Manager",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Welcome back!",
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Stay organized and achieve your goals.",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Task Overview",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                SummaryCard(
                    title = "Total",
                    count = totalTasks,
                    modifier = Modifier.weight(1f)
                )

                SummaryCard(
                    title = "Pending",
                    count = pendingTasks,
                    modifier = Modifier.weight(1f)
                )

                SummaryCard(
                    title = "Completed",
                    count = completedTasks,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "My Tasks",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Display all tasks
            tasks.forEachIndexed { index, task ->

                TaskItem(
                    task = task,
                    onCompletionChange = { isCompleted ->

                        // Update the selected task
                        tasks[index] = task.copy(
                            isCompleted = isCompleted
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "More features will be added as we build the application.",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SummaryCard(
    title: String,
    count: Int,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Text(
                text = count.toString(),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = title,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onCompletionChange: (Boolean) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = task.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = task.category,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Checkbox(
                    checked = task.isCompleted,
                    onCheckedChange = onCompletionChange
                )
            }

            Text(
                text = if (task.isCompleted) "Completed" else "Pending",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
