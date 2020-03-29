package com.big.imageloader.di.module

import android.content.Context
import com.big.imageloader.di.ActivityContext
import com.big.imageloader.ui.dashboard.DashboardFragment
import dagger.Module
import dagger.Provides

@Module
class DashboardFragmentModule(private val fragment: DashboardFragment) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = fragment.context!!
}
