package com.zbistapp.weatherappforavito.di

import android.app.Application
import android.content.Context
import com.zbistapp.weatherappforavito.BuildConfig
import com.zbistapp.weatherappforavito.domain.ILocationRepo
import com.zbistapp.weatherappforavito.data.LocationRepoImpl
import com.zbistapp.weatherappforavito.data.NetworkRepoImpl
import com.zbistapp.weatherappforavito.domain.INetworkRepo
import com.zbistapp.weatherappforavito.domain.network.WeatherApi
import com.zbistapp.weatherappforavito.ui.main.MainFragment
import com.zbistapp.weatherappforavito.ui.main.MainViewModelFactory
import dagger.Component
import dagger.Module
import dagger.Provides
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    fun provideMainViewModelFactory(locationRepo: ILocationRepo, networkRepo: INetworkRepo) =
        MainViewModelFactory(locationRepo, networkRepo)
}

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideConverterFactory(): Converter.Factory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideRetrofit(
        converterFactory: Converter.Factory,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(converterFactory)
        .build()

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)

    @Provides
    @Singleton
    fun provideNetworkRepo(weatherApi: WeatherApi): INetworkRepo = NetworkRepoImpl(weatherApi)
}

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        LocationModule::class,
        ViewModelFactoryModule::class,
        NetworkModule::class
    ]
)
interface AppComponent {

    fun inject(mainFragment: MainFragment)
}