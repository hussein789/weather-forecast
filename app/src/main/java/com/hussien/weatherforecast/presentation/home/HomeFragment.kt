package com.hussien.weatherforecast.presentation.home

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.hussien.weatherforecast.data.model.Location
import com.hussien.weatherforecast.databinding.FragmentHomeBinding
import com.hussien.weatherforecast.domain.model.WeatherModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment(), LocationCallback {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: LocationAdapter

    @Inject
    lateinit var factory: HomeViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initClickListeners()
        observeViewModel()
    }

    private fun initClickListeners() {
        binding.searchIcon.setOnClickListener {
            showSearchView()
        }

        binding.homeContainer.setOnClickListener {
            hideSearchView()
        }

        binding.arrowContainer.setOnClickListener {
            hideSearchView()
        }

        binding.backIcon.setOnClickListener {
            hideSearchView()
        }

        binding.clearTextIcon.setOnClickListener {
            binding.searchLocationEt.setText("")
        }
    }

    private fun initViews() {
        initRecycler()
        initSearchView()
        initPullToRefresh()
    }

    private fun initPullToRefresh() {
        binding.refreshWeather.setOnRefreshListener {
            viewModel.onRefresh()
            binding.refreshWeather.isRefreshing = false
        }
    }

    private fun initSearchView() {
        binding.searchLocationEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                handleSearchedLocations(p0.toString())
            }

        })
    }

    private fun handleSearchedLocations(searchedCity: String) {
        binding.clearTextIcon.visibility = if (searchedCity.isEmpty()) View.GONE else View.VISIBLE
        if (!searchedCity.isNullOrEmpty()) {
            viewModel.onSearchLocationChanged(searchedCity)
        }
    }

    private fun initRecycler() {
        adapter = LocationAdapter()
        adapter.callback = this
        binding.locationRv.layoutManager = LinearLayoutManager(requireContext())
        binding.locationRv.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.weatherModelState.observe(viewLifecycleOwner) { model ->
            model?.let { handleDays(model) }
        }
        viewModel.loadingState.observe(viewLifecycleOwner){show ->
            show?.let { handleLoading(show) }
        }
        viewModel.errorState.observe(viewLifecycleOwner){resError ->
            resError?.let { handleError(resError) }
        }
        viewModel.searchLocationsState.observe(viewLifecycleOwner){locations ->
            locations?.let { handleLocations(locations as MutableList<Location>) }
        }
    }

    private fun handleLocations(searchLocations: MutableList<Location>) {
        binding.arrowContainer.visibility =
            if (searchLocations.isEmpty()) View.GONE else View.VISIBLE
        adapter.setData(searchLocations)
        adapter.notifyDataSetChanged()
    }

    private fun handleDays(weatherModel: WeatherModel?) {
        weatherModel?.let { model ->
            binding.apply {
                timeTv.text = model.localTime
                locationTv.text = model.cityName
                dateTv.text = model.localDate
                Glide.with(requireContext())
                    .load("https:" + model.mainIcon)
                    .into(mainWeatherIcon)
                degreeTv.text = model.mainDegree
                weatherDescriptionTv.text = model.description
                windSpeedTv.text = model.wind
                humiditySpeedTv.text = model.humidity

                Glide.with(requireContext())
                    .load("https:" + model.weatherDays[0].icon)
                    .into(currentWeather)
                currentMaxMinTv.text = model.weatherDays[0].minMaxDegree

                Glide.with(requireContext())
                    .load("https:" + model.weatherDays[1].icon)
                    .into(middleWeather)
                middleMaxMinTv.text = model.weatherDays[1].minMaxDegree

                Glide.with(requireContext())
                    .load("https:" + model.weatherDays[2].icon)
                    .into(lastWeather)
                lastMaxMinTv.text = model.weatherDays[2].minMaxDegree
                lastDayName.text = model.weatherDays[2].dayName

            }
        }
    }

    private fun handleError(errorMessage: Int?) {
        errorMessage?.let {
            Toast.makeText(requireContext(), getString(it), Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleLoading(loading: Boolean) {
        binding.progress.visibility = if (loading) View.VISIBLE else View.GONE
    }

    override fun onLocationClicked(location: Location) {
        viewModel.onLocationClicked(location)
        hideKeyboard()
        hideSearchView()
    }

    private fun hideSearchView() {
        binding.topView.isVisible = false
        binding.searchContainer.visibility = View.GONE
        binding.searchLocationEt.setText("")
        binding.arrowContainer.visibility = View.GONE
        adapter.setData(emptyList())
        adapter.notifyDataSetChanged()
    }

    private fun showSearchView() {
        binding.searchLocationEt.requestFocus()
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm!!.showSoftInput(binding.searchLocationEt, InputMethodManager.SHOW_IMPLICIT)
        binding.searchContainer.visibility = View.VISIBLE
        binding.topView.isVisible = true
    }

    private fun hideKeyboard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }


}