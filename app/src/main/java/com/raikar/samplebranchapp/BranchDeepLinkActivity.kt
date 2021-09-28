package com.raikar.samplebranchapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import io.branch.indexing.BranchUniversalObject
import io.branch.referral.Branch
import io.branch.referral.Branch.BranchReferralInitListener
import io.branch.referral.BranchError
import io.branch.referral.SharingHelper
import io.branch.referral.util.ContentMetadata
import io.branch.referral.util.LinkProperties
import io.branch.referral.util.ShareSheetStyle
import org.json.JSONObject

class BranchDeepLinkActivity : AppCompatActivity() {
    companion object {
        const val ID = "ID"
        const val DESCRIPTION = "DESCRIPTION"
        const val ORDER = "ORDER"
        const val WISHLIST = "WISHLIST"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.bt_my_order).setOnClickListener {
            startActivity(Intent(this, MyOrdersActivity::class.java))
        }

        findViewById<Button>(R.id.bt_my_wishlist).setOnClickListener {
            startActivity(Intent(this, MyWishListActivity::class.java))
        }

        findViewById<Button>(R.id.bt_refer_a_friend).setOnClickListener {
            referAFriend()
        }
    }

    private fun referAFriend() {
        val shareSheetStyle =
            ShareSheetStyle(this, "Check this out!", "This stuff is awesome: ")
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.EMAIL)
                .setAsFullWidthStyle(true)
                .setSharingTitle("Share With")

        val branchUniversalObject =
            BranchUniversalObject()
                .setCanonicalIdentifier("ReferAFriend") // The canonical URL for SEO purposes (optional)
                .setTitle("Refer A Friend")
                .setContentDescription("Click the link and enjoy the App")
                .setContentImageUrl("https://picsum.photos/id/237/200/300") // You use this to specify whether this content can be discovered publicly - default is public
                .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC) // Here is where you can add cyustom keys/values to the deep link data
                .setContentMetadata(
                    ContentMetadata()
                        .addCustomMetadata("referral_code", "qwerty123")
                )


        val linkProperties: LinkProperties = LinkProperties()
            .setChannel("General")
            .setFeature("ReferAFriend")
            .addControlParameter("\$desktop_url", "https://yml.co/")
            .addControlParameter("\$android_url", "https://yml.co/")

        branchUniversalObject.generateShortUrl(
            this,
            linkProperties,
            Branch.BranchLinkCreateListener { url, error ->
                url?.let {
//                Toast.makeText(this, "Generated link is $it", Toast.LENGTH_LONG).show()
                }
            })
        branchUniversalObject.showShareSheet(
            this,
            linkProperties,
            shareSheetStyle,
            object : Branch.ExtendedBranchLinkShareListener {
                override fun onShareLinkDialogLaunched() {
                }

                override fun onShareLinkDialogDismissed() {
                }

                override fun onLinkShareResponse(
                    sharedLink: String?,
                    sharedChannel: String?,
                    error: BranchError?
                ) {
                }

                override fun onChannelSelected(
                    channelName: String?,
                    buo: BranchUniversalObject?,
                    linkProperties: LinkProperties?
                ): Boolean {
                    return false
                }

                override fun onChannelSelected(channelName: String?) {
                }

            })

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
                val isBranchLink = linkProperties.get("+clicked_branch_link") as Boolean
                if (isBranchLink) {
                    handleBranchLinks(linkProperties)
                }
            }
        }

    private fun handleBranchLinks(linkProperties: JSONObject) {
        when (linkProperties.get("page_redirection") as String) {
            "MyOrder" -> {
                val orderId = linkProperties.get("order_id") as String
                val orderDescription = linkProperties.get("order_description") as String
                val intent = Intent(this, MyOrdersActivity::class.java)
                val bundle = Bundle().apply {
                    putString(ID, orderId)
                    putString(DESCRIPTION, orderDescription)
                }
                intent.putExtra(ORDER, bundle)
                startActivity(intent)
            }
            "MyWishlist" -> {
                val orderId = linkProperties.get("wishlist_id") as String
                val orderDescription = linkProperties.get("wishlist_description") as String
                val intent = Intent(this, MyWishListActivity::class.java)
                val bundle = Bundle().apply {
                    putString(ID, orderId)
                    putString(DESCRIPTION, orderDescription)
                }
                intent.putExtra(WISHLIST, bundle)
                startActivity(intent)
            }
        }
    }
}