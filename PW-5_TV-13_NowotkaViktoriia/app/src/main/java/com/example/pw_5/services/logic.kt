package com.example.pw_5.services

import android.util.Log

class CalculatorService{
    fun compareReliabilitySystems(): Array<Double> {
        var nSwitcher1 = 110
        var l1 = 10
        var transformerN1 = arrayOf(110, 10)
        var inputSwitch1 = 10
        var accesionN1 = 10
        var accesionCount1 = 6

        var nSwitcher2 = 110
        var l2 = 10
        var transformerN2 = arrayOf(110, 10)
        var inputSwitch2 = 10
        var accesionN2 = 10
        var accesionCount2 = 6
        var sectionalSwitchN2 = 10

        var w_sv = 0.02

        var w1 = arrayOf(0.01, 0.07, 0.015, 0.02, 0.03, 0.03, 0.03, 0.03, 0.03, 0.03)
        var t_v1 = arrayOf(30, 10, 100, 15, 2, 2, 2, 2, 2, 2)

        var w_os = w1.sum()
        var t_vos = w1.zip(t_v1) { a, b -> a * b }.sum() / w_os

        var k_aos = w_os * t_vos / 8760
        var k_pos = 1.2 * 43 / 8760

        var w_dk = 2*w_os * (k_aos + k_pos)
        var w_ds = w_dk + w_sv

        Log.d("Calculator1", "w_dk: $w_dk")
        Log.d("Calculator1", "w_ds: $w_ds")

        val roundedValue1 = "%.4f".format(w_dk).toDouble()
        val roundedValue2 = "%.4f".format(w_ds).toDouble()

        return arrayOf(roundedValue1, roundedValue2)


    }

    fun calculateLoses(): String {
        var z_pera = 23.6
        var z_perp = 17.6
        var w = 0.01
        var t_v = 0.045

        var t_m = 5.12 * 1000 * 6451
        var k_p = 0.004

        var m_w_neda = w * t_v * t_m
        var m_w_nedp = k_p * t_m
        Log.d("Calculator2", "m_w_neda: $m_w_neda")
        Log.d("Calculator2", "m_w_nedp: $m_w_nedp")

        var m_z_per = z_pera * m_w_neda + z_perp * m_w_nedp
        return "%.2f".format(m_z_per)
    }
}