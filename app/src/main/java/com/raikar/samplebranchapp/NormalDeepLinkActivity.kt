package com.raikar.samplebranchapp

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class NormalDeepLinkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_normal_deep_link)

        val intent = intent
        val data: Uri? = intent.data
    }
}