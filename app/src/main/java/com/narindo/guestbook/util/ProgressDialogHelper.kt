package com.narindo.guestbook.util

import android.app.ProgressDialog
import android.content.Context

class ProgressDialogHelper(context: Context) {

    private var progressDialog: ProgressDialog? = null

    init {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(context)
        }
    }

    @Synchronized fun show(message: String) {
        progressDialog!!.setMessage(message)
        progressDialog!!.setCancelable(false)
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog!!.show()
    }

    @Synchronized fun dismiss() {
        if (progressDialog == null) {
            return
        }

        progressDialog!!.dismiss()
    }
}