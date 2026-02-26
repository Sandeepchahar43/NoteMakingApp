package com.example.notemakingapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notemakingapp.model.Note
import com.example.notemakingapp.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(app:Application,private val  noteRepository: NoteRepository):AndroidViewModel(app){

    fun addNote(note: Note)= viewModelScope.launch {
        noteRepository.insertNote(note)
    }
     fun updateNotes(note: Note)= viewModelScope.launch {
         noteRepository.updateNotes(note)
     }
      fun deleteNotes(note: Note)= viewModelScope.launch {
          noteRepository.deleteNotes(note)
      }
     fun getAllNotes():LiveData<List<Note>>{
         return noteRepository.getAllNotes()
     }
      fun searchAllNotes(query:String?):LiveData<List<Note>>{
         return noteRepository.searchNotes(query)
      }

}