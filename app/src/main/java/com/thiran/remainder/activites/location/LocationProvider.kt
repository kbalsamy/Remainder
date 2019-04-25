package com.thiran.remainder.activites.location

import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.util.Log
import com.thiran.remainder.activites.Remainder
import java.lang.ref.WeakReference
import java.util.concurrent.CopyOnWriteArrayList

object LocationProvider {

    private val listeners = CopyOnWriteArrayList<WeakReference<LocationListener>>()

    private val locationlistener = object : LocationListener {

        override fun onLocationChanged(location: Location?) {

            val iterator = listeners.iterator()
            while (iterator.hasNext()){

                val reference = iterator.next()
                val listener = reference.get()
                listener?.onLocationChanged(location)
            }
        }

        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

            val iterator = listeners.iterator()

            while (iterator.hasNext()){
                val reference = iterator.next()
                val listener = reference.get()
                listener?.onStatusChanged(provider, status, extras)
            }
        }

        override fun onProviderEnabled(provider: String?) {

            val iterator = listeners.iterator()

            while (iterator.hasNext()){
                val reference = iterator.next()
                val listener = reference.get()
                listener?.onProviderEnabled(provider)
            }
        }

        override fun onProviderDisabled(provider: String?) {

            val iterator = listeners.iterator()

            while (iterator.hasNext()){
                val reference = iterator.next()
                val listener = reference.get()
                listener?.onProviderDisabled(provider)
            }
        }
    }

    fun subscribe(subscriber:LocationListener):Boolean{

        val result = doSubscribe(subscriber)
        turnOnListening()
        return result

    }

    fun doSubscribe(subscriber: LocationListener):Boolean{

        TODO()
    }

    fun unsubscribe(subscriber: LocationListener):Boolean{

        val result = doUnsubcribe(subscriber)
        if(listeners.isEmpty()){
            turnOffListening()
        }
        return result
    }

    fun doUnsubcribe(subscriber: LocationListener):Boolean{

        TODO()
    }

    fun turnOnListening(){

        val ctx = Remainder.ctx

        if (ctx != null) {

            val permissioncheckok  = ActivityCompat.checkSelfPermission(ctx, android.Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission((ctx,android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)

            if (!permissioncheckok) {
                throw IllegalStateException(
                    "Permission required for [ACCESS_COARSE_LOCATION] [ACCESS_FINE_LOCATION]"
                )
            }

            val locationmanager = ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val criteria = Criteria()

            criteria.accuracy = Criteria.ACCURACY_FINE
            criteria.powerRequirement = Criteria.POWER_HIGH
            criteria.isAltitudeRequired= false
            criteria.isBearingRequired=false
            criteria.isSpeedRequired =false
            criteria.isCostAllowed = true


            locationmanager.requestLocationUpdates(1000, 1F, criteria, locationlistener, Looper.getMainLooper())

        }else{

            Log.v("location", "context is not available")
        }



    }

    fun turnOffListening(){

        TODO()
    }


}