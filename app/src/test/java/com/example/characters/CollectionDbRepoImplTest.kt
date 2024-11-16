package com.example.characters

import com.example.characters.db.CharacterDao
import com.example.characters.db.CollectionsDbRepoImpl
import com.example.characters.db.DbCharacter
import com.example.characters.db.DbNote
import com.example.characters.db.NoteDao
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class CollectionDbRepoImplTest {

    private lateinit var repo: CollectionsDbRepoImpl
    private val mockCharacterDao: CharacterDao = mockk(relaxed = true)
    private val mockNoteDao: NoteDao = mockk(relaxed = true)

    @Before
    fun setUp() {
        repo = CollectionsDbRepoImpl(mockCharacterDao, mockNoteDao)
    }

    @Test
    fun `getCharactersFromRepo should return list of characters`() = runTest {
        val expectedCharacters = listOf(
            DbCharacter(
                1,
                101,
                "Mock character",
                "mock_thumbnail.jpg",
                "comic 1",
                "This is mock description",
            )
        )
        DbCharacter(
            1,
            102,
            "Different Mock character",
            "mock_thumbnail.jpg",
            "comic 2",
            "This is different mock description",
        )
        every { mockCharacterDao.getCharacters() } returns flowOf(expectedCharacters)
        val result = repo.getCharactersFromRepo()

        assertEquals(expectedCharacters, result.first())
        verify(exactly = 1) { mockCharacterDao.getCharacters() }
    }

    @Test
    fun `addCharacterToRepo should call addCharacter on characterDao`() = runTest {

        val character = DbCharacter(
            3,
            103,
            "AnotherMock character",
            "mock_thumbnail.jpg",
            "comic 3",
            "This is another mock description",
        )

        repo.addCharacterToRepo(character)
        verify(exactly = 1) { mockCharacterDao.addCharacter(character) }

    }

    @Test
    fun `getAllNotes should return list of notes`()= runTest {
        val expectedNotes= listOf(DbNote(1,1,"Note 1","This is Mock Note"))
        every { mockNoteDao.getAllNotes() } returns flowOf(expectedNotes)

        val result=repo.getAllNotes()

        assertEquals(expectedNotes,result.first())
        verify(exactly = 1) { mockNoteDao.getAllNotes() }
    }

    @Test
    fun `deleteNoteFromRepo should call deleteNote on NoteDao`()= runTest {
        val note=DbNote(1,1,"Note 1","This is Mock Note")

        repo.deleteNoteFromRepo(note)
        verify(exactly = 1) { mockNoteDao.deleteNote(note) }
    }


}