package com.hellomotem.horserace.di

import android.content.Context
import androidx.room.Room
import com.hellomotem.horserace.database.HorseRaceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideHorseRaceDatabase(
        @ApplicationContext context: Context
    ): HorseRaceDatabase = Room.databaseBuilder(
        context = context,
        klass = HorseRaceDatabase::class.java,
        name = "horse_race_database"
    ).build()
}