package com.rickandmorty.presentation.home

import android.net.Uri
import app.cash.turbine.test
import com.google.gson.Gson
import com.rickandmorty.data.datasource.remote.character.FakeCharacterRemoteDataSourceImpl
import com.rickandmorty.data.mappers.toCharacter
import com.rickandmorty.data.repository.FakeCharacterRepositoryImpl
import com.rickandmorty.data.repository.FakeLocationRepositoryImpl
import com.rickandmorty.domain.model.character.Character
import com.rickandmorty.domain.model.character.CharacterLocation
import com.rickandmorty.domain.model.character.Origin
import com.rickandmorty.domain.usecase.character.GetCharacterUseCase
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.junit.Assert.*
import com.rickandmorty.R
import com.rickandmorty.presentation.utils.CharacterGender

private const val FAKE_URL = "https://rickandmortyapi.com/api/character/38"

class HomeViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val dispatcher = UnconfinedTestDispatcher()

    private val fakeCharacterDataSource = FakeCharacterRemoteDataSourceImpl()
    private lateinit var fakeCharacterRepositoryImpl: FakeCharacterRepositoryImpl
    private lateinit var fakeLocationRepositoryImpl: FakeLocationRepositoryImpl

    @Mock
    private lateinit var useCase: GetCharacterUseCase

    private lateinit var viewModel: HomeViewModel
    
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        // Mock Uri.parse
        mockkStatic(Uri::class)
        every { Uri.parse(FAKE_URL).lastPathSegment } returns "38"

        // Mock Gson().toJson
        mockkStatic(Gson::class)
        every { Gson().toJson(fakeCharacterDataSource.characterList) } returns fakeCharacterDataSource.characterList.toCharacter().toString()

        fakeCharacterRepositoryImpl = FakeCharacterRepositoryImpl(fakeCharacterDataSource)
        fakeLocationRepositoryImpl = FakeLocationRepositoryImpl()

        useCase = GetCharacterUseCase(fakeCharacterRepositoryImpl)
        viewModel = HomeViewModel(useCase, fakeLocationRepositoryImpl, dispatcher)
    }

    @Test
    fun `extract character id, appropriate url, it should return last path segment`() {
        val expected = "38"

        assertEquals(expected, viewModel.extractCharacterId(FAKE_URL).toString())
    }

    @Test
    fun `set character gender, gender is male, it should return male drawable`() {
        val gender = CharacterGender.MALE
        val expected = R.drawable.male

        assertEquals(expected, viewModel.setCharacterGenderImage(gender))
    }

    @Test
    fun `set character gender, gender is female, it should return female drawable`() {
        val gender = CharacterGender.FEMALE
        val expected = R.drawable.female

        assertEquals(expected, viewModel.setCharacterGenderImage(gender))
    }

    @Test
    fun `set character gender, genderless, it should return genderless drawable`() {
        val gender = CharacterGender.GENDERLESS
        val expected = R.drawable.genderless

        assertEquals(expected, viewModel.setCharacterGenderImage(gender))
    }

    @Test
    fun `set character gender, gender is unknown, it should return unknown drawable`() {
        val gender = CharacterGender.UNKNOWN
        val expected = R.drawable.unknown

        assertEquals(expected, viewModel.setCharacterGenderImage(gender))
    }

    @Test
    fun `set character gender, mismatched gender name , it should return unknown drawable`() {
        val gender = ""
        val expected = R.drawable.unknown

        assertEquals(expected, viewModel.setCharacterGenderImage(gender))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get characters from network, no residents, it should return empty arraylist`() = runTest(dispatcher) {

        assertEquals(CharacterState.Loading, viewModel.characterState.value)

        viewModel.getCharacters(residents = arrayListOf())

        viewModel.characterState.test {
            assertEquals(CharacterState.Success(data = arrayListOf()), awaitItem())
            ensureAllEventsConsumed()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get characters from network, throw exception, it should return error`() = runTest(dispatcher) {
        fakeCharacterRepositoryImpl.setShouldReturnError(true)

        assertEquals(CharacterState.Loading, viewModel.characterState.value)

        viewModel.getCharacters(residents = arrayListOf(FAKE_URL))

        viewModel.characterState.test {
            assertEquals(CharacterState.Error(message = "Something went wrong. Please try again later."), awaitItem())
            ensureAllEventsConsumed()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `get characters from network, residents available, it should return characters`() = runTest(dispatcher) {
        val expectedCharacterData = arrayListOf(
            Character(
                id = 0,
                name = "fake name",
                status = "fake status",
                species = "fake species",
                type = "fake type",
                gender = "fake gender",
                origin = Origin(
                    name = "fake origin",
                    url = "fake url"
                ),
                location = CharacterLocation(
                    locationName = "fake location",
                    url = "fake url"
                ),
                image = "fake image",
                url = "fake url",
                episode = arrayListOf(),
                created = "fake created date"
            )
        )

        viewModel.getCharacters(residents = arrayListOf(FAKE_URL))

        viewModel.characterState.test {
            assertEquals(CharacterState.Loading, awaitItem())
            advanceTimeBy(1000)
            assertEquals(CharacterState.Success(data = expectedCharacterData), awaitItem())
            ensureAllEventsConsumed()
        }
    }
}
