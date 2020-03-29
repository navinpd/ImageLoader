package com.big.imageloader.di.module

import android.content.Context
import com.big.imageloader.di.ActivityContext
import com.big.imageloader.ui.MainActivity
import dagger.Module
import dagger.Provides

@Module
public class ActivityModule(private val mainActivity: MainActivity) {


    @Provides
    @ActivityContext
    fun provideContext(): Context = mainActivity


}