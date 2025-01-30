package com.example.todousing_firebase.UIDesigning

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.todousing_firebase.Database.Note
import com.example.todousing_firebase.Database.TodoViewmodel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Update(key: String?, viewmodel: TodoViewmodel) {
    var sub by remember { mutableStateOf("") }
    var des by remember { mutableStateOf("") }
    val context = LocalContext.current
    val noteList by viewmodel.noteList.collectAsState()

    val note = noteList.find { it.key == key }

    LaunchedEffect(note) {
        sub = note?.subject ?: ""
        des = note?.description ?: ""
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color(0xFF147982), Color(0xFF0D2A2F))
                        )
                    )
            ) {
                TopAppBar(
                    title = {
                        Text(
                            "Update Note",
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent // Ensure gradient shows
                    )
                )
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(listOf(Color(0xFF116268),
                        Color(0xFF000000))
                    ))
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Subject TextField
                    TextField(
                        value = sub,
                        onValueChange = { sub = it },
                        label = { Text("Subject") },
                        placeholder = { Text("Enter subject") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF3AA9B3), Color(0xFF75C4C4))
                                )
                            )
                            .padding(bottom = 16.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFFFFA000),
                            unfocusedBorderColor = Color.Gray,
                            //textColor = Color(0xFF795548)
                        ),
                    )

                    // Description TextField
                    TextField(
                        value = des,
                        onValueChange = { des = it },
                        label = { Text("Description") },
                        placeholder = { Text("Enter detailed description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF3AA9B3), Color(0xFF065A5A))
                                )
                            )
                            .height(200.dp)
                            .padding(bottom = 16.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFFFFA000),
                            unfocusedBorderColor = Color.Gray,
                            //textColor = Color(0xFF795548)
                        ),
                        maxLines = 7
                    )
                }

                // Save Button
                Button(
                    onClick = {
                        val noteData = Note(
                            subject = sub,
                            description = des
                        )
                        viewmodel.update(key!!, noteData,
                            onSuccess = {
                                Toast.makeText(context, "Note Updated Successfully", Toast.LENGTH_SHORT).show()
                            },
                            onFailure = {
                                Toast.makeText(context, "Failed to update note", Toast.LENGTH_SHORT).show()
                            })
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(Color(0xFF3AA9B3), Color(0xFF0B3D3D))
                            )
                        ),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues())
                {
                    Text(
                        text = "Save Note",
                        style = TextStyle(color = Color.White)
                    )
                }
            }
        }
    )
}
