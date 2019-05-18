package com.example.kanyequote

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import io.branch.referral.Branch
import io.branch.referral.BranchError
import io.branch.referral.SharingHelper
import io.branch.referral.util.LinkProperties
import io.branch.referral.util.ShareSheetStyle
import khttp.get
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.doAsync
import io.branch.indexing.BranchUniversalObject




class HomeActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val textView = findViewById<TextView>(R.id.kanyeQuoteTextView)
        val refreshButton = findViewById<Button>(R.id.refreshButton)
        val shareButton = findViewById<Button>(R.id.shareButton)




        if (intent.hasExtra("quote")) {
            val bd = intent.extras

            val quote = bd?.getString("quote")?.toString()
            if (quote.isNullOrBlank()){
                doAsync {
                    val test = get("https://api.kanye.rest/").jsonObject.get("quote")
                    Log.e("error------", test.toString())
                    textView.text = test.toString().toUpperCase()
                }
            }else{
                textView.text = quote.toUpperCase()
            }
        }

        refreshButton.setOnClickListener {
            refreshOnclick()
        }

        shareButton.setOnClickListener{
            shareOnClick()
        }

    }



    private fun refreshOnclick(){
        doAsync{
            val apiQuote = get("https://api.kanye.rest/").jsonObject.get("quote")
            Log.e("error------", apiQuote.toString())
            kanyeQuoteTextView.text = apiQuote.toString().toUpperCase()
        }
    }

    private fun shareOnClick() {

        val buo = BranchUniversalObject()
            // This is where you define the open graph structure and how the object will appear on Facebook or in a deepview
            .setTitle("My Favorite Kanye Quote")
            .setContentDescription(kanyeQuoteTextView.text.toString())
            .setContentImageUrl("https://timedotcom.files.wordpress.com/2000/04/kanye-west-time-100-2015-titans.jpg?quality=85")
            // You use this to specify whether this content can be discovered publicly - default is public
            .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)


        var lp = LinkProperties()
            .setChannel("facebook")
            .setFeature("sharing")
            .setCampaign("Get Kanye West Famous")
            .addControlParameter("desktop_url", "http://www.kanyewest.com/")
            .addControlParameter("quote", kanyeQuoteTextView.text.toString())

        val ss = ShareSheetStyle(this@HomeActivity, "[URGENT] Action Needed", "Peep this crazy thing kanye said")
            .setCopyUrlStyle(resources.getDrawable(android.R.drawable.ic_menu_send), "Copy", "Added to clipboard")
            .setMoreOptionStyle(resources.getDrawable(android.R.drawable.ic_menu_search), "Show more")
            .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
            .addPreferredSharingOption(SharingHelper.SHARE_WITH.EMAIL)
            .addPreferredSharingOption(SharingHelper.SHARE_WITH.MESSAGE)
            .addPreferredSharingOption(SharingHelper.SHARE_WITH.HANGOUT)
            .setAsFullWidthStyle(true)
            .setSharingTitle("Share With")

        buo.showShareSheet(this, lp, ss, object : Branch.BranchLinkShareListener {
            override fun onShareLinkDialogLaunched() {}
            override fun onShareLinkDialogDismissed() {}
            override fun onLinkShareResponse(sharedLink: String?, sharedChannel: String?, error: BranchError?) {}
            override fun onChannelSelected(channelName: String) {}
        })
    }



}
