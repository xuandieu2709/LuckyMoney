package vn.xdeuhug.luckyMoney.utils

/**
 * @Author: Bùi Hữu Thắng
 * @Date: 30/08/2022
 */
object AnimationUtils {
    @JvmStatic
    fun mapValueFromRangeToRange(
        value: Double,
        fromLow: Double,
        fromHigh: Double,
        toLow: Double,
        toHigh: Double
    ): Double {
        return toLow + (value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow)
    }

    @JvmStatic
    fun clamp(value: Double, low: Double, high: Double): Double {
        return value.coerceAtLeast(low).coerceAtMost(high)
    }
}