package com.android.fiiapp.data

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Range (
    var min: Double? = null,
    var max: Double? = null
) {
    fun isEmpty(): Boolean {
        return min == null && max == null
    }
}

@Serializable
data class Fii(
    var CODIGODOFUNDO: String,
    val SETOR: String,
    val PRECOATUAL: Double,
    val LIQUIDEZDIARIA: Double,
    val DIVIDENDO: Double,
    val DIVIDENDYIELD: Double,
    val DY3MACUMULADO: Double,
    val DY6MACUMULADO: Double,
    val DY12MACUMULADO: Double,
    val DY3MMEDIA: Double,
    val DY6MMEDIA: Double,
    val DY12MMEDIA: Double,
    val DYANO: Double,
    val VARIACAOPRECO: Double,
    val RENTABPERIODO: Double,
    val RENTABACUMULADA: Double,
    val PATRIMONIOLIQ: Double,
    val VPA: Double,
    val DYPATRIMONIAL: Double,
    val VARIACAOPATRIMONIAL: Double,
    val RENTABPATRNOPERIODO: Double,
    val RENTABPATRACUMULADA: Double,
    val VACANCIAFISICA: Double,
    val VACANCIAFINANCEIRA: Double,
    val QUANTIDADEATIVOS: Int,
    val CODIGOEXEC: String
) {
    val PVPA: Double
        get() = this.PRECOATUAL / this.VPA
}

@Serializable
data class FiiList(
    val fiis: List<Fii> = emptyList()
) {
    val sectors: SortedSet<String>
        get() {
            return fiis.map { it.SETOR }.toSortedSet()
        }

    fun rangeOf(fn: Fii.() -> Double): Range {
        return Range(
            fiis.minOf { it.fn() },
            fiis.maxOf { it.fn() },
        )
    }
}