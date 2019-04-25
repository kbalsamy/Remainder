package com.thiran.remainder.activites

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import android.widget.Toast
import com.thiran.remainder.R
import com.thiran.remainder.activites.fragments.FragmentItem
import com.thiran.remainder.activites.navigation.navigationAdapter
import com.thiran.remainder.activites.navigation.navigationItem
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity:BaseActivity(){

    override val tag: String
        get() = "Main View Activity"

    override fun getLayout(): Int = R.layout.activity_main

    override fun setTitle(): Int = R.string.app_name


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pager.adapter = ViewPageManager(supportFragmentManager)

        val menuItems = mutableListOf<navigationItem>()

        val today = navigationItem("today", Runnable {
            pager.setCurrentItem(0, true)
        })

        val next7days = navigationItem("Next7days", Runnable {
            pager.setCurrentItem(1, true)
        })

        val todos = navigationItem("Todos", Runnable {
            pager.setCurrentItem(2, true)

        })

        val notes = navigationItem("Notes", Runnable {
            pager.setCurrentItem(3, true)
        })

        menuItems.add(today)
        menuItems.add(next7days)
        menuItems.add(todos)
        menuItems.add(notes)

        val nagadapter = navigationAdapter(this, menuItems)
        left_drawer.adapter = nagadapter

    }

    private class ViewPageManager(manager:FragmentManager):FragmentStatePagerAdapter(manager){

        override fun getItem(p0: Int): Fragment {
            return FragmentItem()
        }

        override fun getCount(): Int {

            return 4

        }
    }




    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){
            R.id.drawable_menuID -> {
                drawer.openDrawer(GravityCompat.START)
                return true
            }

            R.id.option_menuID ->{

                return true
            }

            else -> return super.onOptionsItemSelected(item)

        }

    }

}