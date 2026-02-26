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
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notemakingapp.databinding.FragmentNewNoteBinding
import com.example.notemakingapp.databinding.FragmentUpdateNoteBinding
import com.example.notemakingapp.model.Note
import com.example.notemakingapp.viewmodel.NoteViewModel


class updateNoteFragment : Fragment() {
    private lateinit var binding: FragmentUpdateNoteBinding
    private lateinit var notesViewModel: NoteViewModel
    private lateinit var currentNote: Note
    //since update fragment contain argument in nav_graph
    val args: updateNoteFragmentArgs by navArgs()

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
            R.layout.fragment_update_note,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentNote = args.note!!// data come here

        binding.etNotetitleUpdate.setText(currentNote.noteTitle)
        binding.etNotenoteUpdate.setText(currentNote.noteBody)
        binding.floatingActionButton2.setOnClickListener{
            updateNotes()

        }
    }

    private fun updateNotes() {

             var body = binding.etNotenoteUpdate.text.toString()
            var title = binding.etNotetitleUpdate.text.toString()
             if(title.isNotEmpty()&& body.isNotEmpty()){

                 var note = Note(currentNote.id,title,body)

                 notesViewModel.updateNotes(note)
                 findNavController().popBackStack()
             }else{
                 Toast.makeText(requireContext(), "Please enter title & body", Toast.LENGTH_SHORT).show()
             }


    }
// this triggered when click on delete icon in toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == R.id.menu_update_note){
            deleteNote()

        }
          return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {

        AlertDialog.Builder(requireContext())
            .setTitle("Delete Note")
            .setMessage("Do you want to delete this note?")
            .setPositiveButton("Delete") { _, _ ->
                notesViewModel.deleteNotes(currentNote)
                findNavController().popBackStack()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        menu.clear()
        inflater.inflate(R.menu.menu_update_note,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


}