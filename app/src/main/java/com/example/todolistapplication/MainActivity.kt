package com.example.todolistapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolistapplication.ui.theme.ToDoListApplicationTheme
import java.text.SimpleDateFormat
import java.util.Date

/**
 * The To Do List App
 * By: Ryan Meziane
 * Assignment 2 Application Development
 *
 * WBS(LOE):
 * 1. Create a style for the app (Estimation: 2 hours Actual: 1 hour)
 * 2. Logic for adding and removing tasks (Estimation: 2 hours Actual: 2 hours)
 * 3. Display the list of tasks (Estimation: 30min Actual: 1 hour)
 *
 * Total Time: 4 hours
 * Issues Encountered: Couldnt figure out how to make the list scrollable and Preventing the application from crashing when
 * the device is rotated. For some odd reason the app only crashed when I rotated the device with information stored in the list (*FIXED*).
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ToDoListApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Calling the main app method
                    ToDoListApp()
                }
            }
        }
    }
}
/**
 * The main app4
 */
@Composable
fun ToDoListApp() {
    // The list of tasks
    val taskList = rememberMutableStateListOf<String>()
    // Display the header and the input box
    DisplayHeader()
    InputBox(taskList)
}

/**
 * Display the header of the app
 */
@Composable
fun DisplayHeader(){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(10.dp)
    ) {
        Text(
            text = "To Do List",
            fontSize = 25.sp,
            lineHeight = 100.sp,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Display the input box along with a button to store the value in the task list
 * Displays the list of tasks
 * @param taskList the list of tasks to display
 */
@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputBox(taskList: SnapshotStateList<String>) {
    // A name state to store the name of the task
    val name = remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(50.dp)
    ) {
        Row(
            modifier = Modifier
        ) {
            TextField(
                // The value of the text field
                value = name.value,
                onValueChange = { name.value = it },
                modifier = Modifier
                    .weight(1f)
                    .size(50.dp)
                    .height(100.dp)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp)
                .padding(bottom = 50.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    // Get the current date and stores it in a formatted string
                    val dateFormat = SimpleDateFormat("MM/dd/yy")
                    val formattedDate = dateFormat.format(Date())
                    // Limits the length of the task name to 30 characters so it doesnt create a new line
                    if(name.value == "" || name.value.length > 30)
                        return@Button
                    taskList.add(name.value + "\n" + formattedDate)
                    name.value = ""
                },
                modifier = Modifier
                    .height(35.dp)
                    .fillMaxWidth(),
                shape  = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 5.dp,
                    bottomEnd = 5.dp
                )
            ) {
                Text(text = "Add Task")
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            // Created a surface to aid in the creation of the rounded corners since I couldnt find a way to do it with the LazyColumn
            Surface(
                shape = RoundedCornerShape(topEnd = 0.dp, topStart = 8.dp, bottomEnd = 8.dp, bottomStart = 8.dp),
                modifier = Modifier

                    .fillMaxWidth()
                    .padding(top = 95.dp)
                    .background(color = Color.Transparent),
            ) {
                // Displays the list of tasks and makes it scrollable
                // NOTE: I USED CHATGPT TO FIND USE OF THE LAZYCOLUMN PROMPT: How can I make a list scrollable in Jetpack Compose Kotlin?
                LazyColumn(
                    modifier = Modifier
                        .background(color = Color.DarkGray),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // For each task in the list call the DisplayTaskItem method
                    items(taskList) { task ->
                        DisplayTaskItem(task, taskList)
                    }
                }
                // END OF CHATGPT CODE PROVIDED
            }

        }
    }
}

/**
 * Display the list of tasks along with a button to remove them
 * @param taskList the list of tasks to display
 */
@Composable
fun DisplayTaskItem(task: String, taskList: SnapshotStateList<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = task,
            fontSize = 25.sp,
            lineHeight = 25.sp,
            textAlign = TextAlign.Left,
            color = Color.White
        )
        Image(
            painter = painterResource(id = R.drawable.yellow),
            contentDescription = "",
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.Top)
        )

        // Removes the task from the list
        Button(
            onClick = { taskList.remove(task) },
            colors = ButtonDefaults.buttonColors(Color.Red),
            modifier = Modifier.size(20.dp),
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomStart = 4.dp,
                bottomEnd = 0.dp
            )
        ){}
    }
    // Creates a divider between each task
    Divider(color = Color.Gray, thickness = 1.dp)
}