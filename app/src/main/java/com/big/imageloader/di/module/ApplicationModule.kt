package com.big.imageloader.di.module

import android.content.Context
import android.content.SharedPreferences
import com.big.imageloader.BuildConfig
import com.big.imageloader.MyApplication
import com.big.imageloader.data.remote.NetworkService
import com.big.imageloader.data.remote.Networking
import com.big.imageloader.di.ApplicationContext
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: MyApplication) {


    @Provides
    @ApplicationContext
    fun provideContext(): Context = application.applicationContext

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()


    @Provides
    @Singleton
    fun providePicasso() : Picasso = Picasso.get()

    @Provides
    @Singleton
    fun provideNetworking(): NetworkService {
        return Networking.createNetworking(
            BuildConfig.API_Key,
            BuildConfig.API_Host,
            BuildConfig.BASE_URL,
            application.cacheDir,
            10 * 1024 * 1024
        )
    }

    @Provides
    @Singleton
    fun provideSharedPreference(): SharedPreferences =
        application.getSharedPreferences("Local-Shared-Pref", 0)

}
