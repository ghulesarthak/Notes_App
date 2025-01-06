package com.example.todousing_firebase.UIDesigning

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.todousing_firebase.Database.Note
import com.example.todousing_firebase.Database.TodoViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Update(key: String?, viewmodel: TodoViewmodel){

  //  val viewModel = TodoViewmodel()


    var sub by remember { mutableStateOf("") }
    var des by remember { mutableStateOf("") }
    val context= LocalContext.current
    val noteList by viewmodel.noteList.collectAsState()

    val note = noteList.find { it.key == key }

    LaunchedEffect(note) {
        sub = note?.subject ?: ""
        des = note?.description ?: ""
    }



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Update Note",
                    style = MaterialTheme.typography.headlineLarge) },
                modifier = Modifier.background(MaterialTheme.colorScheme.onBackground)
            )
        },
        content = {
                paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Input Fields
                Column(modifier = Modifier.fillMaxWidth()) {


                    TextField(
                        value = sub,
                        onValueChange = { sub = it },
                        label = { Text("Subject") },
                        placeholder = { Text("Enter subject") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        singleLine = true,
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedIndicatorColor = Color.Gray
                        )
//
                    )

                    TextField(
                        value = des,
                        onValueChange = { des = it },
                        label = { Text("Description") },
                        placeholder = { Text("Enter detailed description") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(bottom = 16.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                            unfocusedIndicatorColor = Color.Gray
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
                        viewmodel.update(key!!,noteData,
                            onSuccess = {Toast.makeText(context,"Note Updated Successfully", Toast.LENGTH_SHORT).show()},
                            onFailure = {Toast.makeText(context,"Failed to update note", Toast.LENGTH_SHORT).show()})
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Cyan,
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "Save Note",
                        style = TextStyle(Color.Yellow),
                        color = Color.White)
                }

            }
        }


    )


}