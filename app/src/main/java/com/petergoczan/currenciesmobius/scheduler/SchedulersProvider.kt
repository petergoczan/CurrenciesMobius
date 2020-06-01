package com.petergoczan.currenciesmobius.scheduler

import io.reactivex.Scheduler

interface SchedulersProvider {

    fun computation(): Scheduler

    fun io(): Scheduler

    fun ui(): Scheduler
}