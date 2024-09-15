package com.example.characters

import android.content.Context
import androidx.room.Room
import com.example.characters.db.CharacterDao
import com.example.characters.db.CollectionDb
import com.example.characters.db.CollectionDbRepo
import com.example.characters.db.CollectionsDbRepoImpl
import com.example.characters.db.Constants.DB
import com.example.characters.db.NoteDao
import com.example.characters.model.api.ApiService
import com.example.characters.model.api.MarvelApiRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideApiRepo() = MarvelApiRepo(ApiService.api)

    @Provides
    fun provideCollectionDb(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, CollectionDb::class.java, DB).build()

    @Provides
    fun provideCharacterDao(collectionDb: CollectionDb) = collectionDb.characterDao()

    @Provides
    fun provideNoteDao(collectionDb: CollectionDb) = collectionDb.noteDao()

    @Provides
    fun provideDbRepoImpl(characterDao: CharacterDao, noteDao: NoteDao): CollectionDbRepo =
        CollectionsDbRepoImpl(characterDao, noteDao)


}