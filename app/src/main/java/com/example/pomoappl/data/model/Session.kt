package com.example.pomoappl.data.model

data class Session(
    val id: Int,
    val duration: Long,
    val completedAt: Long
)

data class PomodoroConfig(
    val id: Int,
    val nome: String,
    val duracaoFocoMinutos: Int,
    val duracaoPausaCurtaMinutos: Int,
    val duracaoPausaLongaMinutos: Int,
    val ciclosAtePausaLonga: Int
)