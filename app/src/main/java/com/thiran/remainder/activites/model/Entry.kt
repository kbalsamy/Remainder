package com.thiran.remainder.activites.model

import android.location.Location
import com.thiran.remainder.activites.database.DBbase

abstract class Entry(val title:String, val content: String, val location:Location ):DBbase()


