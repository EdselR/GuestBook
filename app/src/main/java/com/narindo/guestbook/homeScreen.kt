package com.narindo.guestbook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import com.narindo.guestbook.util.addData
import com.narindo.guestbook.util.myBounceInterpolator
import com.narindo.guestbook.util.simpleAlertDialog


class homeScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

    }

    //Listener for sign in button, opens the sign in form
    fun signInForm(view: View){

        val button = findViewById<Button>(R.id.signIn)
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = myBounceInterpolator(0.2, 20.0)

        myAnim.interpolator = interpolator

        button.startAnimation(myAnim)

        val intent = Intent(this, signInActivity::class.java)

        startActivity(intent)
    }

    fun signOutForm(view: View){

        val button = findViewById<Button>(R.id.signOut)
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = myBounceInterpolator(0.2, 20.0)

        myAnim.interpolator = interpolator

        button.startAnimation(myAnim)

        val intent = Intent(this, signOutActivity::class.java)

        startActivity(intent)
    }

    fun guestListForm(view: View){

        val button = findViewById<Button>(R.id.guestList)
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = myBounceInterpolator(0.2, 20.0)

        myAnim.interpolator = interpolator

        button.startAnimation(myAnim)

        val intent = Intent(this, guestListActivity:: class.java)

        startActivity(intent)
    }

    //Listener for sign in button, opens the sign in form
    fun deliveryPageForm(view: View){

        val button = findViewById<Button>(R.id.deliveryBtn)
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = myBounceInterpolator(0.2, 20.0)

        myAnim.interpolator = interpolator

        button.startAnimation(myAnim)

        val intent = Intent(this, deliveryPage::class.java)

        startActivity(intent)
    }

    //Listener for sign in button, opens the sign in form
    fun deliveryListBtn(view: View){

        val button = findViewById<Button>(R.id.deliveryListBtn)
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = myBounceInterpolator(0.2, 20.0)

        myAnim.interpolator = interpolator

        button.startAnimation(myAnim)

        val intent = Intent(this, packageList::class.java)

        startActivity(intent)
    }
}

