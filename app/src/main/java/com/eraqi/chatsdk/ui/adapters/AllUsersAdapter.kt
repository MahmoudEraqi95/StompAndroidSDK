package com.eraqi.chatsdk.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eraqi.chatsdk.R

class AllUsersAdapter(val context: Context, val users: ArrayList<String>,
                      val itemClick: ((String)->Unit)): RecyclerView.Adapter<AllUsersAdapter.ViewHolder>() {
    class ViewHolder( val view: View): RecyclerView.ViewHolder(view) {
        var userName: TextView = view.findViewById(R.id.tv_user)

        fun bindItem(item: String,  itemClick: ((String)->Any?)){
            userName.text = item
            view.setOnClickListener{
               itemClick(item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_all_users, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(users[position], itemClick)

    }

    override fun getItemCount(): Int {
        return users.size
    }
    fun addItems(newUsers: List<String>){
        users.addAll(newUsers)
    }
}
