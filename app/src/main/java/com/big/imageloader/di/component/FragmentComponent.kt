package com.big.imageloader.di

import com.big.imageloader.di.component.ApplicationComponent
import com.big.imageloader.di.module.DashboardFragmentModule
import com.big.imageloader.di.module.HomeFragmentModule
import com.big.imageloader.ui.fragment.DashboardFragment
import com.big.imageloader.ui.fragment.HomeFragment
import dagger.Component

@FragmentScope
@Component(dependencies = [ApplicationComponent::class], modules = [HomeFragmentModule::class, DashboardFragmentModule::class])
interface FragmentComponent {

    fun inject(fragment: HomeFragment)

    fun inject(fragment: DashboardFragment)
}
