package com.example.notemakingapp.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface Doa {

     @Insert
     suspend fun insertNotes(note: Note)

      @Update
      suspend fun updateNotes(note: Note)

      @Delete
      suspend fun deleteNotes(note: Note)

      @Query("SELECT * FROM notes ORDER BY id DESC")
       fun getAllNotes():LiveData<List<Note>>

       @Query("SELECT *FROM notes WHERE noteTitle LIKE:query OR noteBody LIKE:query")
       fun searchNote(query: String?):LiveData<List<Note>>

       

}