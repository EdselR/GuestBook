package com.narindo.guestbook

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.Window
import android.widget.*
import com.google.firebase.firestore.FirebaseFirestore
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.Query
import com.narindo.guestbook.util.ProgressDialogHelper
import com.narindo.guestbook.util.firestoreAdapter
import com.narindo.guestbook.util.userClass
import android.widget.AdapterView
import android.R.array
import android.content.Intent
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import com.narindo.guestbook.util.myBounceInterpolator


//import sun.management.MemoryUsageCompositeData.getMax

class guestListActivity : AppCompatActivity() {

      var sort:String = "name"
      var direction:Query.Direction = Query.Direction.DESCENDING

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_list)

        initializeSort()
        initializeDir()


        fillData(sort,direction)

        val back_button = findViewById<Button>(R.id.backImage)
        back_button.setOnClickListener {
            backButtonGuestForm()
        }

    }


    //Listener for sign in button, opens the sign in form
    fun backButtonGuestForm(){

        val button = findViewById<Button>(R.id.backImage)
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = myBounceInterpolator(0.2, 20.0)

        myAnim.interpolator = interpolator

        button.startAnimation(myAnim)

        val intent = Intent(this, homeScreen::class.java)
        startActivity(intent)
        finish()
    }

    fun initializeSort() {

        val spinnerTwoSort = findViewById<View>(R.id.spinnerTwoSort) as Spinner

        val itemsOne = arrayOf("Sign In Time", "Sign Out Time", "Name", "Company", "Visiting Type")
        val adapterOne = ArrayAdapter(this, R.layout.spinner_item, itemsOne )

        spinnerTwoSort.adapter = adapterOne
        spinnerTwoSort.onItemSelectedListener = object:  AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sort = parent!!.getItemAtPosition(position).toString()

                when(sort){

                    "Sign In Time" -> sort = "signInTime"
                    "Sign Out Time" -> sort = "signOutTime"
                    "Name" -> sort = "name"
                    "Company" -> sort = "company"
                    "Visiting Type" -> sort = "type"
                    else -> sort = "signInTime"
                }

                fillData(sort,direction)
            }
        }

    }


    fun initializeDir(){

        val spinnerTwoDir = findViewById<View>(R.id.spinnerTwoDir) as Spinner

        val itemsTwo = arrayOf("Descending", "Ascending")
        val adapterTwo = ArrayAdapter(this, R.layout.spinner_item, itemsTwo)

        spinnerTwoDir.adapter = adapterTwo
        spinnerTwoDir.onItemSelectedListener = object:  AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val dirChoice = parent!!.getItemAtPosition(position).toString()

                when(dirChoice){

                    "Ascending" -> direction = Query.Direction.ASCENDING
                    "Descending" -> direction = Query.Direction.DESCENDING
                    else -> direction = Query.Direction.ASCENDING
                }

                fillData(sort, direction)
            }
        }


    }


    fun fillData(sort: String, direction: Query.Direction){

        val query = FirebaseFirestore.getInstance().collection("Guests")
                .orderBy(sort,direction)
                .limit(50)

        val options = FirestoreRecyclerOptions.Builder<userClass>().setQuery(query, userClass::class.java)
                .build()

        var rv = findViewById<RecyclerView>(R.id.recyclerView)
        rv.layoutManager = LinearLayoutManager(this@guestListActivity, LinearLayout.VERTICAL, false)

        val fireAdapter = firestoreAdapter(options, this@guestListActivity)
        rv.adapter = fireAdapter
        fireAdapter.startListening()
    }
}
