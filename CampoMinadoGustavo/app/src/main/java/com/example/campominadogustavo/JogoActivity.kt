package com.example.campominadogustavo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.saveable.rememberSaveable

class JogoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val dificuldade = intent.getStringExtra("DIFICULDADE") ?: "FACIL"

        setContent {

            var quantidadeCliques by rememberSaveable {
                mutableStateOf(0)
            }

            var tempoSegundos by rememberSaveable {
                mutableStateOf(0)
            }

            var cronometroAtivo by rememberSaveable {
                mutableStateOf(true)
            }

            LaunchedEffect(cronometroAtivo) {
                while (cronometroAtivo) {
                    delay(1000)
                    tempoSegundos++
                }
            }

            val minutos = tempoSegundos / 60

            val segundos = tempoSegundos % 60

            val tempoFormatado = String.format(
                Locale.getDefault(),
                "%02d:%02d",
                minutos,
                segundos
            )

            val formatoHora = remember {
                SimpleDateFormat("HH:mm:ss", Locale.getDefault())
            }

            var horaAtual by remember {
                mutableStateOf(formatoHora.format(Date()))
            }

            LaunchedEffect(Unit) {
                while (true) {
                    horaAtual = formatoHora.format(Date())
                    delay(1000)
                }
            }

            MaterialTheme {
                Scaffold(
                    containerColor = Color(0xFFF5F5F5)
                ) { espacoSistema ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(espacoSistema)
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.align(Alignment.TopCenter),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Hora atual: $horaAtual",
                                color = Color.DarkGray
                            )

                            Text(
                                text = "Tempo: $tempoFormatado",
                                color = Color.DarkGray
                            )

                            Text(
                                text = "Cliques: $quantidadeCliques",
                                color = Color.DarkGray
                            )
                        }
                        Text(
                            text = "Dificuldade: $dificuldade",
                            modifier = Modifier.align(Alignment.Center),
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}