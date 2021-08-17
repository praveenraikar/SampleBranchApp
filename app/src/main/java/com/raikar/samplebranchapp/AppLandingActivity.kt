package com.raikar.samplebranchapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.branch.referral.Branch
import io.branch.referral.Branch.BranchReferralInitListener


class AppLandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_landing)
    }
}