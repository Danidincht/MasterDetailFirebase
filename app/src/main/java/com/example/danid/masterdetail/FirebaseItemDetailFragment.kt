package com.example.danid.masterdetail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.danid.masterdetail.dummy.DummyContent
import com.example.danid.masterdetail.dummy.FirebaseItemList
import kotlinx.android.synthetic.main.activity_firebaseitem_detail.*
import kotlinx.android.synthetic.main.firebaseitem_detail.view.*

/**
 * A fragment representing a single FirebaseItem detail screen.
 * This fragment is either contained in a [FirebaseItemListActivity]
 * in two-pane mode (on tablets) or a [FirebaseItemDetailActivity]
 * on handsets.
 */
class FirebaseItemDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var mItem: FirebaseItemList.FirebaseItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println("Detail frag")


        if (arguments.containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = FirebaseItemList.ITEM_MAP[arguments.getString(ARG_ITEM_ID)]
            println(mItem?.descripcion + " " + arguments.getString(ARG_ITEM_ID))
            mItem?.let {
                activity.toolbar_layout?.title = it.descripcion
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.firebaseitem_detail, container, false)

        // Show the dummy content as text in a TextView.
        mItem?.let {
            rootView.firebaseitem_detail.text = it.descripcion
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}
