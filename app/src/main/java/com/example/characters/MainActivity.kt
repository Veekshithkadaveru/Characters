package com.example.characters

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.characters.ui.theme.CharactersTheme
import com.example.characters.view.CharacterDetails
import com.example.characters.view.CharactersBottomNav
import com.example.characters.view.CollectionScreen
import com.example.characters.view.LibraryScreen
import com.example.characters.viewmodel.LibraryApiViewModel
import dagger.hilt.android.AndroidEntryPoint

sealed class Destination(val route: String) {
    data object Library : Destination("library")
    data object Collection : Destination("collection")
    data object CharacterDetail : Destination("character/{characterId}") {
        fun createRoute(characterId: Int?) = "character/$characterId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val lvm by viewModels<LibraryApiViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CharactersTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    CharactersScaffold(navController = navController, lvm, paddingValues = PaddingValues())
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharactersScaffold(navController: NavHostController, lvm: LibraryApiViewModel,paddingValues:PaddingValues) {
    val snackbarHostState = remember { SnackbarHostState() }
    val ctx = LocalContext.current
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        bottomBar = { CharactersBottomNav(navController = navController) }

    ) {
        NavHost(navController = navController, startDestination = Destination.Library.route) {
            composable(Destination.Library.route) {
                LibraryScreen(navController, lvm, paddingValues)
            }
            composable(Destination.Collection.route) {
                CollectionScreen()
            }
            composable(Destination.CharacterDetail.route) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString("characterId")?.toIntOrNull()
                if (id == null)
                    Toast.makeText(ctx, "Character id is required", Toast.LENGTH_SHORT).show()
                else {
                    lvm.retrieveSingleCharacter(id)
                    CharacterDetails(
                        lvm = lvm,
                        paddingValues = paddingValues,
                        navController = navController
                    )
                }
            }
        }
    }
}
