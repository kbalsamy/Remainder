package com.thiran.remainder.activites.model

import android.location.Location

class Todo(title:String, content:String, location: Location, val scheduledfor:Long):Entry(title, content, location) {

    override var id: Long = 0L
}
