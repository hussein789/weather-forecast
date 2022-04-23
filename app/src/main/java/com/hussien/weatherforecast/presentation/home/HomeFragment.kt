package com.hussien.weatherforecast.presentation.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.hussien.weatherforecast.R
import com.hussien.weatherforecast.data.model.Day
import com.hussien.weatherforecast.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding:FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

    @Inject lateinit var factory: HomeViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this,factory)[HomeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.weatherUiState.observe(viewLifecycleOwner){ uiStateModel ->
            uiStateModel?.let { updateView(it) }

        }
    }

    private fun updateView(model: WeatherUiState) {
        handleLoading(model.isLoading)
        handleError(model.errorMessage)
        handleDays(model.days)
    }

    private fun handleDays(days: List<Day?>?) {
        Log.d("hussein","you received theses days $days")
    }

    private fun handleError(errorMessage: Int?) {
        errorMessage?.let {
            Toast.makeText(requireContext(),getString(it),Toast.LENGTH_LONG).show()
        }
    }

    private fun handleLoading(loading: Boolean) {
        binding.progress.visibility = if(loading) View.VISIBLE else View.GONE
    }

}