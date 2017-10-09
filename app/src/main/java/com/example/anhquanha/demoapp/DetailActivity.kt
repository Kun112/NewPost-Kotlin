package com.example.anhquanha.demoapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_view)
        val intent = intent
        val userName = intent.getStringExtra("userId")
        val content:String = intent.getStringExtra("body")
        val cmtCount:Int = intent.getIntExtra("cmtCount", 0)

        var userIdtv:TextView = findViewById<TextView>(R.id.author)
        var bodytv:TextView = findViewById<TextView>(R.id.body)
        var cmttv:TextView = findViewById<TextView>(R.id.cmtcounts)

        userIdtv.text = userName
        bodytv.text = content
        cmttv.text = "Number of comments: " + cmtCount.toString()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}

