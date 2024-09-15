package math


import java.util.Locale


fun round(num: Int): Double {
    return String.format(Locale.US, "%.2f", num).toDouble()
}

fun round(num: Double): Double {
    return String.format(Locale.US, "%.2f", num).toDouble()
}

fun round(num: Float): Float {
    return String.format(Locale.US, "%.2f", num).toFloat()
}