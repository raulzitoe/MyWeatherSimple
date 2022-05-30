package com.example.myweathersimple.home

import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.myweathersimple.AutocompleteModel
import com.example.myweathersimple.Coordinates
import com.example.myweathersimple.LocationSearchAdapter
import com.example.myweathersimple.R
import com.example.myweathersimple.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myActivity = (activity as AppCompatActivity)

        myActivity.setSupportActionBar(binding.homeTopAppBar)

        binding.homeTopAppBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.search ->{
                    binding.searchLocationLayout.isVisible = !binding.searchLocationLayout.isVisible
                    true
                }
                else -> false
            }
        }

        binding.recyclerviewSearch.adapter = LocationSearchAdapter(listener = object : LocationSearchAdapter.ItemListener{
            override fun itemClick(item: AutocompleteModel) {
                binding.recyclerviewSearch.isVisible = false
                binding.searchLocationLayout.isVisible = false
                with(viewModel){
                    coordinates.value?.latitude = item.latitude.toDouble()
                    coordinates.value?.longitude = item.longitude.toDouble()
                    requestWeatherAndForecastData()
                    city.value = item.city_name
                    state.value = item.state_name
                    country.value = item.country_name
                }
            }
        })

        viewModel.coordinates.value = Coordinates(43.6553, -79.4578)
        viewModel.requestWeatherAndForecastData()
        viewModel.forecastData.observe(viewLifecycleOwner) { forecastModel ->
            binding.recyclerviewForecast.adapter = HomeAdapter(forecastModel.list)
        }

        binding.searchLocation.setOnEditorActionListener { _, actionId, _ ->
            val query = binding.searchLocation.text.toString()
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.requestLocationData(query)
                binding.recyclerviewSearch.isVisible = true
                false
            } else {
                false
            }
        }

        viewModel.locationData.observe(viewLifecycleOwner) {
            (binding.recyclerviewSearch.adapter as LocationSearchAdapter).items = it
            (binding.recyclerviewSearch.adapter as LocationSearchAdapter).notifyDataSetChanged()
        }
    }
}