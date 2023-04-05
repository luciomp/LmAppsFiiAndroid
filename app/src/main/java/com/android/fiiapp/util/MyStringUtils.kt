package com.android.fiiapp.util

import java.text.DecimalFormat
import kotlin.math.abs

class MyStringUtils {
    companion object {
        @JvmStatic
        fun d2(v: Double, pattern: String): String {
            val df = DecimalFormat(pattern)
            val av = abs(v)
            return when {
                av >= 1000.0 && av < 1000000.0 -> {
                    df.format(v / 1000) + " mil"
                }
                av >= 1000000.0 && av < 1000000000.0 -> {
                    df.format(v / 1000000) + " mi"
                }
                av >= 1000000000.0 && av < 1000000000000.0 -> {
                    df.format(v / 1000000000) + " bi"
                }
                av >= 1000000000000.0 -> {
                    df.format(v / 1000000000000) + " tri"
                } else -> df.format(v)
            }
        }

        @JvmStatic
        fun d2c(v: Double): String {
            return d2(v, "¤ 0.00")
        }

        @JvmStatic
        fun d2n(v: Double): String {
            return d2(v, "0.00")
        }
    }
}
