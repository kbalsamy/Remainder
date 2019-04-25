package com.thiran.remainder.activites

import android.os.Bundle
import com.thiran.remainder.R
import kotlinx.android.synthetic.main.todolayout.*


class todoActivity:itemActivity() {

    companion object {

        val date = "EXTRA_DATE"
        val time = "EXTRA_TIME"

    }

    override val tag: String
        get() = "TODO Activity"

    override fun getLayout(): Int {
        return R.layout.todolayout
    }

    override fun setTitle(): Int {

        return R.string.app_name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val data = intent.extras

        data?.let {

            val date = data.getString(date, "")
            val time = data.getString(time, "")

            todo_date_ID.text = date
            todo_time_id.text = time


        }
    }
}
