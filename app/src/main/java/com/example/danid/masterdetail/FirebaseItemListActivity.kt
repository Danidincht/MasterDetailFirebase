package com.example.danid.masterdetail

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.danid.masterdetail.dummy.DummyContent
import com.example.danid.masterdetail.dummy.FirebaseItemList
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_firebaseitem_list.*
import kotlinx.android.synthetic.main.firebaseitem_list_content.view.*

import kotlinx.android.synthetic.main.firebaseitem_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [FirebaseItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class FirebaseItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var mTwoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebaseitem_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        if (firebaseitem_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true
        }


        var database = FirebaseDatabase.getInstance()
        var dbRef: DatabaseReference = database.getReference();
        dbRef.orderByKey().addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                println("Error reading db")
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                val items = dataSnapshot!!.children.iterator()
                val list = FirebaseItemList
                var i=0
                while(items.hasNext())
                {
                    val item = items.next()
                    list.addItem(FirebaseItemList.FirebaseItem(i++.toString(), item.key, item.value.toString()))

                    print(item.key)
                    println(item.value)
                }
                setupRecyclerView(firebaseitem_list)
            }
        })
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, FirebaseItemList.ITEMS, mTwoPane)
        println("inicializado recycler")
    }

    class SimpleItemRecyclerViewAdapter(private val mParentActivity: FirebaseItemListActivity,
                                        private val mValues: List<FirebaseItemList.FirebaseItem>,
                                        private val mTwoPane: Boolean) :
            RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val mOnClickListener: View.OnClickListener

        init {
            mOnClickListener = View.OnClickListener { v ->
                val item = v.tag as FirebaseItemList.FirebaseItem
                if (mTwoPane) {
                    val fragment = FirebaseItemDetailFragment().apply {
                        arguments = Bundle()
                        println("ID: " + item.id)
                        arguments.putString(FirebaseItemDetailFragment.ARG_ITEM_ID, item.id)
                    }
                    mParentActivity.supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.firebaseitem_detail_container, fragment)
                            .commit()
                } else {
                    val intent = Intent(v.context, FirebaseItemDetailActivity::class.java).apply {
                        putExtra(FirebaseItemDetailFragment.ARG_ITEM_ID, item.id)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.firebaseitem_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = mValues[position]
            holder.mIdView.text = item.id
            holder.mContentView.text = item.nombre

            with(holder.itemView) {
                tag = item
                setOnClickListener(mOnClickListener)
            }
        }

        override fun getItemCount(): Int {
            return mValues.size
        }

        inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
            val mIdView: TextView = mView.id_text
            val mContentView: TextView = mView.content
        }
    }
}
