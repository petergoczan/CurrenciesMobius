package com.petergoczan.currenciesmobius

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.petergoczan.currenciesmobius.view.MainPageView
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var view: MainPageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view.bind(root)
    }
}