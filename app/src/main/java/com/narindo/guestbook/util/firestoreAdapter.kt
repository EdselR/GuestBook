package com.narindo.guestbook.util

import android.app.ProgressDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.narindo.guestbook.R
import com.narindo.guestbook.guestListActivity


data class firestoreAdapter(var option : FirestoreRecyclerOptions<userClass>, var context: Context): FirestoreRecyclerAdapter<userClass, firestoreAdapter.BorrowerHolder>(option){


    var dialog : ProgressDialog = progressDialog(context as guestListActivity,"","Fetching Guest information...")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BorrowerHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.item_layout,parent,false)

        return BorrowerHolder(v)
    }

    override fun onBindViewHolder(holder: BorrowerHolder, position: Int, model: userClass) {

        holder?.guestName?.text = model.name
        holder?.guestCompany?.text = model.company
        holder?.guestEmail?.text = model.email
        holder?.guestPhone?.text = model.phone
        holder?.guestType?.text = model.type
        holder?.guestPurpose?.text = model.purpose
        holder?.guestSignIn?.text =  model.signInTime
        holder?.guestSignOut?.text = model.signOutTime

        val decodedString = Base64.decode(model.photoString, Base64.DEFAULT)

        Glide.with(context)
                .asDrawable()
                .load(decodedString)
                .into(holder?.guestPhoto!!)

    }


    class BorrowerHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val guestName = itemView.findViewById<TextView>(R.id.guestName)
        val guestCompany = itemView.findViewById<TextView>(R.id.guestCompany)
        val guestEmail = itemView.findViewById<TextView>(R.id.guestEmail)
        val guestPhone = itemView.findViewById<TextView>(R.id.guestPhone)
        val guestType = itemView.findViewById<TextView>(R.id.guestType)
        val guestPurpose = itemView.findViewById<TextView>(R.id.guestPurpose)
        val guestSignIn = itemView.findViewById<TextView>(R.id.guestSignIn)
        val guestSignOut = itemView.findViewById<TextView>(R.id.guestSignOut)
        val guestPhoto = itemView.findViewById<ImageView>(R.id.guestPhoto)

    }

    override fun onDataChanged() {
        if (dialog != null && dialog.isShowing) {
            dialog.dismiss()
        }
    }


}