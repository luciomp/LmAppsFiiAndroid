package com.android.fiiapp.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.fiiapp.business.BusinessLogic
import com.android.fiiapp.data.Fii
import com.android.fiiapp.data.Range

class FilterRangeViewModel(
    val min: MutableLiveData<String> = MutableLiveData<String>(""),
    val max: MutableLiveData<String> = MutableLiveData<String>("")
)
{
    fun clear() {
        min.value = ""
        max.value = ""
    }

    private fun safeToDouble(i: String?): Double? {
        return try {
            i?.toDouble()
        } catch (e: NumberFormatException) {
            null
        }
    }

    fun toFilterRange(): Range {
        return Range(
            safeToDouble(min.value),
            safeToDouble(max.value),
        )
    }
}

class FilterViewModel(
    private val businessLogic: BusinessLogic
): ViewModel() {

    val sectors = businessLogic.fiis.sectors
    val sectors_selected: Map<String, MutableLiveData<Boolean>> = sectors.associateWith {
        MutableLiveData<Boolean>(it in businessLogic.filter.sectors)
    }
    val pl = FilterRangeViewModel()
    val pl_limits = businessLogic.rangeOf(Fii::PATRIMONIOLIQ)
    val price = FilterRangeViewModel()
    val price_limits = businessLogic.rangeOf(Fii::PRECOATUAL)
    val dy = FilterRangeViewModel()
    val dy_limits = businessLogic.rangeOf(Fii::DIVIDENDYIELD)
    val dy12m = FilterRangeViewModel()
    val dy12m_limits = businessLogic.rangeOf(Fii::DY12MACUMULADO)
    val vpa = FilterRangeViewModel()
    val vpa_limits = businessLogic.rangeOf(Fii::VPA)
    val pvpa = FilterRangeViewModel()
    val pvpa_limits = businessLogic.rangeOf(Fii::PVPA)

    fun clearFilter() {
        sectors_selected.forEach { it.value.value = false }
        pl.clear()
        price.clear()
        dy.clear()
        dy12m.clear()
        vpa.clear()
        pvpa.clear()
    }

    fun confirm() {
        businessLogic.setFilters(
            sectors = sectors_selected.filter { it.value.value!! }.map { it.key }.toSet(),
            pl = pl.toFilterRange(),
            price = price.toFilterRange(),
            dy = dy.toFilterRange(),
            dy12m = dy12m.toFilterRange(),
            vpa = vpa.toFilterRange(),
            pvpa = pvpa.toFilterRange()
        )
    }

}