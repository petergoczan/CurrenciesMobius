package com.petergoczan.currenciesmobius

import android.os.Bundle
import com.petergoczan.currenciesmobius.data.resolveDefaultModel
import com.petergoczan.currenciesmobius.data.saveModel
import com.petergoczan.currenciesmobius.mobius.CurrencyEvent
import com.petergoczan.currenciesmobius.mobius.CurrencyModel
import com.petergoczan.currenciesmobius.presentation.MainPagePresenter
import com.petergoczan.currenciesmobius.view.MainPageView
import com.spotify.mobius.MobiusLoop
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var view: MainPageView
    @Inject
    lateinit var presenter: MainPagePresenter

    private lateinit var controller: MobiusLoop.Controller<CurrencyModel, CurrencyEvent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view.bind(root)
        initMobiusController(savedInstanceState)
        initPresenter()
    }

    override fun onResume() {
        super.onResume()
        controller.start()
    }

    override fun onPause() {
        super.onPause()
        controller.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.disconnect()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveModel(controller.model, outState)
    }

    private fun initMobiusController(savedInstanceState: Bundle?) {
        controller = presenter.createController(resolveDefaultModel(savedInstanceState))
        controller.connect(presenter)
    }

    private fun initPresenter() {
        presenter.onViewAvailable(view)
        presenter.onModelUpdated(controller.model)
    }
}