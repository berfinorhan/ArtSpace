package com.beecoding.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.beecoding.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                ArtSpaceApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtSpaceAppBar() {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .padding(bottom = 32.dp)
            .shadow(elevation = 8.dp),
        title = {
            Text(
                text = stringResource(R.string.art_space),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.darker_green),
            titleContentColor = Color.White
        )
    )
}

@Composable
fun ArtSpaceApp() {

    var currentPainting by remember { mutableIntStateOf(0) }
    val paintingList: List<Painting> = listOf(
        Painting(
            R.drawable.mountain_brook_default,
            R.string.mountain_brook,
            R.string.albert_bierstadt_1863,
            R.string.mountain_brook_painting
        ),
        Painting(
            R.drawable.fisherman_s_cottage_default,
            R.string.fisherman_s_cottage,
            R.string.harald_sohlberg_1906,
            R.string.fisherman_s_cottage_painting
        ),
        Painting(
            R.drawable.new_england_scenery_default,
            R.string.new_england_scenery,
            R.string.thomas_cole_1839,
            R.string.new_england_scenery_painting
        )
    )

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ArtSpaceAppBar()

        when (currentPainting) {
            0 -> Artwork(painting = paintingList[0])
            1 -> Artwork(painting = paintingList[1])
            2 -> Artwork(painting = paintingList[2])
        }

        ButtonGroup(
            prevCallback = {
                currentPainting = (currentPainting - 1 + paintingList.size) % paintingList.size
            },
            nextCallback = {
                currentPainting = (currentPainting + 1) % paintingList.size
            })
    }
}

@Composable
fun Artwork(modifier: Modifier = Modifier, painting: Painting) {
    ArtworkFrame(
        modifier = modifier,
        imageResource = painting.paintingImgResId,
        contentDesc = painting.contentDesc
    )
    ArtistInfoSection(
        modifier = modifier,
        paintingName = painting.paintingNameId,
        artistId = painting.artistId
    )
}

@Composable
fun ArtworkFrame(modifier: Modifier = Modifier, imageResource: Int, contentDesc: Int) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .padding(32.dp)
            .shadow(elevation = 16.dp, shape = ShapeDefaults.Small)
    ) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = stringResource(id = contentDesc)
        )
    }
}

@Composable
fun ArtistInfoSection(
    modifier: Modifier = Modifier,
    @StringRes paintingName: Int,
    @StringRes artistId: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(paintingName),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
        )
        Text(
            text = stringResource(artistId),
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic,
            fontFamily = FontFamily.Serif
        )
    }
}

@Composable
fun ButtonGroup(modifier: Modifier = Modifier, prevCallback: () -> Unit, nextCallback: () -> Unit) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        DefaultButton(modifier, stringResource(R.string.previous), prevCallback)
        DefaultButton(modifier, stringResource(R.string.next), nextCallback)
    }
}

@Composable
fun DefaultButton(modifier: Modifier = Modifier, text: String, callback: () -> Unit) {
    Button(
        onClick = callback,
        modifier = modifier.width(144.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = colorResource(R.color.dark_green)
        )
    ) {
        Text(text = text)
    }
}

data class Painting(
    val paintingImgResId: Int,
    val paintingNameId: Int,
    val artistId: Int,
    val contentDesc: Int
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}