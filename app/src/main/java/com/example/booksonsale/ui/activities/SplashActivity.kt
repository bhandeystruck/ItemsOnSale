package com.example.booksonsale.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.booksonsale.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //need to add flags to get rid of the top bars
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        //this function makes the slash change to main activity after a delay
        //we will finish the activity after the 2500 delay is finished
        //and move to the main activity
        @Suppress("DEPRECATION")
        Handler().postDelayed(
                {
                    //Launch the main activity after the delay
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    finish()//calling this will close the activity after it is done
                },
                1500
        )

    }
}