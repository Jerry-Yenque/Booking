package com.froxengine.booking.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.froxengine.booking.presentation.screens.home.components.SportCenterItem
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.froxengine.booking.data.model.SportCenter

// "modifier" and should appear as the first optional parameter in the function's parameter list.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxSize(), // Hace que el Box ocupe todo el TopAppBar
                        contentAlignment = Alignment.Center // Centra el contenido dentro del Box
                    ) {
                        Text(
                            text = "CENTROS DEPORTIVOS DISPONIBLES",
                            color = MaterialTheme.colorScheme.onSecondaryContainer, // Color del texto
                            style = MaterialTheme.typography.titleMedium, // Estilo del texto
                            textAlign = TextAlign.Center, // Asegura que el texto está centrado
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.secondaryContainer)
            )
        },
//        bottomBar = {
//            BottomAppBar(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.Transparent),
//                containerColor = Color.Transparent,
//                contentColor = Color.Unspecified,
//                tonalElevation = 0.dp
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color.Transparent),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Versión 1.0.0",
//                        style = MaterialTheme.typography.bodySmall,
//                        modifier = Modifier.padding(start = 16.dp)
//                    )
//                    Text(
//                        text = "Powered by: Grupo j",
//                        style = MaterialTheme.typography.bodySmall,
//                        modifier = Modifier.padding(end = 16.dp)
//                    )
//                }
//            }
//        }
    ) { innerPadding ->
        HomeContent(homeViewModel , navController, homeViewModel.sportCenter, Modifier.padding(innerPadding))
    }
}

@Composable
fun HomeContent(homeViewModel: HomeViewModel, navController: NavHostController, sportCenters: List<SportCenter>, modifier: Modifier) {
    Box(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .zIndex(1f)
                .background(Color.Transparent), //.padding(bottom = 40.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "v1.0.0",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                text = "Powered by Team J",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(end = 16.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            sportCenters.chunked(2).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    rowItems.forEach { center ->
                        SportCenterItem(homeViewModel= homeViewModel, navController = navController, center = center, modifier = Modifier.weight(1f))
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun HomeScreenPreview() {
//    BooKingTheme(darkTheme = false) {
//        HomeScreen()
//    }
//}