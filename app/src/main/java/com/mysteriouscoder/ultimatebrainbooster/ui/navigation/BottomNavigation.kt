package com.mysteriouscoder.ultimatebrainbooster.ui.navigation

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mysteriouscoder.ultimatebrainbooster.R
import com.mysteriouscoder.ultimatebrainbooster.ui.navigation.customdialog.CustomDialogViewModel
import com.mysteriouscoder.ultimatebrainbooster.ui.navigation.customdialog.dialog.ConnectUsDialog
import com.mysteriouscoder.ultimatebrainbooster.ui.navigation.customdialog.dialog.ExitDialog
import com.mysteriouscoder.ultimatebrainbooster.ui.screens.binauralbeats.BinauralBeats
import com.mysteriouscoder.ultimatebrainbooster.ui.screens.binauralbeats.musiccontroller.MusicPlayerViewModel
import com.mysteriouscoder.ultimatebrainbooster.ui.screens.binauralbeats.musiccontroller.SongScreen
import com.mysteriouscoder.ultimatebrainbooster.ui.screens.faq.FAQ
import com.mysteriouscoder.ultimatebrainbooster.ui.screens.soothingmusic.SoothingMusic
import com.mysteriouscoder.ultimatebrainbooster.ui.screens.soothingmusic.musiccontroller.MusicPlayerViewModel2
import com.mysteriouscoder.ultimatebrainbooster.ui.screens.soothingmusic.musiccontroller.SongScreen2


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavigation(viewModel: CustomDialogViewModel) {


    var isSheetOpen by rememberSaveable {
        mutableStateOf(true)
    }

    val context = LocalContext.current
    val sendEmailIntent = remember {
        mutableStateOf(Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:officialbrainbooster@gmail.com")
            putExtra(Intent.EXTRA_SUBJECT, "Feedback")
        })
    }

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->

        }

    val showConnectUsDialog = remember { mutableStateOf(false) }
    val showExitDialog = remember { mutableStateOf(false) }
    val navigationController = rememberNavController()
    val items = listOf(

        BottomNavigationItem(
            title = "Soothing Music",
            icon = painterResource(id = R.drawable.soothingmusicicon),
        ),
        BottomNavigationItem(
            title = "Binaural Beats",
            icon = painterResource(id = R.drawable.binauralbeatsicon),
        ),
        BottomNavigationItem(
            title = "FAQ",
            icon = painterResource(id = R.drawable.faqicon),
        )
    )
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "\t\tBrainBooster",
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.nunito_extrabold)),

                        )
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
//                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    var showMenu by remember { mutableStateOf(false) }
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "More options",
                            tint = Color.White
                        )
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("connect") },
                            onClick = {
                                CustomDialogViewModel.onClick()
                                if (CustomDialogViewModel.isDialogShown) {
                                    showConnectUsDialog.value = true
                                }
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("mail") },
                            onClick = {
                                launcher.launch(sendEmailIntent.value)
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("exit") },
                            onClick = {
                                CustomDialogViewModel.onClick()
                                if (CustomDialogViewModel.isDialogShown) {
                                    showExitDialog.value = true
                                }

                            }
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (isSheetOpen) {
                NavigationBar(
                    modifier = Modifier.height(95.dp),
//                    containerColor = if (isSystemInDarkTheme()) Color.Black else Color.White,
                    containerColor = MaterialTheme.colorScheme.background,

                    ) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(selected = selectedItemIndex == index, onClick = {
                            selectedItemIndex = index
                            navigationController.navigate(item.title){
                                popUpTo(Navigationitems.BinauralBeats.route) {
                                    saveState = true
                                    inclusive = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                        }, label = {
                            Text(
                                text = item.title,
                                fontFamily = FontFamily(Font(R.font.nunito_extrabold)),
                                fontSize = if (selectedItemIndex == index) 10.sp else 8.sp,
                                color = if (selectedItemIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                modifier = if (selectedItemIndex == index) Modifier.padding(top = 4.dp) else Modifier.padding(
                                    top = 0.dp
                                )
                            )
                        }, icon = {

                            if (index == selectedItemIndex) {

                                Column {

                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(
                                                MaterialTheme.colorScheme.primary
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = item.icon,
                                            contentDescription = item.title,
                                            modifier = Modifier.size(24.dp),
                                            tint = if (isSystemInDarkTheme()) Color.Black else Color.White


                                        )
                                    }
                                }
                            } else {
                                Icon(
                                    painter = item.icon,
                                    contentDescription = item.title,
                                    modifier = Modifier.size(28.dp),
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        })
                    }
                }
            }
        }) { paddingValues ->
        NavHost(
            navController = navigationController,
            startDestination = Navigationitems.SoothingMusic.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(Navigationitems.FAQ.route) {
                isSheetOpen = true
                FAQ()

            }
            composable(Navigationitems.BinauralBeats.route) {
                isSheetOpen = true
                BinauralBeats { idx ->
                    navigationController.navigate(
                        Navigationitems.MusicPlayerWithIndex.route.replace(
                            "{idx}", idx.toString()
                        )
                    ) {
                        popUpTo(Navigationitems.BinauralBeats.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
            composable(Navigationitems.SoothingMusic.route) {
                isSheetOpen = true
                SoothingMusic { idx ->
                    navigationController.navigate(
                        Navigationitems.MusicPlayerWithIndex2.route.replace(
                            "{idx}",
                            idx.toString()
                        )
                    ) {
                        popUpTo(Navigationitems.SoothingMusic.route) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
            composable(Navigationitems.MusicPlayerScreen.route) {

                isSheetOpen = false

                val packageName = context.packageName
                // Player screen
                val vm: MusicPlayerViewModel = viewModel(
                    factory = MusicPlayerViewModel.Factory(1)
                )
                val state = vm.playerState.collectAsState().value

                SongScreen(
                    state = state,
                    onBackPress = {
                        navigationController.popBackStack()
                    })
            }

            composable(Navigationitems.MusicPlayerScreen2.route) {

                isSheetOpen = false

                // Player screen
                val vm: MusicPlayerViewModel2 = viewModel(
                    factory = MusicPlayerViewModel2.Factory(1)
                )
                val state = vm.playerState.collectAsState().value

                SongScreen2(
                    state = state,
                    onBackPress = {
                        navigationController.popBackStack()
                    })
            }

            composable(
                Navigationitems.MusicPlayerWithIndex.route,
                arguments = listOf(navArgument("idx") { type = NavType.IntType })
            ) {
                val idx = it.arguments?.getInt("idx") ?: 0
                isSheetOpen = true
                Log.d("MusicPlayerWithIndex", "MusicPlayerWithIndex")
                val vm: MusicPlayerViewModel = viewModel(
                    factory = MusicPlayerViewModel.Factory(idx)
                )
                val state = vm.playerState.collectAsState().value

                SongScreen(
                    state = state,
                    onBackPress = {
                        navigationController.popBackStack()
                    }
                )
                isSheetOpen = false


            }

            composable(
                Navigationitems.MusicPlayerWithIndex2.route,
                arguments = listOf(navArgument("idx") { type = NavType.IntType })
            ) {
                val idx = it.arguments?.getInt("idx") ?: 0
                isSheetOpen = true
                Log.d("MusicPlayerWithIndex", "MusicPlayerWithIndex")
                val vm: MusicPlayerViewModel2 = viewModel(
                    factory = MusicPlayerViewModel2.Factory(idx)
                )
                val state = vm.playerState.collectAsState().value

                SongScreen2(
                    state = state,
                    onBackPress = {
                        navigationController.popBackStack()
                    }
                )
                isSheetOpen = false


            }
        }
        if (showConnectUsDialog.value) {
            ConnectUsDialog(
                onDismiss = {
                    showConnectUsDialog.value = false
                    CustomDialogViewModel.onDismissDialog()
                },
            )
        }
        if (showExitDialog.value) {
            ExitDialog(
                onDismiss = {
                    showExitDialog.value = false
                    CustomDialogViewModel.onDismissDialog()
                },
            )
        }
    }

}

data class BottomNavigationItem(
    val title: String,
    val icon: Painter,
)

@Preview
@Composable
private fun BottomNavigationPreview() {
    BottomNavigation(CustomDialogViewModel())

}