package com.binar.challengechapterempat


import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

var db : NotesDB?=null

class MainActivity : AppCompatActivity() {

    lateinit var home : SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = NotesDB.getInstance(this)



}
    fun getdata(){
        list?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        GlobalScope.launch {
            val listdata = db?.NotesDao()?.getAllNote()

            runOnUiThread {
                listdata.let {
                    val adapt = AdapterNotes(){}
                    list.adapter = adapt
                }
            }
        }
    }


}