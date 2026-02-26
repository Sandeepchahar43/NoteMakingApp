package com.example.notemakingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.notemakingapp.adapter.NoteAdapter
import com.example.notemakingapp.databinding.FragmentHomeBinding
import com.example.notemakingapp.databinding.FragmentNewNoteBinding
import com.example.notemakingapp.model.Note
import com.example.notemakingapp.viewmodel.NoteViewModel


class NewNoteFragment : Fragment() {
    private lateinit var binding: FragmentNewNoteBinding
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        notesViewModel = ViewModelProvider(requireActivity()).get(NoteViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_new_note,
            container,
            false
        )
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.save_icon_menu,menu)
        val menuSave = menu.findItem(R.id.save_Icon)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.save_Icon) {
            saveNote()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun saveNote() {
        val title = binding.etNoteTitle.text.toString()
        val body = binding.etNoteBody.text.toString()
        if (title.isNotEmpty()&&body.isNotEmpty()) {

            val note = Note(0, title, body)

            notesViewModel.addNote(note)
            Toast.makeText(requireContext(), "Note is added Successfully", Toast.LENGTH_SHORT).show()

            findNavController().popBackStack()
        }else{
            Toast.makeText(requireContext(), "Please Enter the NoteTitle", Toast.LENGTH_SHORT).show()
        }
    }


}