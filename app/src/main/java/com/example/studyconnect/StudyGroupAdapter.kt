package com.example.studyconnect

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudyGroupAdapter(private val context: Context, private val studyGroups: List<StudyGroup>) :
    RecyclerView.Adapter<StudyGroupAdapter.ViewHolder>() {

    private lateinit var listener: OnItemClickListener


    // Define an interface for the item click listener
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // Set the listener from outside the adapter (in your fragment or activity)
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.study_group_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val studyGroupEntry = studyGroups[position]
        holder.bind(studyGroupEntry)
    }

    override fun getItemCount(): Int = studyGroups.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val groupNameTV = itemView.findViewById<TextView>(R.id.studyGroupNameTV)
        private val classNameTV = itemView.findViewById<TextView>(R.id.classNameTV)


        // helper method to help set up the onBindViewHolder method
        fun bind(studyGroup: StudyGroup) {
            groupNameTV.text = studyGroup.groupName
            classNameTV.text = studyGroup.subject

            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }

        /* use this for on button click
        // TODO: Navigate to Details screen and pass selected article
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(ARTICLE_EXTRA, article)
        context.startActivity(intent)
        */
        override fun onClick(v: View?) {
            // TODO: Get selected article

        }
    }
}


