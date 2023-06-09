package com.example.demobuoi7.room

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.withTransaction
import com.example.demobuoi7.room.daos.UserAndNotes
import com.example.demobuoi7.room.entities.NoteEntity
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlin.random.Random

class RoomViewModel(application: Application) : AndroidViewModel(application) {
  val userAndNotesLiveData: LiveData<UserAndNotes?> = AppDatabase
    .getInstance(application)
    .noteDao()
    .observeAllByUserId(userId = 1)

  init {
    viewModelScope.coroutineContext.job.invokeOnCompletion {
      Log.d("RoomViewModel", it.toString())
    }
  }

  override fun onCleared() {
    super.onCleared()
    Log.d("RoomViewModel", "onCleared")
  }

  fun addNewData() {
    val appDatabase = AppDatabase
      .getInstance(getApplication())

    viewModelScope.launch {
      appDatabase.withTransaction {
        val notes = List(10) {
          val id = Random.nextInt()
          NoteEntity(
            id = id,
            title = "Title $id",
            userId = 1
          )
        }

        AppDatabase
          .getInstance(getApplication())
          .noteDao()
          .insertMany(notes)
      }

      Toast.makeText(getApplication(), "Success", Toast.LENGTH_SHORT).show()
    }
  }
}
