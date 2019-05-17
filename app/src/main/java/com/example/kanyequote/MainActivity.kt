package com.example.kanyequote

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import org.json.JSONObject

import io.branch.indexing.BranchUniversalObject
import io.branch.referral.Branch
import io.branch.referral.BranchError
import io.branch.referral.util.LinkProperties
import android.util.Log
import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*

import khttp.get
import org.jetbrains.anko.doAsync
import android.widget.Button

class MainActivity : Activity() {

    var quote = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.refreshButton)
        button.setOnClickListener {
            refreshOnclick()
        }
    }


    override fun onStart() {
        super.onStart()

        doAsync{
            val test = get("https://api.kanye.rest/").jsonObject.get("quote")
            Log.e("error------", test.toString())
            quote = test.toString() // this works
        }
        //Branch.getInstance(getApplicationContext()).initSession{
        Branch.getInstance(getApplicationContext()).initSession(object : Branch.BranchReferralInitListener {
            override fun onInitFinished(referringParams: JSONObject, error: BranchError?) {
                //If not Launched by clicking Branch link
                if (error == null) {
                    Log.e("BRANCH SDK", referringParams.toString())
                    // Retrieve deeplink keys from 'referringParams' and evaluate the values to determine where to route the user
                    // Check '+clicked_branch_link' before deciding whether to use your Branch routing logic
                    kanyeQuoteText.text = quote

                } else {
                    Log.e("BRANCH SDK", "error")
                }
            }
        }, this.intent.data, this)
    }

    public override fun onNewIntent(intent: Intent) {
        this.intent = intent
    }

    public fun refreshOnclick(){
        doAsync{
            val test = get("https://api.kanye.rest/").jsonObject.get("quote")
            Log.e("error------", test.toString())
            quote = test.toString() // this works
            kanyeQuoteText.text = quote
        }

    }
}
