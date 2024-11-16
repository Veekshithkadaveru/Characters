package com.example.characters

import app.cash.turbine.test
import com.example.characters.db.CollectionDbRepo
import com.example.characters.db.DbCharacter
import com.example.characters.model.CharacterResult
import com.example.characters.viewmodel.CollectionDbViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CollectionDbViewModelTest {

    private lateinit var viewModel: CollectionDbViewModel
    private val mockRepo: CollectionDbRepo = mockk(relaxed = true)

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = CollectionDbViewModel(mockRepo)
    }

    @Test
    fun `getCollection should update collection state`() = runTest {
        val mockCharacters = listOf(
            DbCharacter(1, 101, "Mock Character", "mock_thumbnail.jpg", "Comic 1", "Description")
        )
        coEvery { mockRepo.getCharactersFromRepo() } returns flowOf(mockCharacters)

        viewModel.getCollection()
        advanceUntilIdle()

        viewModel.collection.test {
            assertEquals(mockCharacters, awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `setCurrentCharacterId should update currentCharacter state`() = runTest {
        val mockCharacter = DbCharacter(1, 101, "Mock Character", "mock_thumbnail.jpg", "Comic 1", "Description")
        coEvery { mockRepo.getCharacterFromRepo(1) } returns flowOf(mockCharacter)

        viewModel.setCurrentCharacterId(1)
        advanceUntilIdle()

        viewModel.currentCharacter.test {
            assertEquals(mockCharacter, awaitItem())
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `addCharacter should call addCharacterToRepo`() = runTest {
        val characterResult = CharacterResult(
            id = 101,
            name = "Mock Character",
            thumbnail = null,
            description = "Description",
            comics = null,
            resourceURI = "Mock.com",
            urls = null
        )

        viewModel.addCharacter(characterResult)
        advanceUntilIdle()

        coVerify(exactly = 1) { mockRepo.addCharacterToRepo(DbCharacter.fromCharacter(characterResult)) }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}