package vn.xdeuhug.luckyMoney.utils

import android.annotation.SuppressLint
import android.content.Context
import vn.xdeuhug.luckyMoney.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

object DateUtils {
    const val DATE_FORMAT = "dd/MM/yyyy"

    fun formatDate(stringDate: String): String {
        var format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        var time = format.parse(stringDate)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Objects.requireNonNull(time))
    }

    fun formatDateTimeToDate(stringDate: String): String {
        val format = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val time = format.parse(stringDate)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(Objects.requireNonNull(time))
    }


    fun formatTime(strTime: String): String {
        val inputFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return outputFormat.format(inputFormat.parse(strTime)!!)
    }

    fun formatTimeWithUTC(strDate: String): String {
        val OLD_FORMAT = "yyyy-MM-dd HH:mm:ss"
        val NEW_FORMAT = "HH:mm dd-MM-yyyy"

        @SuppressLint("SimpleDateFormat") val df = SimpleDateFormat(OLD_FORMAT)
        df.timeZone = TimeZone.getDefault()
        val date: Date = try {
            df.parse(strDate) as Date
        } catch (e: ParseException) {
            throw RuntimeException(e)
        }
        df.timeZone = TimeZone.getDefault()
        val formattedDate = df.format(Objects.requireNonNull(date))

        var newDateString = ""
        try {
            @SuppressLint("SimpleDateFormat") val sdf = SimpleDateFormat(OLD_FORMAT)
            val d = sdf.parse(formattedDate)
            sdf.applyPattern(NEW_FORMAT)
            newDateString = sdf.format(Objects.requireNonNull(d))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return newDateString
    }

    fun formatDate(day: Int, month: Int, year: Int): String {
        var strDay = day.toString() + ""
        var strMonth = month.toString() + ""
        if (day == 0) strDay = ""
        if (month == 0) strMonth = ""
        if (day < 10) strDay = "0$strDay"
        if (month < 10) strMonth = "0$strMonth"
        return if (month == 0) year.toString() + "" else if (day == 0) "$strMonth/$year" else "$strDay/$strMonth/$year"
    }

    fun getYesterday(): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        return getDateReportString(cal.time)
    }

    fun getLastMonth(): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, -1)
        return getMonthReportString(cal.time)
    }

    fun getNextMonth(): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, +1)
        return getMonthReportString(cal.time)
    }

    fun getLastYear(): String {
        val cal = Calendar.getInstance()
        cal.add(Calendar.YEAR, -1)
        return getYearReportString(cal.time)
    }

    fun getDateReportString(): String {
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return sdf.format(Date())
    }

    fun getDateReportString(date: Date): String {
        val sdf = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return sdf.format(date)
    }

    fun getLastDayOfMonthString(): String {
        val calendar = Calendar.getInstance()
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH).toString()
    }

    fun getThisWeekFormat(): String {
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.minimalDaysInFirstWeek = 7
        val weekOfYear = calendar[Calendar.WEEK_OF_YEAR]
        var strWeek = weekOfYear.toString()
        if (weekOfYear < 10) strWeek = "0$strWeek"
        return String.format("%s/%s", strWeek, getYearReportString())
    }

    fun getStartOfWeek(): String {
        val calendar = Calendar.getInstance(Locale.getDefault())
        val startOfWeek = calendar.apply {
            firstDayOfWeek = Calendar.MONDAY
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }.time
        return getDateReportString(startOfWeek)
    }

    fun getEndOfWeek(): String {
        val calendar = Calendar.getInstance(Locale.getDefault())
        val endOfWeek = calendar.apply {
            firstDayOfWeek = Calendar.MONDAY
            set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        }.time
        return getDateReportString(endOfWeek)
    }

    fun getDateRangeLastMonth(): Pair<String, String> {
        var begin: Date
        var end: Date
        run {
            val calendar = Calendar.getInstance(Locale.getDefault())
            calendar.add(Calendar.MONTH, -1)
            calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
            setTimeToBeginningOfDay(calendar)
            begin = calendar.time
        }
        run {
            val calendar = Calendar.getInstance(Locale.getDefault())
            calendar.add(Calendar.MONTH, -1)
            calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            setTimeToEndOfDay(calendar)
            end = calendar.time
        }
        return Pair(getDateReportString(begin), getDateReportString(end))
    }

    fun getDateRangeThreeMonthNearest(): Pair<String, String> {
        var begin: Date
        var end: Date
        run {
            val calendar = Calendar.getInstance(Locale.getDefault())
            calendar.add(Calendar.MONTH, -2)
            calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
            setTimeToBeginningOfDay(calendar)
            begin = calendar.time
        }
        run {
            val calendar = Calendar.getInstance(Locale.getDefault())
            calendar.add(Calendar.MONTH, 0)
            calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            setTimeToEndOfDay(calendar)
            end = calendar.time
        }
        return Pair(getDateReportString(begin), getDateReportString(end))
    }

    fun getDateRangeThisMonth(): Pair<String, String> {
        var begin: Date
        var end: Date
        run {
            val calendar: Calendar = getCalendarForNow()
            calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
            setTimeToBeginningOfDay(calendar)
            begin = calendar.time
        }
        run {
            val calendar: Calendar = getCalendarForNow()
            calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            setTimeToEndOfDay(calendar)
            end = calendar.time
        }
        return Pair(getDateReportString(begin), getDateReportString(end))
    }

    private fun getCalendarForNow(): Calendar {
        val calendar = GregorianCalendar.getInstance()
        calendar.time = Date()
        return calendar
    }

    private fun setTimeToBeginningOfDay(calendar: Calendar) {
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        calendar[Calendar.MILLISECOND] = 0
    }

    private fun setTimeToEndOfDay(calendar: Calendar) {
        calendar[Calendar.HOUR_OF_DAY] = 23
        calendar[Calendar.MINUTE] = 59
        calendar[Calendar.SECOND] = 59
        calendar[Calendar.MILLISECOND] = 999
    }

    fun getStartDateInThisYear(): String {
        return getStartDateInYear(getThisYear())
    }

    fun getEndDateInThisYear(): String {
        return getEndDateInYear(getThisYear())
    }

    fun getStartDateInLastYear(): String {
        return getStartDateInYear(getLastYears())
    }

    fun getEndDateInLastYear(): String {
        return getEndDateInYear(getLastYears())
    }

    fun getStartTwoYearAgo(): String {
        return getStartDateInYear(getTwoYearAgo())
    }

    fun getStartYear2000(): String{
        return getStartDateInYear(2000)
    }


    fun getStartDateInYear(year: Int): String {
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = year
        cal[Calendar.DAY_OF_YEAR] = 1
        val start = cal.time
        return getDateReportString(start)
    }

    fun getEndDateInYear(year: Int): String {
        val cal = Calendar.getInstance()
        cal[Calendar.YEAR] = year
        cal[Calendar.MONTH] = 11 // 11 = december
        cal[Calendar.DAY_OF_MONTH] = 31 // new years eve
        val end = cal.time
        return getDateReportString(end)
    }

    fun getThisYear(): Int {
        val cal = Calendar.getInstance()
        return cal[Calendar.YEAR]
    }

    fun getLastYears(): Int {
        val cal = Calendar.getInstance()
        return cal[Calendar.YEAR] - 1
    }

    fun getTwoYearAgo(): Int {
        val cal = Calendar.getInstance()
        return cal[Calendar.YEAR] - 2
    }


    fun getMonthReportString(): String {
        return getDateReportString().substring(getDateReportString().indexOf("/") + 1)
    }

    fun getYearReportString(): String {
        return getDateReportString().substring(getDateReportString().lastIndexOf("/") + 1)
    }

    fun getMonthReportString(date: Date): String {
        return getDateReportString(date).substring(getDateReportString(date).indexOf("/") + 1)
    }

    fun getYearReportString(date: Date): String {
        return getDateReportString(date).substring(getDateReportString(date).lastIndexOf("/") + 1)
    }

    fun formatReportDateTime(
        context: Context, reportTypeSort: Int, inputDateTime: String, position: Int
    ): String {
        var outputDateTime = ""
        if (inputDateTime == "0000-00-00 00") return "0"
        try {
            val format: SimpleDateFormat
            val time: Date
            when (reportTypeSort) {
                1, 9 -> {
                    format = SimpleDateFormat("yyyy-MM-dd HH", Locale.getDefault())
                    time = format.parse(inputDateTime)!!
                    val timeFormat = SimpleDateFormat("HH", Locale.getDefault())
                    outputDateTime = String.format("%s:00", timeFormat.format(time))
                    return outputDateTime
                }

                2 -> when (position) {

                    0 -> {
                        outputDateTime = context.getString(R.string.monday)
                        return outputDateTime
                    }

                    1 -> {
                        outputDateTime = context.getString(R.string.tuesday)
                        return outputDateTime
                    }

                    2 -> {
                        outputDateTime = context.getString(R.string.wednesday)
                        return outputDateTime
                    }

                    3 -> {
                        outputDateTime = context.getString(R.string.thursday)
                        return outputDateTime
                    }

                    4 -> {
                        outputDateTime = context.getString(R.string.friday)
                        return outputDateTime
                    }

                    5 -> {
                        outputDateTime = context.getString(R.string.saturday)
                        return outputDateTime
                    }

                    6 -> {
                        outputDateTime = context.getString(R.string.sunday)
                        return outputDateTime
                    }
                }

                3, 10, 4 -> {
                    format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    time = format.parse(inputDateTime)!!
                    val dayFormat = SimpleDateFormat("dd/MM", Locale.getDefault())
                    outputDateTime = dayFormat.format(time)
                    return outputDateTime
                }

                5, 11, 6, 15 -> {
                    format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    time = format.parse(inputDateTime)!!
                    val monthCurrentFormat = SimpleDateFormat("MM/yyyy", Locale.getDefault())
                    outputDateTime = monthCurrentFormat.format(time)
                    return outputDateTime
                }

                8, 16 -> {

                    format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    time = format.parse(inputDateTime)!!
                    val yearFormat = SimpleDateFormat("yyyy", Locale.getDefault())
                    outputDateTime = yearFormat.format(time)
                    return outputDateTime
                }

                13 -> {
                    format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    time = format.parse(inputDateTime)!!
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    outputDateTime = dateFormat.format(time)
                    return outputDateTime
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return outputDateTime
    }

    fun formatMonthString(month: Int, year: Int): String {
        val monthFormat = if (month < 10) {
            String.format("0%s", month)
        } else {
            month.toString()
        }
        return String.format("%s/%s", monthFormat, year)
    }

    fun formatDateString(day: Int, month: Int, year: Int): String {

        val dayFormat = if (day < 10) {
            String.format("0%s", day)
        } else {
            day.toString()
        }
        val monthFormat = if (month < 10) {
            String.format("0%s", month)
        } else {
            month.toString()
        }
        return String.format("%s/%s/%s", dayFormat, monthFormat, year)
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateFromStringDay(str_date: String?): String? {
        try {
            @SuppressLint("SimpleDateFormat") var dt = SimpleDateFormat("dd/MM/yyyy")
            val newDate = dt.parse(str_date!!)
            dt = SimpleDateFormat("MM/yyyy")
            return dt.format(newDate!!)
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
            ex.printStackTrace()
        }
        return ""
    }

    fun getCurrentTime(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(currentDate)
    }

    fun getTimeCurrent(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return dateFormat.format(currentDate)
    }

    fun getCurrentDate(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return dateFormat.format(currentDate)
    }

    // tính tuổi từ ngày sinh
    fun calculateAge(dateString: String): Int {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        val cal = Calendar.getInstance()
        cal.time = date!!
        val now = Calendar.getInstance()
        var years = now.get(Calendar.YEAR) - cal.get(Calendar.YEAR)
        if (now.get(Calendar.DAY_OF_YEAR) < cal.get(Calendar.DAY_OF_YEAR)) {
            years--
        }
        return years
    }

    // ngày bắt đầu nhỏ hơn ngày kết thúc
    fun checkTimings(time: String, endTime: String): Boolean {
        val pattern = "dd/MM/yyyy"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())

        try {
            val date1 = sdf.parse(time)
            val date2 = sdf.parse(endTime)

            if (date1 != null && date2 != null) {
                // Compare the dates
                if (date1 <= date2) {
                    return true // time is before or equal to endTime
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return false // time is after endTime or an error occurred
    }

    private fun getDateFromString(str_date: String?): Date {
        @SuppressLint("SimpleDateFormat") val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        var date: Date? = null
        try {
            date = dateFormat.parse(str_date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date!!
    }

    @SuppressLint("DefaultLocale")
    fun timeAgoString(context: Context, date: String?): String? {
        val past: Date = getDateFromString(date)
        val now = Date()
        try {
            val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time)
            val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time)
            val hours: Long = TimeUnit.MILLISECONDS.toHours(now.time - past.time)
            val days: Long = TimeUnit.MILLISECONDS.toDays(now.time - past.time)
            val mouth = days / 30
            return if (seconds < 60) {
                context.getString(R.string.finish_now)
            } else if (minutes < 60) {
                String.format(context.getString(R.string.minute_ago_notify), minutes)
            } else if (hours < 24) {
                String.format(context.getString(R.string.hour_ago_notify), hours)
            } else if (days < 30) {
                String.format(context.getString(R.string.day_ago_notify), days)
            } else if (days > 30 && mouth < 12) {
                String.format(context.getString(R.string.month_ago_notify), mouth)
            } else {
                String.format("%s", date)
            }
        } catch (j: Exception) {
            j.printStackTrace()
        }
        return String.format("%s", date)
    }

    fun getDateForPass(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("ddMMyy", Locale.getDefault())
        return dateFormat.format(currentDate)
    }


    fun addDays(date: Date, days: Int): Date? {
        val cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.DATE, days) //minus number would decrement the days
        return cal.time
    }

    fun formatDateTime(inputData: String): String {
        val inputDateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("hh:mm a   dd/MM/yyyy", Locale.getDefault())

        return try {
            val date = inputDateFormat.parse(inputData)
            outputDateFormat.format(date!!)
        } catch (e: Exception) {
            "Đã xảy ra lỗi: ${e.message}"
        }
    }
}