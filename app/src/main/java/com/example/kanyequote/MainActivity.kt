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
import android.os.Handler
import kotlinx.android.synthetic.main.activity_main.*

import khttp.get
import org.jetbrains.anko.doAsync
import android.widget.Button

class MainActivity : Activity() {

    var quote = ""
    // https://medium.com/@pererikbergman/android-splash-screens-a1f44acb4fce

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onStart() {
        super.onStart()

        Branch.getInstance(applicationContext).initSession(object : Branch.BranchReferralInitListener {
            override fun onInitFinished(referringParams: JSONObject, error: BranchError?) {
                //If not Launched by clicking Branch link
                if (error == null) {
                    Log.e("BRANCH SDK", referringParams.toString())
                    // Retrieve deeplink keys from 'referringParams' and evaluate the values to determine where to route the user
                    // Check '+clicked_branch_link' before deciding whether to use your Branch routing logic
                    if (referringParams.has("quote")){
                        quote = referringParams.get("quote").toString()
                    } else{

                    }
                } else {
                    Log.e("BRANCH SDK", "error")
                }

                // splash is up for 1 second at least

                Handler().postDelayed({
                    val intent = Intent(this@MainActivity, home::class.java)
                    intent.putExtra("quote", quote)
                    startActivity(intent)
                    overridePendingTransition(R.anim.fade_in,R.anim.fade_in)
                    finish()

                }, 500)


            }


        }, this.intent.data, this)
    }

    public override fun onNewIntent(intent: Intent) {
        this.intent = intent
    }
}
