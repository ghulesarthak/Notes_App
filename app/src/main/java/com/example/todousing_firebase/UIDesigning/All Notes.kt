package com.example.todousing_firebase.UIDesigning

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todousing_firebase.Database.TodoViewmodel


@Composable
fun AllNotes(navController: NavController, viewmodel: TodoViewmodel) {
    val notes by viewmodel.noteList.collectAsState()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var noteTodelete by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewmodel.GetNotes()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)), // Light grey background for the entire screen
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "All Notes",
            color = Color.Black,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(top = 45.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(16.dp)
        ) {
            items(notes) { note ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .shadow(elevation = 10.dp, shape = RoundedCornerShape(16.dp))
                        ,
                    shape = RoundedCornerShape(16.dp) // Rounded corners for the card
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFE3F2FD), // Light Blue
                                        Color(0xFFFFF9C4) // Light Yellow
                                    )
                                )
                            )
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Column for subject and description
                        Column(
                            modifier = Modifier.weight(1f) // Take up remaining space
                        ) {
                            Text(
                                text = "Subject: ${note.subject}",
                                fontSize = 20.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Text(
                                text = """Description: 
                                    |${note.description}""".trimMargin(),
                                fontSize = 16.sp,
                                color = Color.Black.copy(alpha = 0.8f), // Slightly dimmed text color for description
                                modifier = Modifier.padding(bottom = 4.dp)
                            )
                        }

                        // Row for action icons (Edit and Delete)
                        Row(
                            modifier = Modifier
                                .padding(start = 16.dp),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                tint = Color(0xFF1976D2), // Blue for Edit
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable { navController.navigate("Update/${note.key}") }
                                    .padding(4.dp)
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.Red,
                                modifier = Modifier
                                    .size(30.dp)
                                    .clickable {
                                        noteTodelete = note.key
                                        showDialog = true // Show dialog when delete icon is clicked
                                    }
                                    .padding(4.dp)
                            )
                        }
                    }
                }
            }
        }

        // AlertDialog for confirmation
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false }, // Close dialog on outside click
                title = { Text(text = "Delete Note", fontWeight = FontWeight.Bold) },
                text = { Text(text = "Are you sure you want to delete this note?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewmodel.deleteMember(
                            noteTodelete,
                            onSuccess = {
                                Toast.makeText(context, "Note Deleted Successfully", Toast.LENGTH_SHORT).show()
                            },
                            onFailure = {
                                Toast.makeText(context, "Failed to delete note", Toast.LENGTH_SHORT).show()
                            }
                        )
                        showDialog = false // Close dialog after deletion
                    }) {
                        Text("Delete", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false // Close dialog without deleting
                    }) {
                        Text("Cancel")
                    }
                },
//                backgroundColor = Color(0xFFE3F2FD), // Light blue background for dialog
//                contentColor = Color.Black
            )
        }
    }
}




