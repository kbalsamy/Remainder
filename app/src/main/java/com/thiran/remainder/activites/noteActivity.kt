package com.thiran.remainder.activites

import com.thiran.remainder.R


class noteActivity:itemActivity() {

    override val tag: String
        get() = "Note Activity"

    override fun getLayout(): Int {
        return R.layout.noteslayout
    }

    override fun setTitle(): Int {
        return R.string.app_name
    }
}