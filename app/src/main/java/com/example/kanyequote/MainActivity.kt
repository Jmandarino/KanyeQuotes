package com.example.kanyequote

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import org.json.JSONObject

import io.branch.referral.Branch
import io.branch.referral.BranchError
import android.util.Log
import android.content.Intent
import android.os.Handler

class MainActivity : AppCompatActivity() {

    var quote = ""
    // https://medium.com/@pererikbergman/android-splash-screens-a1f44acb4fce

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onStart() {
        super.onStart()

        Branch.getInstance().initSession(object : Branch.BranchReferralInitListener {
            override fun onInitFinished(referringParams: JSONObject, error: BranchError?) {
                //If not Launched by clicking Branch link
                if (error == null) {
                    Log.e("BRANCH SDK", referringParams.toString())
                    // Retrieve deeplink keys from 'referringParams' and evaluate the values to determine where to route the user
                    // Check '+clicked_branch_link' before deciding whether to use your Branch routing logic
                    if (referringParams.has("quote")){
                        quote = referringParams.get("quote").toString()
                        Log.e("quote", quote)
                    } else{

                    }
                } else {
                    Log.e("BRANCH SDK", "error")
                }

                // splash is up for 1 second at least

                Handler().postDelayed({
                    val intent = Intent(this@MainActivity, HomeActivity::class.java)
                    intent.putExtra("quote", quote)
                    Log.e("extras", intent.extras.toString())
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
