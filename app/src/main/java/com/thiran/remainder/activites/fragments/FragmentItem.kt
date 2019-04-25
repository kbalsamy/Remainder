package com.thiran.remainder.activites.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thiran.remainder.R
import com.thiran.remainder.activites.model.MODE
import com.thiran.remainder.activites.noteActivity
import com.thiran.remainder.activites.todoActivity
import java.text.SimpleDateFormat
import java.util.*


class FragmentItem:BaseFragment() {

    private val NOTE_REQUEST = 1

    private val TODO_REQUEST = 2

    override val fname: String
        get() = "Fragment 1"

    override fun getFragment(): Int {
        return R.layout.fragmentlayout
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {


        // get view

        val view = inflater?.inflate(getFragment(), container, false)

        val btn = view?.findViewById<FloatingActionButton>(R.id.new_item)

        btn?.setOnClickListener{

            val items = arrayOf("Note","Todo")

            val builder = AlertDialog.Builder(this@FragmentItem.context ).setTitle("Choose")
                .setItems(items,{_,which -> when(which) {
                    0 -> {openCreateNote()}
                    1 -> {openCreateTodo()}
                    else -> { println("None selected")}
                }
            })

            builder.show()

        }

        return view
    }


    private fun openCreateNote(){

        val intent = Intent(context, noteActivity::class.java)
        val data = Bundle()
        data.putInt(MODE.EXTRA_KEYS, MODE.CREATE.mode)
        intent.putExtras(data)
        startActivityForResult(intent, NOTE_REQUEST)
    }

    private fun openCreateTodo(){

        val date = Date(System.currentTimeMillis())
        val dateFormat = SimpleDateFormat("MMM dd YYYY", Locale.ENGLISH)
        val timeformat = SimpleDateFormat("MM:HH", Locale.ENGLISH)

        val intent = Intent(context, todoActivity::class.java)
        val data = Bundle()
        data.putInt(MODE.EXTRA_KEYS, MODE.CREATE.mode)
        data.putString(todoActivity.date, dateFormat.format(date))
        data.putString(todoActivity.time, timeformat.format(date))

        intent.putExtras(data)
        startActivityForResult(intent, TODO_REQUEST)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode){

            TODO_REQUEST -> {

                if (resultCode == Activity.RESULT_OK){
                    Log.v("Reminder", "TODO activity created")
                }else {
                    Log.v("Reminder", "TODO activity not created")
                }

            }

            NOTE_REQUEST -> {

                if (requestCode == Activity.RESULT_OK){

                    Log.v("Reminder", "Note activity created")
                } else {
                    Log.v("Reminder", "Note activity not created")
                }

            }

        }
    }

}


