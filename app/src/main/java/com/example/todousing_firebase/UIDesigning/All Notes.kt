package com.example.todousing_firebase.UIDesigning

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
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
    var noteToDelete by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewmodel.GetNotes()
    }

    Box(modifier = Modifier.fillMaxSize()
        .background(Brush.verticalGradient(listOf(Color(0xFF116268),
            Color(0xFF000000))
        ))) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                //.background(Color(0xFFF5F5F5)) // Light grey background
                , // Space for the FAB
                 verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "All Notes",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.padding(top = 45.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                itemsIndexed(notes) { index, note ->
                    // Dynamic colors for each card based on index
                    val cardColors = getDynamicColors(index)

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .shadow(elevation = 16.dp, shape = RoundedCornerShape(16.dp))
                            .clickable {
                                // Navigate to the Update screen with the note's key
                                navController.navigate("Update/${note.key}")
                            },
                        shape = RoundedCornerShape(16.dp),
                    ) {
                        Row(
                            modifier = Modifier
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            cardColors.backgroundColor,
                                            Color(0xFF7AC7FF)
                                        )
                                    )
                                )
                                .fillMaxSize()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Column for subject and description
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Subject: ${note.subject}",
                                    fontSize = 20.sp,
                                    color = cardColors.textColor,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = """Description: 
                    |${note.description}""".trimMargin(),
                                    fontSize = 16.sp,
                                    color = cardColors.textColor.copy(alpha = 0.8f),
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                            }

                            // Row for action icons (Edit and Delete)
                            Row(
                                modifier = Modifier.padding(start = 16.dp),
                                horizontalArrangement = Arrangement.End
                            ) {

                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    tint = Color.Red,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .clickable {
                                            noteToDelete = note.key
                                            showDialog = true
                                        }
                                        .padding(4.dp)
                                )
                            }
                        }
                    }

                }
            }
        }

        // Floating Action Button (FAB)
        FloatingActionButton(
            onClick = { navController.navigate(Route.Insert) },
            containerColor = Color(0xFF4CA2AC), // Blue color
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(32.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note", tint = Color.White
            )
        }

        // AlertDialog for deletion confirmation
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Delete Note", fontWeight = FontWeight.Bold) },
                text = { Text(text = "Are you sure you want to delete this note?") },
                confirmButton = {
                    TextButton(onClick = {
                        viewmodel.deleteMember(
                            noteToDelete,
                            onSuccess = {
                                Toast.makeText(context, "Note Deleted Successfully", Toast.LENGTH_SHORT).show()
                            },
                            onFailure = {
                                Toast.makeText(context, "Failed to delete note", Toast.LENGTH_SHORT).show()
                            }
                        )
                        showDialog = false
                    }) {
                        Text("Delete", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}

// Function to generate dynamic colors based on the index
fun getDynamicColors(index: Int): CardColors {
    val colors = listOf(
        CardColors(
            backgroundColor = Color(0xFFE3F2FD), // Light Blue
            textColor = Color(0xFF0D47A1), // Dark Blue
            iconColor = Color(0xFF1565C0) // Medium Blue
        ),
        CardColors(
            backgroundColor = Color(0xFFFFF9C4), // Light Yellow
            textColor = Color(0xFFFFA000), // Amber
            iconColor = Color(0xFFFFC107) // Bright Amber
        ),
        CardColors(
            backgroundColor = Color(0xFFFFEBEE), // Light Red
            textColor = Color(0xFFD32F2F), // Dark Red
            iconColor = Color(0xFFC62828) // Medium Red
        ),
        CardColors(
            backgroundColor = Color(0xFFE8F5E9), // Light Green
            textColor = Color(0xFF388E3C), // Dark Green
            iconColor = Color(0xFF4CAF50) // Medium Green
        )
    )
    return colors[index % colors.size]
}

// Data class for dynamic card colors
data class CardColors(
    val backgroundColor: Color,
    val textColor: Color,
    val iconColor: Color
)





