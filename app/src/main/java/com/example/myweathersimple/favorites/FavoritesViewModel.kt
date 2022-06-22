package com.example.myweathersimple.favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweathersimple.FavoriteLocation
import com.example.myweathersimple.FavoritesModel
import com.example.myweathersimple.SharedPref

class FavoritesViewModel : ViewModel() {
    var favoritesList: MutableLiveData<MutableList<FavoriteLocation>> = MutableLiveData()

    init {
        favoritesList.value = SharedPref.instance.favoriteLocations?.locationsList
    }

    fun deleteFavoriteById(id: Int) {
        val newFav = favoritesList.value
        newFav?.let {
            it.removeAt(id)
            SharedPref.instance.favoriteLocations = FavoritesModel(it)
            favoritesList.value = it
        }
    }
}