package com.example.trackcurrencyapp.di

import com.example.trackcurrencyapp.data.repository.FavouriteRepositoryImpl
import com.example.trackcurrencyapp.data.repository.IFavouriteRepository
import com.example.trackcurrencyapp.data.repository.IPopularRepository
import com.example.trackcurrencyapp.data.repository.ISortRepository
import com.example.trackcurrencyapp.data.repository.PopularRepositoryImpl
import com.example.trackcurrencyapp.data.repository.SortRepositoryImpl
import com.example.trackcurrencyapp.domain.usecase.FavouriteUseCase
import com.example.trackcurrencyapp.domain.usecase.IFavouriteUseCase
import com.example.trackcurrencyapp.domain.usecase.IPopularUseCase
import com.example.trackcurrencyapp.domain.usecase.ISortUseCase
import com.example.trackcurrencyapp.domain.usecase.PopularUseCase
import com.example.trackcurrencyapp.domain.usecase.SortUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface IAppModule {
   @Binds
   @Singleton
   fun providePopularRepository(repository: PopularRepositoryImpl): IPopularRepository

   @Binds
   @Singleton
   fun providePopularUseCase(useCase: PopularUseCase): IPopularUseCase

   @Binds
   @Singleton
   fun provideFavouriteRepository(repository: FavouriteRepositoryImpl): IFavouriteRepository

   @Binds
   @Singleton
   fun provideFavouriteUseCase(useCase: FavouriteUseCase): IFavouriteUseCase

   @Binds
   @Singleton
   fun provideSortRepository(repository: SortRepositoryImpl): ISortRepository

   @Binds
   @Singleton
   fun provideSortUseCase(useCase: SortUseCase): ISortUseCase


}