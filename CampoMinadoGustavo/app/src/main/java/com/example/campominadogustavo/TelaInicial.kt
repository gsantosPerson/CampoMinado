package com.example.campominadogustavo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import android.content.Intent
import androidx.compose.ui.platform.LocalContext

@Composable
fun TelaInicial() {

    val contexto = LocalContext.current

    var dificuldadeSelecionada by rememberSaveable {
        mutableStateOf("")
    }

    var mostrarPersonalizado by rememberSaveable {
        mutableStateOf(false)
    }

    var linhas by rememberSaveable {
        mutableStateOf("10")
    }

    var colunas by rememberSaveable {
        mutableStateOf("10")
    }

    var bombas by rememberSaveable {
        mutableStateOf("15")
    }

    val dificuldades = listOf(
        "FACIL" to "Fácil - 10×10 - 15%",
        "MEDIO" to "Médio - 16×16 - 18%",
        "DIFICIL" to "Difícil = 30×16 - 21%",
        "PERSONALIZADO" to "Personalizado"
    )

    if (mostrarPersonalizado) {
        val quantidadeLinhas = linhas.toIntOrNull() ?: 0
        val quantidadeColunas = colunas.toIntOrNull() ?: 0
        val quantidadeBombas = bombas.toIntOrNull() ?: 0

        val configuracaoValida =
            quantidadeLinhas in 2..30 &&
                    quantidadeColunas in 2..30 &&
                    quantidadeBombas in 1 until (
                    quantidadeLinhas * quantidadeColunas
                    )

        AlertDialog(
            onDismissRequest = {
                mostrarPersonalizado = false
            },

            containerColor = Color(0xFFF5F5F5),

            title = {
                Text("Personalizado", color = Color.DarkGray)
            },

            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Linhas (2 a 30)", color = Color.DarkGray)

                    OutlinedTextField(
                        value = linhas,
                        onValueChange = { linhas = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("Colunas (2 a 30)", color = Color.DarkGray)

                    OutlinedTextField(
                        value = colunas,
                        onValueChange = { colunas = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text("Bombas", color = Color.DarkGray)

                    OutlinedTextField(
                        value = bombas,
                        onValueChange = { bombas = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },

            confirmButton = {
                TextButton(
                    enabled = configuracaoValida,
                    onClick = {
                        dificuldadeSelecionada = "PERSONALIZADO"
                        mostrarPersonalizado = false

                        val intent = Intent(
                            contexto,
                            JogoActivity::class.java
                        ).apply {
                            putExtra("DIFICULDADE", "PERSONALIZADO")
                            putExtra("LINHAS", quantidadeLinhas)
                            putExtra("COLUNAS", quantidadeColunas)
                            putExtra("BOMBAS", quantidadeBombas)
                        }

                        contexto.startActivity(intent)
                    }
                ) {
                    Text("Confirmar", color = Color.DarkGray)
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        mostrarPersonalizado = false
                    }
                ) {
                    Text("Cancelar", color = Color.DarkGray)
                }
            }
        )
    }

    Scaffold(
        containerColor = Color(0xFFF5F5F5)
    ) { espacoSistema ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(espacoSistema)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Campo Minado",
                fontSize = 30.sp,
                color = Color(0xFF222222)
            )

            Text(
                text = "Escolha a dificuldade",
                fontSize = 25.sp,
                color = Color(0xFF555555)
            )

            dificuldades.forEach { (codigo, descricao) ->
                val selecionada = dificuldadeSelecionada == codigo

                OutlinedButton (
                    onClick = {

                        if (codigo == "PERSONALIZADO") {
                            mostrarPersonalizado = true
                        } else {
                            dificuldadeSelecionada = codigo

                            val intent = Intent(
                                contexto,
                                JogoActivity::class.java
                            ).apply {
                                putExtra("DIFICULDADE", codigo)
                            }

                            contexto.startActivity(intent)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Color(0xFF444444)
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selecionada) {
                            Color(0xFF333333)
                        } else {
                            Color.White
                        },
                        contentColor = if (selecionada) {
                            Color.White
                        } else {
                            Color(0xFF222222)
                        }
                    )
                ) {
                    Text(
                        text = descricao,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}