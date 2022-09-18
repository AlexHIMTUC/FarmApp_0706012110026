package com.training.farmapp_0706012110026.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.training.farmapp_0706012110026.Interface.CardClick
import com.training.farmapp_0706012110026.Model.Animal
import com.training.farmapp_0706012110026.R
import com.training.farmapp_0706012110026.databinding.AnimalCardBinding

class AnimalListAdapter(val listAnimal: ArrayList<Animal>, val cardClick: CardClick):
RecyclerView.Adapter<AnimalListAdapter.viewHolder>() {

    class viewHolder (itemView: View, cardClick: CardClick): RecyclerView.ViewHolder(itemView) {
        //Bind
        val binding = AnimalCardBinding.bind(itemView)

        fun setData(data: Animal, cardClick: CardClick){
            binding.animalListName.text = data.nama
            binding.animalListAge.text = "Usia : " + data.age.toString() + " tahun"
            binding.animalListType.text = data.jenis
            if(data.imageUri?.isNotEmpty() == true)
                binding.imageUri.setImageURI(Uri.parse(data.imageUri))
            binding.editAnimal.setOnClickListener {
                cardClick.onCardClick("edit", adapterPosition)
            }
            binding.deleteAnimal.setOnClickListener {
                cardClick.onCardClick("delete", adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.animal_card, parent, false)
        return viewHolder(view, cardClick)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(listAnimal[position], cardClick)
    }

    override fun getItemCount(): Int {
        return listAnimal.size
    }

}