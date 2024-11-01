package com.example.morningapp.di

import android.app.Activity.MODE_PRIVATE
import android.content.Context
import android.content.SharedPreferences
import com.example.morningapp.MyApplication
import com.example.morningapp.data.database.MorningDatabase
import com.example.morningapp.data.database.SettingsDao
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
    fun provideMorningDatabase(@ApplicationContext context: Context): MorningDatabase {
        return MorningDatabase.getInstance(context)
    }

    @Provides
    fun provideSettingsDao(morningDatabase: MorningDatabase): SettingsDao {
        return morningDatabase.settingsDao()
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("PREFS_NAME", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext context: Context): MyApplication {
        return context as MyApplication
    }
}