package com.thiran.remainder.activites.navigation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.LinearLayout
import com.thiran.remainder.R

class navigationAdapter (val ctx : Context, val items :List<navigationItem> ):BaseAdapter(){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater = LayoutInflater.from(ctx)

        var view = convertView

        if (view == null) {

            view = inflater.inflate(R.layout.navigationlayout, null) as LinearLayout

        }

        val item = items[position]
        val title = view.findViewById<Button>(R.id.drawer_button)

        title.text = item.title
        title.setOnClickListener{
            item.onclick.run()
        }


        return view
    }

    override fun getItem(position: Int): Any {

        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return 0L
    }

    override fun getCount(): Int {
        return items.size
    }
}

