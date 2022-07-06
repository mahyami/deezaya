package com.mahyamir.deezaya.scheduler

import io.reactivex.rxjava3.core.Scheduler

data class SchedulerProvider(
    val ioScheduler: Scheduler,
    val mainScheduler: Scheduler
)