package com.thiran.remainder.activites.model

enum class MODE (val mode:Int){

    CREATE(0),
    EDIT(1),
    VIEW(2);

    companion object {

        val EXTRA_KEYS = "MODE"

        fun getValue(value:Int):MODE{

            values().forEach {

                item -> if (item.mode == value){

                return item

                }
            }
            return VIEW
        }

    }

}