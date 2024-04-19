package com.mysteriouscoder.ultimatebrainbooster.ui.screens.soothingmusic

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.ads.MobileAds
import com.mysteriouscoder.ultimatebrainbooster.Heading
import com.mysteriouscoder.ultimatebrainbooster.OnImageCard
import com.mysteriouscoder.ultimatebrainbooster.R
import com.mysteriouscoder.ultimatebrainbooster.data.soothingmusiclist
import com.mysteriouscoder.ultimatebrainbooster.ads.BannerAds
import com.mysteriouscoder.ultimatebrainbooster.ui.theme.GreyBg


@Composable
fun SoothingMusic() {

    LazyColumn(
//        verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
    .background(GreyBg)
    .fillMaxSize()
    .padding(top = 10.dp)
    .padding(bottom = 20.dp)
    .padding(start = 10.dp)

    )
    {


        item {

            Heading(title = "Soothing Music",
                modifier=Modifier.fillMaxWidth()
            )

             Spacer(modifier = Modifier.height(5.dp))
            BannerAds(modifier = Modifier.fillMaxSize())
            Spacer(modifier = Modifier.height(5.dp))


        }

        items(soothingmusiclist.size){index->
            OnImageCard(
                title = soothingmusiclist[index].title,
                time= soothingmusiclist[index].time,
                image = soothingmusiclist[index].image
            )

            Spacer(modifier = Modifier.height(10.dp))


        }
//        item{
//            MobileAds.initialize(LocalContext.current)
//
//        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SWPreview() {
    SoothingMusic()


}