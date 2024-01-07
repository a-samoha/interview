package com.artsam.interview.data

import android.content.Context

class QuestionsRepository() {

    fun getJsonDataFromAsset(context: Context, fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}