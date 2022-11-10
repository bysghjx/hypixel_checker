package com.example.myapplication.util

import kotlin.random.Random

object RanDomUtils {
    fun createRan(max_time: Long): Long {
        val random = Random(System.currentTimeMillis())
        val wait = random.nextLong(max_time)
        return wait
    }
    fun createXtoX(low_time: Long,max_time: Long): Long {
        val random = Random(System.currentTimeMillis())
        val wait = random.nextLong(low_time,max_time)
        return wait;
    }


}