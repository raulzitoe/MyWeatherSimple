package com.example.myweathersimple.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myweathersimple.FavoritesModel
import com.example.myweathersimple.databinding.ItemFavoritesBinding

class FavoritesAdapter(val favoritesList: FavoritesModel) :
    RecyclerView.Adapter<FavoritesAdapter.FavoritesAdapterViewHolder>() {

    inner class FavoritesAdapterViewHolder(private val binding: ItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            with(binding) {
                locationName.text = favoritesList.locationsList[position].name
                latitude.text = favoritesList.locationsList[position].latitude.toString()
                longitude.text = favoritesList.locationsList[position].longitude.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesAdapterViewHolder {
        val binding =
            ItemFavoritesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoritesAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesAdapterViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return favoritesList.locationsList.size
    }
}