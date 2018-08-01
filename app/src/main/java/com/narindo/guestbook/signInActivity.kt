package com.narindo.guestbook

import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.widget.*
import android.widget.RadioGroup
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.narindo.guestbook.PhotoId.PhotoIdActivity
import com.narindo.guestbook.util.*
import kotlinx.android.synthetic.main.activity_sign_in.*
import android.view.animation.AnimationUtils
import android.graphics.BitmapFactory
import com.narindo.guestbook.PreferenceHelper.set
import com.narindo.guestbook.PreferenceHelper.get



class signInActivity : AppCompatActivity() {

    var guestPurpose : String = ""
    var guestType: String = ""
    var pictureFlag = false
    lateinit var userCompanyET:EditText
    lateinit var userNameET:EditText
    lateinit var userEmailET:EditText
    lateinit var userPhoneET:EditText
    lateinit var prefs:SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val spinner = findViewById<View>(R.id.spinnerOne) as Spinner

        val items = arrayOf("Meeting","Interview", "Private", "Other")
        val adapter = ArrayAdapter(this, R.layout.spinner_item, items)

        userCompanyET = findViewById<EditText>(R.id.company)
        userNameET = findViewById<EditText>(R.id.name)
        userEmailET = findViewById<EditText>(R.id.email)
        userPhoneET = findViewById<EditText>(R.id.phone)

        prefs = PreferenceHelper.customPrefs(this)
        /**
         * RONALD: Check data in SharedPreference before do anything.
         * IF data exists then write into form
         */
        val userNamePref:String? = prefs[PrefConstants.KEY_NAME]
        val userCompanyPref:String? = prefs[PrefConstants.KEY_COMPANY_NAME]
        val userEmailPref:String? = prefs[PrefConstants.KEY_EMAIL]
        val userPhonePref:String? = prefs[PrefConstants.KEY_PHONE]

        userNameET.setText(userNamePref)
        userCompanyET.setText(userCompanyPref)
        userEmailET.setText(userEmailPref)
        userPhoneET.setText(userPhonePref)

        val othersField = findViewById<EditText>(R.id.others)
        othersField!!.visibility = View.GONE

        spinner.adapter = adapter
        spinner.onItemSelectedListener = object:  AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var purpose = parent!!.getItemAtPosition(position).toString()
                guestPurpose = purpose

                if(purpose == "Other"){
                    othersField!!.visibility = View.VISIBLE
                }
                else{
                    othersField!!.visibility = View.GONE
                }
            }
        }
        val selectedRadio = findViewById<RadioGroup>(R.id.radioGroup)

        guestType = "Business"

        selectedRadio.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId == R.id.radio_one){
                guestType = "Personal"
            }
            else{
                guestType = "Business"
            }
        }

        takePhoto.setOnClickListener {

            val button = findViewById<Button>(R.id.takePhoto)
            val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

            // Use bounce interpolator with amplitude 0.2 and frequency 20
            val interpolator = myBounceInterpolator(0.2, 20.0)

            myAnim.interpolator = interpolator

            button.startAnimation(myAnim)
            button.isEnabled = false


            /**
             * RONALD: Save data into SharedPreference before move to different activity
             * so data entered in form will be remain
             */
            prefs[PrefConstants.KEY_NAME] = userNameET.text.toString()
            prefs[PrefConstants.KEY_COMPANY_NAME] = userCompanyET.text.toString()
            prefs[PrefConstants.KEY_EMAIL] = userEmailET.text.toString()
            prefs[PrefConstants.KEY_PHONE] = userPhoneET.text.toString()


            val intent = Intent(this, PhotoIdActivity::class.java)
            startActivity(intent)

            button.isEnabled = true
        }



        var photoString = intent.getStringExtra("photoString")

        if(!photoString.isNullOrEmpty()) {
            val decodedString = Base64.decode(photoString, Base64.DEFAULT)

            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
            photoID.setImageBitmap(decodedByte)

            pictureFlag = true
        }

        val back_button = findViewById<Button>(R.id.backImageTwo)
        back_button.setOnClickListener {
            backButtonGuestForm()
        }
    }

    //Listener for sign in button, opens the sends data
    fun saveData(view: View){

        val button = findViewById<Button>(R.id.signIn2)
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = myBounceInterpolator(0.2, 20.0)

        myAnim.interpolator = interpolator

        button.startAnimation(myAnim)

        //user information to be saved

        val userCompany = userCompanyET.text.toString()
        val userName = userNameET.text.toString()
        val userEmail = userEmailET.text.toString()
        val userPhone = userPhoneET.text.toString()

        val userType = guestType
        var photoString = intent.getStringExtra("photoString")

        if(guestPurpose == "Other"){
            guestPurpose = findViewById<EditText>(R.id.others).text.toString()
        }
        val userPurpose = guestPurpose

        //get current time
        val signInTime = getFormattedDate()

        //instantiate a new guest
        val newGuest = userClass(userName,userCompany, userEmail, userPhone, userType, userPurpose, signInTime,"n\\a", photoString)

        if(userName.isNullOrEmpty()){
            dialogNotif(this, null, "Name is mandatory","OK")
        }else if(userEmail.isNullOrEmpty()){
            dialogNotif(this, null, "Email is mandatory","OK")
        }
        else if(pictureFlag == false){
            dialogNotif(this, null, "Please take a picture of your ID","OK")
        }
        else{
            val button = findViewById<Button>(R.id.signIn2)
            button.isEnabled = false

            val settings = FirebaseFirestoreSettings.Builder()
                    .setPersistenceEnabled(true)
                    .build()

            val db = FirebaseFirestore.getInstance()
            db.firestoreSettings = settings

            var loading = ProgressDialogHelper(this)
            loading.show("Loading")

            addData(db,"Guests", newGuest,loading)

            /**
             * RONALD: Clear SharedPreference data upon successfully saved
             */
            prefs[PrefConstants.KEY_NAME] = ""
            prefs[PrefConstants.KEY_COMPANY_NAME] = ""
            prefs[PrefConstants.KEY_EMAIL] = ""
            prefs[PrefConstants.KEY_PHONE] = ""

            //set the intent
            val intent = Intent(this, homeScreen::class.java)

            //set the dialog
            dialogNotif(this, intent, "Guest is successfully signed in","OK")

            button.isEnabled = true
        }
    }

    //Listener for sign in button, opens the sign in form
    fun backButtonGuestForm(){

        val button = findViewById<Button>(R.id.backImageTwo)
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = myBounceInterpolator(0.2, 20.0)

        myAnim.interpolator = interpolator

        button.startAnimation(myAnim)

        val intent = Intent(this, homeScreen::class.java)
        startActivity(intent)
        finish()
    }
}

