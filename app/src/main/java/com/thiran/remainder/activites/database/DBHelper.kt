package com.thiran.remainder.activites.database

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.thiran.remainder.activites.Remainder

class DBHelper(val dbname:String, val version:Int):SQLiteOpenHelper(Remainder.ctx, dbname, null, version) {

    companion object {

        val ID: String = "_id"
        val TABLE_TODOS = "todos"
        val TABLE_NOTES = "notes"
        val COLUMN_TITLE: String = "title"
        val COLUMN_MESSAGE: String = "message"
        val COLUMN_LOCATION: String = "location"
        val COLUMN_LOCATION_LATITUDE = "latitude"
        val COLUMN_LOCATION_LONGITUDE = "longitude"
        val COLUMN_SCHEDULED: String = "scheduled"
    }

    private val createTableNotes = """ CREATE TABLE if not exists $TABLE_NOTES (

        $ID integer PRIMARY KEY autoincrement,
        $COLUMN_TITLE text,
        $COLUMN_MESSAGE text,
        $COLUMN_LOCATION_LATITUDE real,
        $COLUMN_LOCATION_LONGITUDE real )"""

    private val createTableTodos = """ CREATE TABLE if not exists $TABLE_TODOS (

        $ID integer PRIMARY KEY autoincrement,
        $COLUMN_TITLE text,
        $COLUMN_MESSAGE text,
        $COLUMN_SCHEDULED integer,
        $COLUMN_LOCATION_LATITUDE real,
        $COLUMN_LOCATION_LONGITUDE real)"""




    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL(createTableNotes)
        db?.execSQL(createTableTodos)
        Log.v("Db", "Database for Notes and Todos created")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}