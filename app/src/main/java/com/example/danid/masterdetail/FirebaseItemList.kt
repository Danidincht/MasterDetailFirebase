package com.example.danid.masterdetail.dummy

import com.google.firebase.database.*
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object FirebaseItemList {

    var nombre = ""
    var descripcion = ""
    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<FirebaseItem> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, FirebaseItem> = HashMap()

    private val COUNT = 25

    public fun addItem(item: FirebaseItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class FirebaseItem(val id: String, val nombre: String, val descripcion: String) {
        override fun toString(): String = descripcion
    }
}
