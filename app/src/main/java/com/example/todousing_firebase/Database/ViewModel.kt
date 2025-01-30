package com.example.todousing_firebase.Database

import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

//class TodoViewmodel : ViewModel() {
//
//    private val database = FirebaseDatabase.getInstance().getReference("NOTES")
//
//
//    fun InsertNotes(noteData: Note, onSuccess: () -> Unit, onFailure: () -> Unit) {
//        val uniqueId = UUID.randomUUID().toString()
//
//        val noteWithKey = noteData.copy(key = uniqueId)
//
//        database.child(uniqueId).setValue(noteWithKey)
//            .addOnSuccessListener { onSuccess() }
//            .addOnFailureListener { onFailure() }
//    }
//
//
//
//    //Read Data
//    val AllNotes = MutableStateFlow<List<Note>>(emptyList())
//    val noteList : StateFlow<List<Note>> get() = AllNotes.asStateFlow()
//
//
//    fun GetNotes() {
//        database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val notes = snapshot.children.mapNotNull { dataSnapshot ->
//                    val note = dataSnapshot.getValue(Note::class.java)
//                    note?.copy(key = dataSnapshot.key ?: "") // Set the key from the database
//                }
//                AllNotes.value = notes
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                AllNotes.value = emptyList()
//            }
//        })
//    }
//
//
//
//    //Update Data
//
//    fun update(key: String,
//               noteData: Note,
//               onSuccess: () -> Unit,
//               onFailure: () -> Unit)
//    {
//        database.child(key).setValue(noteData)
//            .addOnSuccessListener { onSuccess() }
//            .addOnFailureListener { onFailure() }
//
//
//    }
//
//    //delete Note
//
//    fun deleteMember(key: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit)
//    {
//
//        database.child(key).removeValue()
//            .addOnSuccessListener {
//                GetNotes()
//                onSuccess() }
//            .addOnFailureListener { exception -> onFailure(exception) }
//    }
//
//}




class TodoViewmodel : ViewModel() {

    private val database = FirebaseDatabase.getInstance().getReference("NOTES")

    val AllNotes = MutableStateFlow<List<Note>>(emptyList())
    val noteList: StateFlow<List<Note>> get() = AllNotes.asStateFlow()

    fun InsertNotes(noteData: Note, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val uniqueId = UUID.randomUUID().toString()
        val noteWithKey = noteData.copy(key = uniqueId)

        database.child(uniqueId).setValue(noteWithKey)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }

    fun GetNotes() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val notes = snapshot.children.mapNotNull { dataSnapshot ->
                    val note = dataSnapshot.getValue(Note::class.java)
                    note?.copy(key = dataSnapshot.key ?: "") // Set the key from the database
                }
                AllNotes.value = notes
            }

            override fun onCancelled(error: DatabaseError) {
                AllNotes.value = emptyList()
            }
        })
    }

    fun update(
        key: String,
        noteData: Note,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        database.child(key).setValue(noteData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }

    fun deleteMember(key: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        database.child(key).removeValue()
            .addOnSuccessListener {
                GetNotes()
                onSuccess()
            }
            .addOnFailureListener { exception -> onFailure(exception) }
    }
}
