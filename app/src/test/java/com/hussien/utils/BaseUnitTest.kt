package com.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vezeeta.patients.app.utils.MainCoroutineScopeRule
import org.junit.Rule


open class BaseUnitTest {

    @get:Rule
    var mainCoroutinBaseViewModelClasseScopeRule = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
}