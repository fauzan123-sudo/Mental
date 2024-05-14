package com.infinity.mental.ui.activity

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.infinity.mental.R
import com.infinity.mental.databinding.ActivityOnBoardingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity<ActivityOnBoardingBinding>() {
    private lateinit var navController: NavController
    override fun onViewCreated(binding: ActivityOnBoardingBinding) {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

//        binding.fragmentContainerView.setupWithNavController(navController)
    }
}