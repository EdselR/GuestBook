package com.narindo.guestbook.util

import android.app.ProgressDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.narindo.guestbook.R
import com.narindo.guestbook.packageList



data class firestoreAdapterPackage(var option : FirestoreRecyclerOptions<packageClass>, var context: Context): FirestoreRecyclerAdapter<packageClass, firestoreAdapterPackage.BorrowerHolder>(option){


    var dialog : ProgressDialog = progressDialog(context as packageList,"","Fetching Package information...")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):BorrowerHolder{
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.package_layout,parent,false)

        return BorrowerHolder(v)
    }

    override fun onBindViewHolder(holder: BorrowerHolder, position: Int, model: packageClass) {

        holder?.packageDispatcher?.text = model.dispatcher
        holder?.packageService?.text = model.courierService
        holder?.packageDescription?.text = model.description
        holder?.packageType?.text = model.deliveryType
        holder?.packageRecipient?.text = model.recipient
        holder?.packageSender?.text = model.sender
        holder?.packageTime?.text = model.receiving_sending_time
    }


    class BorrowerHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val packageDispatcher = itemView.findViewById<TextView>(R.id.packageDispatcher)
        val packageService= itemView.findViewById<TextView>(R.id.packageService)
        val packageDescription =  itemView.findViewById<TextView>(R.id.packageDescription)
        val packageType =  itemView.findViewById<TextView>(R.id.packageType)
        val packageRecipient =  itemView.findViewById<TextView>(R.id.packageRecipient)
        val packageSender =  itemView.findViewById<TextView>(R.id.packageSender)
        val packageTime =  itemView.findViewById<TextView>(R.id.packageTime)
    }

    override fun onDataChanged() {
        if (dialog != null && dialog.isShowing) {
            dialog.dismiss()
        }
    }


}