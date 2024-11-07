package com.froxengine.booking.presentation.screens.home.components

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.froxengine.booking.data.model.SportCenter
import com.froxengine.booking.presentation.screens.home.HomeViewModel
import com.froxengine.booking.presentation.ui.theme.BooKingTheme

@Composable
fun SportCenterItem(homeViewModel: HomeViewModel, navController: NavHostController, center: SportCenter, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.wrapContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Image(
//                    painter = painterResource(id = 0),
//                    contentDescription = center.name,
//                    modifier = Modifier
//                        .size(150.dp)
//                )
                Base64Image(sportCenter = center)
//                Text(
//                    text = center.name,
//                    modifier = Modifier
////                        .fillMaxWidth()
////                        .wrapContentSize()
//                        .padding(8.dp),
//                    style = MaterialTheme.typography.bodyMedium,
//                    textAlign = TextAlign.Center
//                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                homeViewModel.sportCenterSelected(center)
                navController.navigate("detail")
                      },
            modifier = Modifier
                .clip(RoundedCornerShape(50))
        ) {
            Text(text = "MAS INFORMACIÓN")
        }
    }
}

@Composable
fun Base64Image(sportCenter: SportCenter) {
    // Decodificar la cadena Base64 en un array de bytes
    val imageBytes = Base64.decode(sportCenter.image, Base64.DEFAULT)

    // Convertir los bytes a un Bitmap
    val bitmap: Bitmap? = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)

    // Mostrar la imagen si el bitmap no es nulo
    bitmap?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = sportCenter.description,
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(8.dp))
        )
    }
}


@Preview
@Composable
fun SportCenterItemPreview() {
    BooKingTheme {
//        SportCenterItem(SportCenter("Center 1", "R.drawable.center_1"))
    }
}