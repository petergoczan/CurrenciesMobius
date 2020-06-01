package com.petergoczan.currenciesmobius.scheduler

import com.petergoczan.currenciesmobius.di.ApplicationScope
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ApplicationScope
class SchedulersProviderImpl @Inject constructor() : SchedulersProvider {

    override fun computation(): Scheduler = Schedulers.computation()

    override fun io(): Scheduler = Schedulers.io()

    override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}