package com.thiran.remainder.activites

import android.app.Activity
import android.os.Bundle
import com.thiran.remainder.activites.model.MODE

abstract class itemActivity:BaseActivity() {

    protected var mode = MODE.VIEW
    protected var success = Activity.RESULT_CANCELED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = intent.extras

        data?.let {
            val modeToSet = data.getInt(MODE.EXTRA_KEYS, MODE.CREATE.mode)
            mode = MODE.getValue(modeToSet)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        setResult(success)

    }

}