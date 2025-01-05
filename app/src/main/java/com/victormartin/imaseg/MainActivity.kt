package com.victormartin.imaseg

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.victormartin.imaseg.ui.components.core.MultiplePermissionsHandler
import com.victormartin.imaseg.ui.screens.main.MainScreen
import com.victormartin.imaseg.ui.theme.ImasegTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ImasegTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MultiplePermissionsHandler {
                        MainScreen(
                            modifier = Modifier.fillMaxSize(),
                            padding = innerPadding,
                            onError = { error ->
                                Toast.makeText(
                                    this@MainActivity,
                                    error,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                }
            }
        }
    }
}