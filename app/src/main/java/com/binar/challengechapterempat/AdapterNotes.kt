package com.binar.challengechapterempat

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.recreate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_note.view.*
import kotlinx.android.synthetic.main.delete_dialog.view.*
import kotlinx.android.synthetic.main.edit_dialog.view.*
import kotlinx.android.synthetic.main.fragment_logout_dialog.view.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.threeten.bp.LocalDateTime;

data class AdapterNotes(private val onclick : (Notes)->Unit): RecyclerView.Adapter<AdapterNotes.ViewHolder>(){
    var datanote : List<Notes>? = null


    fun setData(note : List<Notes>){
        this.datanote = note
    }






    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewitem = LayoutInflater.from(parent.context).inflate(R.layout.adapter_note,parent,false)

        return  ViewHolder(viewitem)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        holder.itemView.notejudul.text = datanote!![position].judul

            holder.itemView.notetext.text = datanote!![position].time


        holder.itemView.card.setOnClickListener {
            onclick(datanote!![position])

        }
    }

    override fun getItemCount(): Int {
        if (datanote == null){
            return 0
        }else{
            return datanote!!.size
        }
    }
}

