package com.thiran.remainder.activites

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.Toast
import com.thiran.remainder.R
import com.thiran.remainder.activites.permission.PermissionAppcompatActivity
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity:PermissionAppcompatActivity() {

    abstract val tag: String

    abstract fun getLayout():Int

    abstract fun setTitle():Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
        setSupportActionBar(toolbar)

        requestPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.INTERNET)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

}