package com.example.notemakingapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notemakingapp.adapter.NoteAdapter
import com.example.notemakingapp.databinding.FragmentHomeBinding
import com.example.notemakingapp.model.Note
import com.example.notemakingapp.viewmodel.NoteViewModel


class HomeFragment : Fragment() ,SearchView.OnQueryTextListener{


    private lateinit var binding: FragmentHomeBinding
    private lateinit var notesViewModel:NoteViewModel
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
            R.layout.fragment_home,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()

        binding.floatingActionButton.setOnClickListener{
             findNavController().navigate(R.id.action_homeFragment_to_newNoteFragment)
        }
    }

    private fun setUpRecyclerView() {
        noteAdapter = NoteAdapter()
        binding.recyclerview.apply {
            layoutManager =  StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            adapter = noteAdapter
        }

        notesViewModel.getAllNotes().observe(
            viewLifecycleOwner
        ) { note ->
            noteAdapter.diff.submitList(note)
            updateUI(note)
        }

    }

    private fun updateUI(note: List<Note>?) {
         if(note!!.isNotEmpty()){
             binding.cardView.visibility = View.GONE
             binding.recyclerview.visibility = View.VISIBLE
         }else{
             binding.cardView.visibility = View.VISIBLE
             binding.recyclerview.visibility = View.GONE
         }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.home_menu,menu)
        val MenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        MenuSearch.isSubmitButtonEnabled = false
        MenuSearch.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { searchNote(it) }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(newText != null){
            searchNote(newText)
        }
        return true
    }

    private fun searchNote(text:String){
        notesViewModel.searchAllNotes("%$text%")
            .observe(viewLifecycleOwner){ list ->
                noteAdapter.diff.submitList(list)
            }
    }




}