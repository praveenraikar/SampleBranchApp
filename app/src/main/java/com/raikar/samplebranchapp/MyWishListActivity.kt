package com.raikar.samplebranchapp

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.branch.referral.Branch
import io.branch.referral.Branch.BranchReferralInitListener


class MyWishListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_wishlist)

        intent?.getBundleExtra(BranchDeepLinkActivity.WISHLIST)?.let {
            val orderId = it.getString(BranchDeepLinkActivity.ID)?:"NA"
            val orderDescription = it.getString(BranchDeepLinkActivity.DESCRIPTION)?:"NA"

            findViewById<TextView>(R.id.tv_order_id).text = orderId
            findViewById<TextView>(R.id.tv_order_description).text = orderDescription
        }
    }
}