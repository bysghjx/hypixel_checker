package com.example.myapplication.util

import android.app.ProgressDialog
import android.content.Context


class WaitDialog {
    companion object{

        lateinit var progressDialog: ProgressDialog

        fun waitingDialog(context: Context) {
            progressDialog = ProgressDialog(context)
            progressDialog.setTitle("查询中")
            progressDialog.setMessage("请等待")
            progressDialog.setCancelable(false) //设置不可关闭
            progressDialog.setIndeterminate(true) //设置模糊
            progressDialog.show()
        }

        fun WaitDialogDismiss(){
            progressDialog.dismiss()
        }
    }

}