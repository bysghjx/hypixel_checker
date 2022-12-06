package com.example.myapplication.util

import android.os.Message
import java.util.Objects

object MessageUtils {
    fun newMessage(objects: Objects,what: Int): Message {
        val message = Message()
        message.obj =objects
        message.what = what
        return message
    }
}