package com.example.myweathersimple.favorites

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.myweathersimple.FavoritesModel
import com.example.myweathersimple.SharedPref
import com.example.myweathersimple.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {
    private lateinit var binding: FragmentFavoritesBinding
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerFavorites.adapter = viewModel.favoritesList.value?.let {
            FavoritesAdapter(
                FavoritesModel(it),
                listener = object : FavoritesAdapter.FavoritesItemListener {
                    override fun itemClick(id: Int) {
                        val action =
                            FavoritesFragmentDirections.actionFavoritesFragmentToHomeFragment2()
                        action.id = id
                        Navigation.findNavController(view).navigate(action)
                    }

                    override fun deleteItem(id: Int) {
                        viewModel.deleteFavoriteById(id)
                    }
                })
        }

        viewModel.favoritesList.observe(viewLifecycleOwner) {
            viewModel.favoritesList.value?.let {
                (binding.recyclerFavorites.adapter as FavoritesAdapter).favoritesList =
                    FavoritesModel(it)
                (binding.recyclerFavorites.adapter as FavoritesAdapter).notifyDataSetChanged()
            }
        }
    }
}