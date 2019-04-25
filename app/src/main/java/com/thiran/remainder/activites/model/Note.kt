package com.thiran.remainder.activites.model

import android.location.Location

class Note(title:String, content:String, location: Location):Entry(title, content, location) {

    override var id: Long = 0L

}


