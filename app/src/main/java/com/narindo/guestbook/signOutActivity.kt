package com.narindo.guestbook

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.narindo.guestbook.util.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class signOutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_out)

        val back_button = findViewById<Button>(R.id.backImageThree)
        back_button.setOnClickListener {
            backButtonGuestForm()
        }
    }

    //Listener for sign in button, opens the sign in form
    fun backButtonGuestForm(){

        val button = findViewById<Button>(R.id.backImageThree)
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = myBounceInterpolator(0.2, 20.0)

        myAnim.interpolator = interpolator

        button.startAnimation(myAnim)

        val intent = Intent(this, homeScreen::class.java)
        startActivity(intent)
        finish()
    }

    //Listener for sign out button, opens the sign out form
    fun signOut(view: View){

        val button = findViewById<Button>(R.id.signOut2)
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = myBounceInterpolator(0.2, 20.0)

        myAnim.interpolator = interpolator

        button.startAnimation(myAnim)
        button.isEnabled = false

        val keyColl = "visitor_key"
        val guestColl = "Guests"

        //get current time
        val signOutTime = getFormattedDate()

        val userName = findViewById<EditText>(R.id.name2).text.toString()
        var userEmail = findViewById<EditText>(R.id.email2).text.toString()

        var newGuest = userClass(userName,"",userEmail,"","","","",signOutTime,"")

        if(userEmail.isNullOrEmpty()){
            dialogNotif(this, null, "Email is Mandatory","OK")
        }
        else {

            val settings = FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build()

            val db = FirebaseFirestore.getInstance()
            db.firestoreSettings = settings

            var loading = ProgressDialogHelper(this)
            loading.show("Loading")

            db.collection("visitor_key")
                    .document(newGuest.email)
                    .get()
                    .addOnCompleteListener(OnCompleteListener<DocumentSnapshot> { task ->
                        val signOutString = "Sign-out Activity"
                        if (task.isSuccessful) {
                            var vkey = task.result
                            if (vkey.exists()) {
                                Log.d(signOutString, "DocumentSnapshot " + newGuest.email + " vkey: " + vkey.data!!)
                                newGuest.vkey = vkey.data!!.get("vkey").toString()

                                if (!newGuest.vkey.equals("null")) {

                                    deleteKey(db, keyColl, newGuest)
                                    updateSignOut(db, guestColl, newGuest, loading)

                                    val intent = Intent(this, homeScreen::class.java)
                                    dialogNotif(this, null, "Guest is successfully signed out","OK")
                                } else {
                                    loading.dismiss()
                                    dialogNotif(this, null, "Guest could not be found","OK")
                                }
                            } else {
                                Log.d(signOutString, "No such document")
                                loading.dismiss()

                                dialogNotif(this, null, "Guest could not be found","OK")

                            }
                        } else {
                            Log.d(signOutString, "get failed with ", task.exception)
                            loading.dismiss()

                            dialogNotif(this, null, "Guest could not be found","OK")
                        }
                    })
        }

        button.isEnabled = true
    }
}
