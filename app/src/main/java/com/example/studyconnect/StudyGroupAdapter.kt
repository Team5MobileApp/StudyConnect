package com.example.studyconnect

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudyGroupAdapter(private val context: Context, private val studyGroups: List<StudyGroup>) :
    RecyclerView.Adapter<StudyGroupAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.study_group_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val studyGroupEntry = studyGroups[position]
        holder.bind(studyGroupEntry)
    }

    override fun getItemCount() = studyGroups.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val groupNameTV = itemView.findViewById<TextView>(R.id.studyGroupNameTV)
        private val classNameTV = itemView.findViewById<TextView>(R.id.classNameTV)

        init {
            itemView.setOnClickListener(this)
        }

        // helper method to help set up the onBindViewHolder method
        fun bind(studyGroup: StudyGroup) {
            groupNameTV.text = studyGroup.groupName
            classNameTV.text = studyGroup.subject
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


