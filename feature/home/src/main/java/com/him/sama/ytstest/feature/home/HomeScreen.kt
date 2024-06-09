package com.him.sama.ytstest.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.him.sama.ytstest.common.ui.theme.SpotifyJetpackComposeTheme

@Composable
fun HomeScreen() {
    Body()
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun Body() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
    ) {
        
    }
}

@Preview
@Composable
fun PreviewBody() {
    SpotifyJetpackComposeTheme {
        Body()
    }
}
