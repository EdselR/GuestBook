package com.narindo.guestbook.util

import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.util.Log

import java.util.*
//import jdk.nashorn.internal.runtime.ECMAException.getException
import java.nio.file.Files.exists
//import android.support.test.orchestrator.junit.BundleJUnitUtils.getResult
//import org.junit.experimental.results.ResultMatchers.isSuccessful
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.OnCompleteListener
import android.support.annotation.NonNull
import com.google.firebase.firestore.*
import com.google.firebase.firestore.FirebaseFirestoreSettings
import java.text.SimpleDateFormat
import android.R.interpolator.bounce
import android.view.animation.AnimationUtils
import android.view.animation.Animation
import android.R.attr.button
import android.R.attr.onClick
import android.app.Dialog
import android.app.PendingIntent.getActivity
import android.content.Context
import android.view.View
import android.widget.Button
import com.narindo.guestbook.R
import android.view.LayoutInflater
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import org.w3c.dom.Text


/**
 * Created by edsel on 13/07/2018.
 */


fun simpleAlertDialog(thisActivity: Activity, title:String, message:String, yesMessage: String, yesIntent: Intent?){
        val builder = AlertDialog.Builder(thisActivity)


// Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout

    builder.setMessage(message)
                .setPositiveButton(yesMessage,DialogInterface.OnClickListener{_,_->

                        if(yesIntent != null){

                            thisActivity.startActivity(yesIntent)
                        }

                })
                .show()
}

fun progressDialog(thisActivity: Activity, title:String, message: String): ProgressDialog{
    val dialog = ProgressDialog(thisActivity)
    dialog.setMessage(message)
    dialog.setTitle(title)
    dialog.setCancelable(false)
    dialog.isIndeterminate = true
    dialog.show()

    return dialog
}


fun dialogNotif(app: Context, yesIntent: Intent?, dialogMessage:String, okMessage: String) {
    val dialog = Dialog(app)
    dialog.setCancelable(true)
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
    val window = dialog.window
    window!!.setBackgroundDrawableResource(R.color.fui_transparent)
    dialog.setContentView(R.layout.dialog_layout)

    val message = dialog.findViewById<TextView>(R.id.dialogText)
    message.text = dialogMessage

    val dialogButton = dialog.findViewById<Button>(R.id.dialogButtonOK)
    dialogButton.text = okMessage

    dialogButton.setOnClickListener {

        if(yesIntent != null){

            app.startActivity(yesIntent)
        }
        dialog.dismiss()
    }

    dialog.show()
}

fun yesNoAlertDialog(thisActivity: Activity, title: String, message: String, yesMessage:String,yesIntent: Intent, noMessage:String, noIntent: Intent?){

    val builder = AlertDialog.Builder(thisActivity)
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(yesMessage, DialogInterface.OnClickListener{_, _ ->
                    if(yesIntent != null){
                        thisActivity.startActivity(yesIntent)
                    }
                })
                .setNegativeButton(noMessage, DialogInterface.OnClickListener{_, _ ->
                        thisActivity.startActivity(noIntent)
                })
                .show()

}

fun addData(db:FirebaseFirestore, collName: String, newGuest: userClass,loadingDialog: ProgressDialogHelper){
    var refid = ""
    val signInString = "Sign-In Activity"

    db.collection(collName).add(newGuest)
            .addOnSuccessListener {
                Log.d(signInString, "Guest data successfully written! " + it.id)
                refid = it.id

                Log.e("email ", newGuest.email.toString())

                val docName = newGuest.email.toString()
                val vkeyData:HashMap<String, Any> = HashMap()
                vkeyData.put("vkey", refid)

                db.collection("visitor_key").document(docName).set(vkeyData)
                        .addOnSuccessListener {
                            Log.d(signInString, "vkey successfully written! ")
                            loadingDialog.dismiss()
                        }
                        .addOnFailureListener{e ->
                            Log.w(signInString, "Error writing vkey", e)
                            loadingDialog.dismiss()
                        }
            }
            .addOnFailureListener { e ->
                Log.w(signInString, "Error writing guest data", e)
                loadingDialog.dismiss()

            }

}

fun addPackageData(db:FirebaseFirestore, collName: String, newPackage:packageClass,loadingDialog: ProgressDialogHelper){
    var refid = ""
    val signInString = "Delivery Activity"

    db.collection(collName).add(newPackage)
            .addOnSuccessListener {
                Log.d(signInString, "Package data successfully written! " + it.id)

            }
            .addOnFailureListener { e ->
                Log.w(signInString, "Error writing package data", e)
                loadingDialog.dismiss()

            }

}

fun deleteKey(db:FirebaseFirestore, collName: String, newGuest: userClass){

    val vkeyDel:HashMap<String, Any> = HashMap()
    vkeyDel.put("vkey", FieldValue.delete())
    val deleteString = "Delete Key"

        db.collection(collName)
            .document(newGuest.email).update(vkeyDel).addOnSuccessListener {

                Log.d(deleteString, newGuest.email + "vkey successfully written! ")
            }
            .addOnFailureListener{e ->
                Log.w(deleteString, "Error writing" + newGuest.email+ "vkey", e)
            }


}

fun updateSignOut(db:FirebaseFirestore, collName: String, newGuest: userClass, loadingDialog: ProgressDialogHelper){

    val signOutString = "signOutTime"
    val updateString = "updateSignOut"

    db.collection(collName)
            .document(newGuest.vkey).update(signOutString, newGuest.signOutTime).addOnSuccessListener {

                Log.d(updateString, signOutString + " successfully updated! ")
                loadingDialog.dismiss()
            }
            .addOnFailureListener{e ->
                Log.w(updateString, "Error writing" + signOutString, e)
                loadingDialog.dismiss()
            }


}

fun getFormattedDate():String{
    val cal = Calendar.getInstance()
    val myFormat = "yyyy-MM-dd h:mm a" // mention the format you need
    val sdf = SimpleDateFormat(myFormat, Locale.US)


    return sdf.format(cal.time)
}



