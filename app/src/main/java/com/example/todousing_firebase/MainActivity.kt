package com.example.todousing_firebase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todousing_firebase.Database.TodoViewmodel
import com.example.todousing_firebase.UIDesigning.AllNotes
import com.example.todousing_firebase.UIDesigning.InsertDetails
import com.example.todousing_firebase.UIDesigning.Route
import com.example.todousing_firebase.UIDesigning.Update
import com.example.todousing_firebase.ui.theme.TodoUsing_FirebaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoUsing_FirebaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    Navigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Navigation(modifier: Modifier){

    val navcontroller = rememberNavController()

    val viewmodel : TodoViewmodel  = viewModel()

    NavHost(navController = navcontroller, startDestination = Route.Read,
        builder={

            composable(Route.Insert){
                InsertDetails(navcontroller,viewmodel)
            }

            composable(Route.Read){
                AllNotes(navcontroller , viewmodel)
            }

            composable("Update/{key}"){
                backStackEntry ->
                val key = backStackEntry.arguments?.getString("key") ?:return@composable

                Update(key,viewmodel)
            }


        })

}