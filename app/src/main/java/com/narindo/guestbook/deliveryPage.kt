package com.narindo.guestbook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.narindo.guestbook.util.*

class deliveryPage : AppCompatActivity() {
    var packageType: String = "Receiving"
    lateinit var dispatcherET:EditText
    lateinit var serviceET:EditText
    lateinit var descriptionET:EditText
    lateinit var selectedRadio:RadioGroup
    lateinit var recipientET:EditText
    lateinit var senderET:EditText
    lateinit var backBtn: Button
    lateinit var saveInfoBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_page)


        dispatcherET = findViewById<EditText>(R.id.dispatcherName)
        serviceET = findViewById<EditText>(R.id.courierService)
        descriptionET = findViewById<EditText>(R.id.packageDescription)
        selectedRadio = findViewById<RadioGroup>(R.id.packageRadio)
        recipientET = findViewById<EditText>(R.id.recipient)
        senderET = findViewById<EditText>(R.id.sender)
        backBtn= findViewById<Button>(R.id.backImageThree)
        saveInfoBtn = findViewById<Button>(R.id.saveInfo)

        recipientET!!.visibility = View.GONE

        selectedRadio.setOnCheckedChangeListener{group, checkedId ->
            if(checkedId == R.id.radio_sending){
                packageType = "Sending"

                senderET!!.visibility = View.INVISIBLE
                recipientET!!.visibility = View.VISIBLE
            }else{
                packageType = "Receiving"

                recipientET!!.visibility = View.INVISIBLE
                senderET!!.visibility = View.VISIBLE
            }
        }

        backBtn.setOnClickListener{
            backButtonPackage()
        }

        saveInfoBtn.setOnClickListener{

            savePackageData()
        }
    }

    //Listener for sign in button, opens the sign in form
    fun backButtonPackage(){
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = myBounceInterpolator(0.2, 20.0)

        myAnim.interpolator = interpolator

        backBtn.startAnimation(myAnim)

        val intent = Intent(this, homeScreen::class.java)
        startActivity(intent)
        finish()
    }

    fun savePackageData(){
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = myBounceInterpolator(0.2, 20.0)

        myAnim.interpolator = interpolator

        saveInfoBtn.startAnimation(myAnim)

        val dispatcher = dispatcherET.text.toString()
        val service = serviceET.text.toString()
        val description = descriptionET.text.toString()
        var sender = senderET.text.toString()
        var recipient = recipientET.text.toString()
        val receiving_sending_time = getFormattedDate()

        if(packageType == "Receiving"){
            recipient = "n/a"
        }
        else{
            sender = "n/a"
        }

        if(dispatcher.isNullOrEmpty()){
            dialogNotif(this, null, "Dispatcher Name is mandatory","OK")
        }else if(service.isNullOrEmpty()){
            dialogNotif(this, null, "Courier Service Name is mandatory","OK")
        }else if(description.isNullOrEmpty()){
            dialogNotif(this, null, "Package Description is mandatory","OK")
        }else if(packageType == "Receiving" && sender.isNullOrEmpty()){
            dialogNotif(this, null, "Sender Name is mandatory","OK")
        }else if(packageType == "Sending" && recipient.isNullOrEmpty()){
            dialogNotif(this, null, "Recipient Name is mandatory","OK")
        }
        else {
            val newPackage = packageClass(dispatcher, service, description, packageType,
                    recipient, sender, receiving_sending_time)

            saveInfoBtn.isEnabled = false

            val settings = FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build()

            val db = FirebaseFirestore.getInstance()
            db.firestoreSettings = settings

            var loading = ProgressDialogHelper(this)
            loading.show("Loading")

            addPackageData(db,"Package", newPackage,loading)

            //set the intent
            val intent = Intent(this, homeScreen::class.java)

            //set the dialog
            dialogNotif(this, null, "Package Information is Successfully saved","OK")

            saveInfoBtn.isEnabled = true
        }
    }
}
