package com.petergoczan.currenciesmobius

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.petergoczan.currenciesmobius.data.resolveDefaultModel
import com.petergoczan.currenciesmobius.data.saveModel
import com.petergoczan.currenciesmobius.view.MainPageView
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.Update
import com.spotify.mobius.android.AndroidLogger
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.ObservableTransformer
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var view: MainPageView

    private lateinit var controller: MobiusLoop.Controller<CurrencyModel, CurrencyEvent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view.bind(root)
        controller = createController(
            createEffectHandlers(),
            resolveDefaultModel(savedInstanceState)
        )
        //TODO connect controller
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

    private fun createController(
        effectHandlers: ObservableTransformer<CurrencyEffect, CurrencyEvent>,
        defaultModel: CurrencyModel
    ): MobiusLoop.Controller<CurrencyModel, CurrencyEvent> {
        return MobiusAndroid.controller(
            createLoop(effectHandlers), defaultModel
        )
    }

    private fun createLoop(effectHandlers: ObservableTransformer<CurrencyEffect, CurrencyEvent>): MobiusLoop.Factory<CurrencyModel, CurrencyEvent, CurrencyEffect> {
        return RxMobius.loop(Update<CurrencyModel, CurrencyEvent, CurrencyEffect> { model: CurrencyModel, event: CurrencyEvent ->
            update(
                model,
                event
            )
        }, effectHandlers)
            .logger(AndroidLogger.tag("MainActivity loop"))
    }
}