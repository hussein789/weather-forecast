package com.hussien.weatherforecast.presentation.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.hussien.weatherforecast.R
import com.hussien.weatherforecast.databinding.FragmentSplashBinding
import com.hussien.weatherforecast.domain.model.WeatherModel
import com.hussien.weatherforecast.presentation.home.HomeFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    lateinit var binding:FragmentSplashBinding
    lateinit var viewModel: SplashViewModel

    @Inject lateinit var factory: SplashViewModelFactory


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this,factory).get(SplashViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorState.observe(viewLifecycleOwner){showError->
            showError?.let { handleError(it) }
        }

        viewModel.weatherModelState.observe(viewLifecycleOwner){ model ->
            model?.let { navigateToHome(model) }
        }
    }

    private fun navigateToHome(model: WeatherModel) {
        val bundle = bundleOf(HomeFragment.WEATHER_DATA_KEY to model)
        findNavController().navigate(R.id.action_splashFragment_to_homeFragment,bundle)
    }

    private fun handleError(errorRes: Int) {
        Toast.makeText(requireContext(),getString(errorRes),Toast.LENGTH_SHORT).show()
    }

}