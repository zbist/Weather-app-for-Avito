package com.zbistapp.weatherappforavito.di

import android.app.Application
import android.content.Context
import com.zbistapp.weatherappforavito.location.ILocationRepo
import com.zbistapp.weatherappforavito.location.LocationRepoImpl
import com.zbistapp.weatherappforavito.ui.main.MainFragment
import com.zbistapp.weatherappforavito.ui.main.MainViewModelFactory
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApplicationModule(private val app: Application) {

    @Provides
    fun providesApplication(): Application = app

    @Provides
    fun providesContext(): Context = app
}

@Module
class LocationModule {

    @Provides
    @Singleton
    fun provideLocationRepository(context: Context): ILocationRepo =
        LocationRepoImpl(context)
}

@Module
class ViewModelFactoryModule {

    @Provides
    @Singleton
    fun provideMainViewModelFactory(locationRepo: ILocationRepo) =
        MainViewModelFactory(locationRepo)
}

@Singleton
@Component(modules = [ApplicationModule::class, LocationModule::class, ViewModelFactoryModule::class])
interface AppComponent {

    fun inject(mainFragment: MainFragment)
}