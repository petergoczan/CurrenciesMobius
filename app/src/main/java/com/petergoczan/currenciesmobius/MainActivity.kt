package com.petergoczan.currenciesmobius

import android.os.Bundle
import com.petergoczan.currenciesmobius.data.resolveDefaultModel
import com.petergoczan.currenciesmobius.data.saveModel
import com.petergoczan.currenciesmobius.presentation.MainPagePresenter
import com.petergoczan.currenciesmobius.view.MainPageView
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var view: MainPageView
    @Inject
    lateinit var presenter: MainPagePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view.bind(root)
        initPresenter(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveModel(presenter.getModel(), outState)
    }

    private fun initPresenter(savedInstanceState: Bundle?) {
        presenter.createController(resolveDefaultModel(savedInstanceState))
        presenter.onViewAvailable(view)
    }
}