package com.thiran.remainder.activites.database

interface Crud <T> where T :DBbase {
    //insert interfaces
    fun insert(what:T):Long
    fun insert(what:Collection<T>):List<Long>
    //update interfaces
    fun update(what: T):Int
    fun update(what: Collection<T>):Int
    //delete interfaces
    fun delete(what: T):Int
    fun delete(what: Collection<T>):Int
    //select interfaces
    fun select(args:Pair<String, String>):List<T>
    fun select(args:Collection<Pair<String, String>>):List<T>
    fun selectAll():List<T>

}