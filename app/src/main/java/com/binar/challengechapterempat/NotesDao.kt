package com.binar.challengechapterempat

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NotesDao {
    @Insert
    fun insertNote(note: Notes) :Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun registerUser(user: User):Long

    @Query("SELECT *  FROM Notes")
    fun getAllNote(): List<Notes>

    @Delete
    fun deleteNotes(note: Notes):Int

    @Update
    fun updateNotes(note: Notes): Int

    @Delete()
    fun deleteacc(user : User):Int

    @Query("SELECT * FROM Notes WHERE Notes.email = :email")
    fun getNoteAcc(email: String) : List<Notes>

    @Query("DELETE FROM Notes WHERE Notes.id = :id")
    fun deleteNoteCustom(id: String) : Int

    @Query("SELECT * FROM User WHERE User.email = :email")
    fun getUserRegis(email:String): List<User>

    @Query("SELECT * FROM User WHERE User.email = :email")
    fun getUser(email:String): User




}