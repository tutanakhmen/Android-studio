package com.dicoding.UMKMConnect.di

import com.dicoding.UMKMConnect.data.UmkmRepository

object Injection {
    fun provideRepository(): UmkmRepository{
        return UmkmRepository.getInstance()
    }
}