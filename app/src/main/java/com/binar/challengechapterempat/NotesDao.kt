package com.binar.challengechapterempat

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NotesDao {
    @Insert
    fun insertNote(note: Notes) :Long

    @Query("SELECT *  FROM Notes")
    fun getAllNote(): List<Notes>

    @Delete
    fun deleteNotes(note: Notes): Int

    @Update
    fun updateNotes(note: Notes): Int

    @Query("DELETE FROM Notes")
    fun deleteAllNote()



}