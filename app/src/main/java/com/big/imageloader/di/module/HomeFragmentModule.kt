package com.big.imageloader.di.module

import android.content.Context
import com.big.imageloader.di.ActivityContext
import com.big.imageloader.ui.fragment.HomeFragment
import dagger.Module
import dagger.Provides

@Module
class HomeFragmentModule(private val fragment: HomeFragment) {

    @ActivityContext
    @Provides
    fun provideContext(): Context = fragment.context!!
}
