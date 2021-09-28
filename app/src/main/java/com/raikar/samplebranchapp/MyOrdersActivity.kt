package com.raikar.samplebranchapp

import android.content.Intent
import android.os.Bundle
import android.text.style.TtsSpan
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.raikar.samplebranchapp.BranchDeepLinkActivity.Companion.DESCRIPTION
import com.raikar.samplebranchapp.BranchDeepLinkActivity.Companion.ID
import com.raikar.samplebranchapp.BranchDeepLinkActivity.Companion.ORDER
import io.branch.referral.Branch
import io.branch.referral.Branch.BranchReferralInitListener


class MyOrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_orders)

        intent?.getBundleExtra(ORDER)?.let {
            val orderId = it.getString(ID)?:"NA"
            val orderDescription = it.getString(DESCRIPTION)?:"NA"

            findViewById<TextView>(R.id.tv_order_id).text = orderId
            findViewById<TextView>(R.id.tv_order_description).text = orderDescription
        }
    }
}