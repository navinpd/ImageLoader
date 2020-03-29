package com.big.imageloader.di.component

import com.big.imageloader.di.ActivityScope
import com.big.imageloader.di.module.ActivityModule
import com.big.imageloader.ui.MainActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)


}