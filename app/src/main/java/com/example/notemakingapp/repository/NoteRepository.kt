package com.example.notemakingapp.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.notemakingapp.model.Doa
import com.example.notemakingapp.model.Note


class NoteRepository(private val doa: Doa) {

    suspend fun insertNote(note: Note){
        return doa.insertNotes(note)
    }
    suspend fun updateNotes(note: Note){
        return doa.updateNotes(note)
    }

    suspend fun deleteNotes(note: Note){
        return doa.deleteNotes(note)
    }

    fun getAllNotes(): LiveData<List<Note>> {
        return doa.getAllNotes()
    }

    fun searchNotes( query:String?):LiveData<List<Note>>{
        return doa.searchNote(query)
    }


}