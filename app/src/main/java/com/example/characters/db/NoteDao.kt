package com.example.characters.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM ${Constants.NOTE_TABLE} ORDER BY id")
    fun getAllNotes(): Flow<List<DbNote>>

    @Query("SELECT * FROM ${Constants.NOTE_TABLE} WHERE characterId=:characterId ORDER BY id ASC")
    fun getNotes(characterId: Int): Flow<List<DbNote>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addNotes(note: DbNote)

    @Update
    fun updateNote(note: DbNote)

    @Delete
    fun deleteNote(note: DbNote)

    @Query("DELETE FROM ${Constants.NOTE_TABLE} WHERE characterId=:characterId")
    fun deleteAllNotes(characterId: Int)
}