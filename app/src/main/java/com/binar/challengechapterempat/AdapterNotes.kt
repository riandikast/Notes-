package com.binar.challengechapterempat

import android.app.Activity
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

data class AdapterNotes(var listdata: List<Notes>): RecyclerView.Adapter<AdapterNotes.ViewHolder>(){
    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewitem = LayoutInflater.from(parent.context).inflate(R.layout.adapter_note,parent,false)

        return  ViewHolder(viewitem)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        holder.itemView.notejudul.text = listdata[position].judul
        holder.itemView.notetext.text = listdata[position].isi

        holder.itemView.notedelete.setOnClickListener {
            db = NotesDB.getInstance(it.context)
            val custom = LayoutInflater.from(it.context).inflate(R.layout.delete_dialog, null)
            val a = androidx.appcompat.app.AlertDialog.Builder(it.context)
                .setView(custom)
                .create()

            custom.btnhapustidak.setOnClickListener {
                a.dismiss()
            }

            custom.btnhapusya.setOnClickListener {



                GlobalScope.async {
                    val hasil =  db?.NotesDao()?.deleteNotes(listdata[position])
                    (holder.itemView.context as MainActivity).runOnUiThread {
                        if (hasil !=0){
                            Toast.makeText(it.context, "Catatan dengan judul ${listdata[position].judul} berhasil di hapus", Toast.LENGTH_LONG).show()
                            (custom.context as MainActivity).recreate()
                        }else{
                            Toast.makeText(it.context, "Catatan dengan judul ${listdata[position].judul} gagal di hapus", Toast.LENGTH_LONG).show()
                        }

                        (holder.itemView.context as MainActivity).getdata()

                    }
                }
              a.dismiss()

            }
            a.show()

        }

        holder.itemView.noteedit.setOnClickListener {
            db = NotesDB.getInstance(it.context)
            val custom = LayoutInflater.from(it.context).inflate(R.layout.edit_dialog, null)
            val a = androidx.appcompat.app.AlertDialog.Builder(it.context)
                .setView(custom)
                .create()

            var judul = listdata[position].judul
            var isi = listdata[position].isi

            custom.editjudul.setText(judul)
            custom.editcatatan.setText(isi)

            custom.editjudul.setSelection(custom.editjudul.length())
            custom.editcatatan.setSelection(custom.editcatatan.length())

            custom.btnedit.setOnClickListener {
                var id = listdata[position].id
                judul = custom.editjudul.text.toString()
                isi = custom.editcatatan.text.toString()



                GlobalScope.async {
                    val hasil =  db?.NotesDao()?.updateNotes(Notes(id,judul,isi))

                    (holder.itemView.context as MainActivity).runOnUiThread {
                        if (hasil !=0){
                            Toast.makeText(it.context, "Data ${listdata[position].judul} berhasil di update", Toast.LENGTH_LONG).show()
                            (custom.context as MainActivity).recreate()
                        }else{
                            Toast.makeText(it.context, "Data ${listdata[position].judul} gagal di update", Toast.LENGTH_LONG).show()
                        }

                        (holder.itemView.context as MainActivity).getdata()








                    }
                }



                a.dismiss()

            }
            a.show()

        }
    }

    override fun getItemCount(): Int {

        return listdata.size
    }
}

