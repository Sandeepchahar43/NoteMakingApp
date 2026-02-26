package com.example.notemakingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notemakingapp.HomeFragment
import com.example.notemakingapp.databinding.NoteItemBinding
import com.example.notemakingapp.model.Note
import android.graphics.Color

import androidx.navigation.findNavController
import com.example.notemakingapp.HomeFragmentDirections
import kotlin.random.Random


class NoteAdapter:RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(){


    class NoteViewHolder(val binding: NoteItemBinding):
        RecyclerView.ViewHolder(binding.root)

    private val differCallback =object: DiffUtil.ItemCallback<Note>(){


        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.noteBody == newItem.noteBody &&
                    oldItem.noteTitle == newItem.noteTitle

        }
    }
    val diff = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
         val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context),
             parent,
             false
             )
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
         val currentNote = diff.currentList[position]
         holder.binding.tvNoteBody.text = currentNote.noteBody
         holder.binding.tvNoteTitle.text = currentNote.noteTitle

        // this will generate a random color
        val random = Random.Default
        val color = Color.argb(
            255,  // Alpha (visibility)(red,blue,green)
            Random.nextInt(256),
            Random.nextInt(256),
            Random.nextInt(256)
        )
         holder.binding.ibColor.setBackgroundColor(color)

        holder.itemView.setOnClickListener{
            val direction = HomeFragmentDirections.actionHomeFragmentToUpdateNoteFragment(currentNote)
            it.findNavController().navigate(direction)

        }
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }
}