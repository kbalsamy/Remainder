package com.thiran.remainder.activites.database

import android.content.ContentValues
import android.location.Location
import android.util.Log
import com.thiran.remainder.activites.model.Note
import com.thiran.remainder.activites.model.Todo
import java.lang.StringBuilder

object DB {

    private val version = 1
    private val name = "Remainder"

    val NOTE = object :Crud<Note>{

        override fun insert(what: Note): Long {

            val inserted = insert(listOf(what))
            if (!inserted.isEmpty()){
                return inserted[0]
            }
            return 0
        }


        override fun insert(what: Collection<Note>): List<Long> {

            val db = DBHelper(name, version).writableDatabase
            db.beginTransaction()
            var inserted = 0
            val items = mutableListOf<Long>()
            what.forEach { item ->
                val values = ContentValues()
                values.put(DBHelper.COLUMN_TITLE, item.title)
                values.put(DBHelper.COLUMN_MESSAGE, item.content)
                values.put(DBHelper.COLUMN_LOCATION_LATITUDE, item.location.latitude)
                values.put(DBHelper.COLUMN_LOCATION_LONGITUDE, item.location.longitude)

                val id = db.insert(DBHelper.TABLE_NOTES,null,values)
                if (id>0) {
                    items.add(id)
                    Log.v("db", "Entry ID assigned [$id]")
                    inserted++
                }
            }

            val success = inserted == what.size
            if (success){

                db.setTransactionSuccessful()
            }
            else {
                items.clear()
            }
            db.endTransaction()
            db.close()
            return items
        }

        override fun update(what: Note): Int = update(listOf(what))


        override fun update(what: Collection<Note>): Int {

            val db = DBHelper(name, version).writableDatabase
            db.beginTransaction()

            var updated = 0

            what.forEach { item ->

                val values = ContentValues()
                val table = DBHelper.TABLE_NOTES
                values.put(DBHelper.COLUMN_TITLE, item.title)
                values.put(DBHelper.COLUMN_MESSAGE, item.content)
                values.put(DBHelper.COLUMN_LOCATION_LATITUDE, item.location.latitude)
                values.put(DBHelper.COLUMN_LOCATION_LONGITUDE, item.location.longitude)

                db.update(table, values, "_id = ?", arrayOf(item.id.toString()))
                updated++
            }

                val result = updated == what.size

                if (result){

                    db.setTransactionSuccessful()
                }
                else{
                    updated = 0
                }



            db.endTransaction()
            db.close()
            return updated

        }

        override fun delete(what: Note): Int = delete(listOf(what))

        override fun delete(what: Collection<Note>): Int {

            val db = DBHelper(name, version).writableDatabase
            db.beginTransaction()

            val ids = StringBuilder()
            what.forEachIndexed{ index, note ->

                ids.append(note.id.toString())

                if (index < what.size -1){

                    ids.append(", ")
                }
            }

            val table = DBHelper.TABLE_NOTES
            val statement = db.compileStatement("DELETE FROM $table WHERE ${DBHelper.ID} in ($ids);")

            val count = statement.executeUpdateDelete()
            val success = count>0

            if (success){

                db.setTransactionSuccessful()
            }
            else{
                Log.v("Db", "Error in deleting record")
            }

            db.endTransaction()
            db.close()
            return count

        }

        override fun select(args: Pair<String, String>): List<Note> = select(listOf(args))

        override fun select(args: Collection<Pair<String, String>>): List<Note> {
            val db = DBHelper(name, version).writableDatabase
            val selection = StringBuilder()
            val selectionArgs = mutableListOf<String>()

            args.forEach { arg ->

                selection.append("${arg.first} ==?")
                selectionArgs.add(arg.second)
            }

            val result = mutableListOf<Note>()

            val cursor = db.query(true,DBHelper.TABLE_NOTES, null, selection.toString(), selectionArgs.toTypedArray(), null, null, null, null )

            while (cursor.moveToNext()){

                val id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TITLE))
                val message = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MESSAGE))
                val latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LOCATION_LATITUDE))
                val longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LOCATION_LONGITUDE))

                val location = Location("")

                location.latitude = latitude
                location.longitude = longitude

                val note = Note(title, message, location)
                note.id = id
                result.add(note)
            }

            cursor.close()
            return result
        }



        override fun selectAll(): List<Note> {
            val db = DBHelper(name, version).writableDatabase
            val result = mutableListOf<Note>()
            val cursor = db.query(true,DBHelper.TABLE_NOTES, null, null,null,null,null,null,null)

            while (cursor.moveToNext()) {

                val id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TITLE))
                val message = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MESSAGE))
                val latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LOCATION_LATITUDE))
                val longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LOCATION_LONGITUDE))

                val location = Location("")

                location.latitude = latitude
                location.longitude = longitude
                val note = Note(title, message, location)
                note.id = id
                result.add(note)
            }

            cursor.close()
            return result
        }
    }

    val TODO = object : Crud<Todo> {

        override fun insert(what: Todo): Long {

            val inserted = insert(listOf(what))
            if (!inserted.isEmpty()) {return inserted[0]}
            return 0
        }

        override fun insert(what: Collection<Todo>): List<Long> {

            val db = DBHelper(name, version).writableDatabase
            db.beginTransaction()

            var inserted = 0
            val items = mutableListOf<Long>()

            what.forEach { item ->

                val values = ContentValues()

                values.put(DBHelper.COLUMN_TITLE, item.title)
                values.put(DBHelper.COLUMN_MESSAGE, item.content)
                values.put(DBHelper.COLUMN_SCHEDULED, item.scheduledfor)
                values.put(DBHelper.COLUMN_LOCATION_LATITUDE, item.location.latitude)
                values.put(DBHelper.COLUMN_LOCATION_LONGITUDE, item.location.longitude)

                val id = db.insert(DBHelper.TABLE_TODOS, null, values)
                if (id>0){
                    items.add(id)
                    inserted++
                }
            }

            val success = inserted == what.size
            if (success) {
                db.setTransactionSuccessful()
            }else{
                items.clear()
            }

            db.endTransaction()
            db.close()
            return items
        }

        override fun update(what: Todo): Int = update(listOf(what))

        override fun update(what: Collection<Todo>): Int {

            val db = DBHelper(name, version).writableDatabase
            db.beginTransaction()

            var updated = 0

            what.forEach { item ->

                val values = ContentValues()
                val table = DBHelper.TABLE_TODOS
                values.put(DBHelper.COLUMN_TITLE, item.title)
                values.put(DBHelper.COLUMN_MESSAGE, item.content)
                values.put(DBHelper.COLUMN_SCHEDULED, item.scheduledfor)
                values.put(DBHelper.COLUMN_LOCATION_LATITUDE, item.location.latitude)
                values.put(DBHelper.COLUMN_LOCATION_LONGITUDE, item.location.longitude)

                db.update(table, values, "_id = ?", arrayOf(item.id.toString()))
                updated++
            }

            val result = updated == what.size

            if (result){

                db.setTransactionSuccessful()
            }
            else{
                updated = 0
            }



            db.endTransaction()
            db.close()
            return updated
        }

        override fun delete(what: Todo): Int = delete(listOf(what))

        override fun delete(what: Collection<Todo>): Int {

            val db = DBHelper(name, version).writableDatabase
            db.beginTransaction()

            val ids = StringBuilder()
            what.forEachIndexed{ index, todo ->

                ids.append(todo.id.toString())

                if (index < what.size -1){

                    ids.append(", ")
                }
            }

            val table = DBHelper.TABLE_TODOS
            val statement = db.compileStatement("DELETE FROM $table WHERE ${DBHelper.ID} in ($ids);")

            val count = statement.executeUpdateDelete()
            val success = count>0

            if (success){

                db.setTransactionSuccessful()
            }
            else{
                Log.v("Db", "Error in deleting record")
            }

            db.endTransaction()
            db.close()
            return count


        }

        override fun select(args: Pair<String, String>): List<Todo>  = select(listOf(args))

        override fun select(args: Collection<Pair<String, String>>): List<Todo> {

            val db = DBHelper(name, version).writableDatabase

            val selection = StringBuilder()
            val selectionArgs = mutableListOf<String>()

             args.forEach { arg ->

                 selection.append("${arg.first}")
                 selectionArgs.add(arg.second)
             }

            val result = mutableListOf<Todo>()

            val cursor = db.query(true,DBHelper.TABLE_TODOS,null,selection.toString(),selectionArgs.toTypedArray(),null,null, null,null)

            while (cursor.moveToNext()){

                val id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TITLE))
                val message = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MESSAGE))
                val scheduled= cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SCHEDULED))
                val latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LOCATION_LATITUDE))
                val longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LOCATION_LONGITUDE))

                val location = Location("")
                location.latitude = latitude
                location.longitude = longitude

                val todo = Todo(title, message, location, scheduled)
                todo.id = id

                result.add(todo)

            }
            cursor.close()
            return result
        }

        override fun selectAll(): List<Todo> {

            val db = DBHelper(name, version).writableDatabase


            val result = mutableListOf<Todo>()

            val cursor = db.query(true,DBHelper.TABLE_TODOS,null,null,null,null,null, null,null)

            while (cursor.moveToNext()){

                val id = cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_TITLE))
                val message = cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MESSAGE))
                val scheduled= cursor.getLong(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_SCHEDULED))
                val latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LOCATION_LATITUDE))
                val longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_LOCATION_LONGITUDE))

                val location = Location("")
                location.latitude = latitude
                location.longitude = longitude

                val todo = Todo(title, message, location, scheduled)
                todo.id = id

                result.add(todo)

            }
            cursor.close()
            return result


        }
    }
}