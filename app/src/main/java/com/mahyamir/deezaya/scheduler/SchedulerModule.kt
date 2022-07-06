package com.mahyamir.deezaya.scheduler

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

@Module
@InstallIn(SingletonComponent::class)
class SchedulerModule {

    @Provides
    fun provideSchedulers(): SchedulerProvider =
        SchedulerProvider(
            ioScheduler = Schedulers.io(),
            mainScheduler = AndroidSchedulers.mainThread()
        )
}
