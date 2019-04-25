package com.thiran.remainder.activites.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment :Fragment(){

    protected abstract val fname:String

    protected abstract fun getFragment():Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        return inflater?.inflate(getFragment(),container,false)
    }


}