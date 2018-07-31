package com.narindo.guestbook

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.narindo.guestbook.util.*

class packageList : AppCompatActivity() {

    var sort:String = "receiving_sending_time"
    var direction: Query.Direction = Query.Direction.DESCENDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_package_list)

        initializeSort()
        initializeDir()


        fillData(sort,direction)

        val back_button = findViewById<Button>(R.id.backImageFour)
        back_button.setOnClickListener {
            backButtonGuestForm()
        }



    }

    fun initializeSort() {

        val spinnerThreeSort = findViewById<View>(R.id.spinnerThreeSort) as Spinner

        val itemsOne = arrayOf("Time", "Delivery Type", "Dispatcher", "Courier Service", "Recipient", "Sender")
        val adapterOne = ArrayAdapter(this, R.layout.spinner_item, itemsOne )

        spinnerThreeSort.adapter = adapterOne
        spinnerThreeSort.onItemSelectedListener = object:  AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sort = parent!!.getItemAtPosition(position).toString()

                when(sort){

                    "Time" -> sort = "receiving_sending_time"
                    "Delivery Type" -> sort = "deliveryType"
                    "Courier Service" -> sort = "courierService"
                    "Recipient" -> sort = "recipient"
                    "Sender" -> sort = "sender"
                    else -> sort = "receiving_sending_time"
                }

                fillData(sort,direction)
            }
        }

    }

    fun initializeDir(){

        val spinnerThreeDir = findViewById<View>(R.id.spinnerThreeDir) as Spinner

        val itemsTwo = arrayOf("Descending", "Ascending")
        val adapterTwo = ArrayAdapter(this, R.layout.spinner_item, itemsTwo)

        spinnerThreeDir.adapter = adapterTwo
        spinnerThreeDir.onItemSelectedListener = object:  AdapterView.OnItemSelectedListener {
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


    //Listener for sign in button, opens the sign in form
    fun backButtonGuestForm(){

        val button = findViewById<Button>(R.id.backImageFour)
        val myAnim = AnimationUtils.loadAnimation(this, R.anim.bounce)

        // Use bounce interpolator with amplitude 0.2 and frequency 20
        val interpolator = myBounceInterpolator(0.2, 20.0)

        myAnim.interpolator = interpolator

        button.startAnimation(myAnim)

        val intent = Intent(this, homeScreen::class.java)
        startActivity(intent)
        finish()
    }
    fun fillData(sort: String, direction: Query.Direction){

        val query = FirebaseFirestore.getInstance().collection("Package")
                .orderBy(sort,direction)
                .limit(50)

        val options = FirestoreRecyclerOptions.Builder<packageClass>().setQuery(query, packageClass::class.java)
                .build()

        var rv = findViewById<RecyclerView>(R.id.recyclerViewTwo)
        rv.layoutManager = LinearLayoutManager(this@packageList, LinearLayout.VERTICAL, false)

        val fireAdapter = firestoreAdapterPackage(options, this@packageList)
        rv.adapter = fireAdapter
        fireAdapter.startListening()
    }

}
