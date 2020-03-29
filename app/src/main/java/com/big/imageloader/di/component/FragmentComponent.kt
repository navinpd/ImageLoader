package com.big.imageloader.di

import com.big.imageloader.di.component.ApplicationComponent
import com.big.imageloader.di.module.DashboardFragmentModule
import com.big.imageloader.di.module.HomeFragmentModule
import com.big.imageloader.ui.dashboard.DashboardFragment
import com.big.imageloader.ui.home.HomeFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [HomeFragmentModule::class, DashboardFragmentModule::class])
interface FragmentComponent {

    fun inject(fragment: HomeFragment)

    fun inject(fragment: DashboardFragment)
}
