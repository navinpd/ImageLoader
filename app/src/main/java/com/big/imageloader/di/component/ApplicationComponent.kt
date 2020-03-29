package com.big.imageloader.di.component

import android.content.Context
import android.content.SharedPreferences
import com.big.imageloader.MyApplication
import com.big.imageloader.data.remote.NetworkService
import com.big.imageloader.di.ApplicationContext
import com.big.imageloader.di.module.ApplicationModule
import com.squareup.picasso.Picasso
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(application: MyApplication)

    @ApplicationContext
    fun getContext() : Context

    fun getCompositeDisposable(): CompositeDisposable

    fun getNetworkService() : NetworkService

    fun getPrefStore() : SharedPreferences

    fun getPicasso() : Picasso

}