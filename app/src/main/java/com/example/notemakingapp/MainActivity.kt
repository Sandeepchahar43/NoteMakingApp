package com.example.notemakingapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.notemakingapp.databinding.ActivityMainBinding
import com.example.notemakingapp.model.NoteDatabase
import com.example.notemakingapp.repository.NoteRepository
import com.example.notemakingapp.viewmodel.NoteViewModel
import com.example.notemakingapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
     lateinit var viewModel: NoteViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setUpViewModel()

         binding = DataBindingUtil.setContentView(this,R.layout.activity_main)



    }

    private fun setUpViewModel() {
        val dao = NoteDatabase.getDatabase(this).getNoteDoa()
        val repository = NoteRepository(dao)
        val factory = ViewModelFactory(application,repository)
        viewModel = ViewModelProvider(this,factory).get(NoteViewModel::class.java)
    }
}