package com.thiran.remainder.activites.permission

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

abstract class PermissionAppcompatActivity:AppCompatActivity() {

    private val permissionID = AtomicInteger()

    private val permissionRequests = ConcurrentHashMap<Int, List<String>>()

    private val permissioncallback = ConcurrentHashMap<List<String>, PermissionCallbacks>()

    private val onpermissionrequest = object : PermissionCallbacks {
        override fun onPermissionGranted() {
            Log.v("permission", "Permission granted")
        }

        override fun onPermissionDenied() {
            Log.v("permission", "Permission Denied")
        }
    }


    fun requestPermission(vararg permission:String, callback: PermissionCallbacks = onpermissionrequest){

        val id = permissionID.incrementAndGet()
        val items = mutableListOf<String>()
        items.addAll(permission)
        permissionRequests[id] = items
        permissioncallback[items] = callback
        ActivityCompat.requestPermissions(this, permission, id)

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        val items = permissionRequests[requestCode]

        items?.let {

            val callback = permissioncallback[items]
            callback?.let {

                var success = true

                for (x in 0..grantResults.lastIndex){

                    val result = grantResults[x]

                    if (result != PackageManager.PERMISSION_GRANTED) {
                        success = false
                        break
                    }
                }

                if (success){

                    callback.onPermissionGranted()
                }

                else {
                    callback.onPermissionDenied()
                }
            }


        }
    }

}