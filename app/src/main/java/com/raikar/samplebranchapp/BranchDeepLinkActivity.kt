package com.raikar.samplebranchapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.branch.referral.Branch
import io.branch.referral.Branch.BranchReferralInitListener


class BranchDeepLinkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        Branch.sessionBuilder(this).withCallback(branchReferralInitListener)
            .withData(if (intent != null) intent.data else null).init()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        // if activity is in foreground (or in backstack but partially visible) launching the same
        // activity will skip onStart, handle this case with reInitSession
        Branch.sessionBuilder(this).withCallback(branchReferralInitListener).reInit()
    }

    private val branchReferralInitListener =
        BranchReferralInitListener { linkProperties, error ->
            if (linkProperties?.get("+clicked_branch_link") is Boolean) {
                val isBranchLink = linkProperties?.get("+clicked_branch_link") as Boolean
                if (isBranchLink) {
                    //custom logic
                }
                // do stuff with deep link data (nav to page, display content, etc)
            }
        }
}