package com.example.trackcurrencyapp.di

import android.content.Context
import com.example.trackcurrencyapp.data.database.favourite.FavouriteCurrencyDao
import com.example.trackcurrencyapp.data.database.favourite.FavouriteCurrencyDataBase
import com.example.trackcurrencyapp.data.database.sort.SortCurrencyDao
import com.example.trackcurrencyapp.data.database.sort.SortCurrencyDataBase
import com.example.trackcurrencyapp.domain.mapper.FavouriteCurrencyMapper
import com.example.trackcurrencyapp.domain.mapper.PopularCurrencyMapper
import com.example.trackcurrencyapp.domain.mapper.SortCurrencyMapper
import com.example.trackcurrencyapp.presentation.utility.CurrencyFormat
import com.example.trackcurrencyapp.presentation.utility.DefaultCurrency
import com.example.trackcurrencyapp.presentation.utility.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

   @Provides
   fun defaultCurrency() = DefaultCurrency()

   @Provides
   fun providePopularCurrencyMapper() = PopularCurrencyMapper()

   @Provides
   fun provideFavouriteCurrencyMapper() = FavouriteCurrencyMapper()

   @Provides
   fun provideSortMapper() = SortCurrencyMapper()

   @Provides
   fun provideCurrencyFormat() = CurrencyFormat

   @Provides
   @Singleton
   fun provideFavouriteCurrencyDatabase(@ApplicationContext context: Context): FavouriteCurrencyDataBase {
      return FavouriteCurrencyDataBase.getDatabase(context)
   }

   @Provides
   @Singleton
   fun provideFavouriteCurrencyDao(dataBase: FavouriteCurrencyDataBase): FavouriteCurrencyDao {
      return dataBase.favouriteCurrencyDao()
   }

   @Provides
   @Singleton
   fun provideSortCurrencyDatabase(@ApplicationContext context: Context): SortCurrencyDataBase {
      return SortCurrencyDataBase.getDatabase(context)
   }

   @Provides
   @Singleton
   fun provideSortCurrencyDao(dataBase: SortCurrencyDataBase): SortCurrencyDao {
      return dataBase.sortCurrencyDao()
   }

   @Provides
   @Singleton
   fun provideDispatchers(): DispatchersProvider = object : DispatchersProvider {
      override val main: CoroutineDispatcher
         get() = Dispatchers.Main

      override val io: CoroutineDispatcher
         get() = Dispatchers.IO

      override val default: CoroutineDispatcher
         get() = Dispatchers.Default

      override val unconfined: CoroutineDispatcher
         get() = Dispatchers.Unconfined
   }
}