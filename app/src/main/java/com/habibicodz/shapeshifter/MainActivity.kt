package com.habibicodz.shapeshifter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.habibicodz.shapeshifter.ui.appicon.AppIcon
import com.habibicodz.shapeshifter.ui.appicon.AppIconUtility
import com.habibicodz.shapeshifter.ui.theme.ShapeShifterTheme
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val applicationContext: Context = LocalContext.current.applicationContext
            val appIconUtility = remember { AppIconUtility(applicationContext) }
            val currentAppIcon = remember { appIconUtility.currentAppIcon }

            ShapeShifterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold (
                        topBar = {
                            TopAppBar(
                                title = {
                                    Text(text = "Shape Shifter")
                                }
                            )
                        }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {

                            enumValues<AppIcon>().toList().chunked(4).forEach { list ->
                                Column (
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    AppIconsRow (
                                        list,
                                        currentAppIcon
                                    ) { appIcon ->
                                        Log.d("analyze", "Icon clicked: ${appIcon.aliasName}")
                                        appIconUtility.setNewAppIcon(appIcon)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun AppIcon(appIcon: AppIcon, isSelected: Boolean, onClick: (AppIcon) -> Unit, modifier: Modifier) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(if (isSelected) 3.dp else 0.dp, color = Color.Blue, CircleShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(bounded = true, color = Color.DarkGray),
                onClick = {
                    onClick.invoke(appIcon)
                }
            )
            .border(if (isSelected) 6.dp else 0.dp, color = Color.White, CircleShape)

    ) {
        Image(
            modifier = Modifier.size(50.dp),
            painter = painterResource(id = appIcon.icon),
            contentDescription = "Application icon"
        )
    }
}

@Composable
fun AppIconsRow (
    list: List<AppIcon>,
    currentAppIcon: AppIcon,
    onClick: (AppIcon) -> Unit, ) {

    Row {
        list.forEach { appIcon ->
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                AppIcon(
                    appIcon = appIcon,
                    isSelected = currentAppIcon == appIcon,
                    onClick = onClick,
                    Modifier
                        .align(Alignment.Center)
                        .padding(10.dp))
            }
        }
    }
}