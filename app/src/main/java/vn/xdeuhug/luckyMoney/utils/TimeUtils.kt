package vn.xdeuhug.luckyMoney.utils

import android.annotation.SuppressLint
import android.content.Context
import vn.xdeuhug.luckyMoney.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone
import java.util.concurrent.TimeUnit

/**
 * @Author: NGUYEN THANH BINH
 * @Date: 08/05/2023
 */
object TimeUtils {
    private var FORMAT_SERVER = "yyyy-MM-dd HH:mm:ss"

    //Tính toán thời gian của thông báo
//Tính toán thời gian của thông báo
    fun formatDatetimeNotification(context: Context, date: String): String {
        return try {
            val past = formatDateFromString(date)
            val now = Date()
            val calendar: Calendar = GregorianCalendar()
            calendar.time = past!!
            val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time) //giây
            val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time) //phút
            val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time) //giờ
            val days = TimeUnit.MILLISECONDS.toDays(now.time - past.time) // ngày
            val year = days / 365 //năm
            if (seconds < 60) {
                context.getString(R.string.finish_now)
            } else if (minutes < 60) {
                String.format(context.getString(R.string.minute_ago_notify), minutes)
            } else if (hours < 24) {
                String.format(context.getString(R.string.hour_ago_notify), hours)
            } else if (days < 8) {
                String.format(context.getString(R.string.day_ago_notify), days)
            } else if (days in 8..364) {
                String.format(
                        "%s/%s lúc %s:%s",
                        String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)),
                        String.format("%02d", calendar.get(Calendar.MONTH) + 1),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        String.format("%02d", calendar.get(Calendar.MINUTE))
                )
            } else if (year > 0) {
                date

            } else {
                date
            }
        } catch (j: Exception) {
            date
        }
    }

    //Tính toán thời gian last message
    fun formatDatetimeChat(context: Context, date: String): String {
        return try {
            val past = formatDateFromString(date)
            val now = Date()
            val calendar: Calendar = GregorianCalendar()
            calendar.time = past!!
            val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time) //giây
            val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time) //phút
            val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time) //giờ
            val days = TimeUnit.MILLISECONDS.toDays(now.time - past.time) // ngày
            val year = days / 365 //năm
            if (seconds < 60) {
                context.getString(R.string.finish_now)
            } else if (minutes < 60) {
                String.format(context.getString(R.string.minute_ago), minutes)
            } else if (hours < 24) {
                String.format(context.getString(R.string.hour_ago), hours)
            } else if (days < 8) {
                String.format(context.getString(R.string.day_ago), days)
            } else if (days in 8..364) {
                String.format(
                    "%s/%s lúc %s:%s",
                    String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)),
                    String.format("%02d", calendar.get(Calendar.MONTH) + 1),
                    calendar.get(Calendar.HOUR_OF_DAY),
                    String.format("%02d", calendar.get(Calendar.MINUTE))
                )
            } else if (year > 0) {
                String.format(
                    "%s lúc %s",
                    date,
                    calendar.get(Calendar.HOUR_OF_DAY),
                    String.format("%02d", calendar.get(Calendar.MINUTE))
                )
            } else {
                date
            }
        } catch (j: Exception) {
            date
        }
    }

    //Tính toán thời gian bình luận
    fun formatDatetimeComment(context: Context, date: String): String {
        return try {
            val past = formatDateFromString(date)
            val now = Date()
            val calendar: Calendar = GregorianCalendar()
            calendar.time = past!!
            val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time) //giây
            val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time) //phút
            val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time) //giờ
            val days = TimeUnit.MILLISECONDS.toDays(now.time - past.time) // ngày
            val year = days / 365 //năm
            if (seconds < 60) {
                context.getString(R.string.finish_now)
            } else if (minutes < 60) {
                String.format(context.getString(R.string.minute_ago), minutes)
            } else if (hours < 24) {
                String.format(context.getString(R.string.hour_ago), hours)
            } else if (days < 8) {
                String.format(context.getString(R.string.day_ago), days)
            } else if (days in 8..364) {
                String.format("%s %s", days / 4, "Tuần")
            } else if (year > 0) {
                String.format("%s %s", year, "Năm")
            } else {
                date
            }
        } catch (j: Exception) {
            date
        }
    }

    //Sử dụng ở các bài post(timeline)
    fun formatDatetimePost(context: Context, date: String): String {
        return try {
            val past = formatDateFromString(date)
            val now = Date()
            val calendar: Calendar = GregorianCalendar()
            calendar.time = past!!
            val seconds = TimeUnit.MILLISECONDS.toSeconds(now.time - past.time) //giây
            val minutes = TimeUnit.MILLISECONDS.toMinutes(now.time - past.time) //phút
            val hours = TimeUnit.MILLISECONDS.toHours(now.time - past.time) //giờ
            val days = TimeUnit.MILLISECONDS.toDays(now.time - past.time) // ngày
            val year = days / 365 //năm
            if (seconds < 60) {
                context.getString(R.string.finish_now)
            } else if (minutes < 60) {
                String.format(context.getString(R.string.minute_ago), minutes)
            } else if (hours < 24) {
                String.format(context.getString(R.string.hour_ago), hours)
            } else if (days < 8) {
                String.format(context.getString(R.string.day_ago), days)
            } else if (days in 9..364) {
                String.format(
                    "%s tháng %s lúc %s:%s",
                    String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)),
                    String.format("%02d", calendar.get(Calendar.MONTH) + 1),
                    calendar.get(Calendar.HOUR_OF_DAY),
                    String.format("%02d", calendar.get(Calendar.MINUTE))
                )
            } else if (year > 0) {
                String.format(
                    "%s tháng %s, %s lúc %s:%s",
                    String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH)),
                    String.format("%02d", calendar.get(Calendar.MONTH) + 1),
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.HOUR_OF_DAY),
                    String.format("%02d", calendar.get(Calendar.MINUTE)),
                )
            } else {
                date
            }
        } catch (j: Exception) {
            date
        }
    }

    //Format thời gian trong bảng tin nhóm
    fun formatDateTimeGroupNews(date: String): String {
        return try {
            val calendar: Calendar = GregorianCalendar()
            calendar.time = formatDateFromString(date)!!
            val minutes = String.format("%02d", calendar.get(Calendar.MINUTE))
            val hours = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY))
            val days = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
            val month = String.format("%02d", calendar.get(Calendar.MONTH) + 1)
            val year: Int = calendar.get(Calendar.YEAR)
            "$days/$month/$year lúc $hours:$minutes"
        } catch (e: Exception) {
            date
        }
    }

    //Format thời gian trong Bình chọn
    fun formatDateTimeVote(date: String): String {
        return try {
            val calendar: Calendar = GregorianCalendar()
            calendar.time = formatDateFromString(date)!!
            val minutes = String.format("%02d", calendar.get(Calendar.MINUTE))
            val hours = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY))
            val days = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))
            val month = String.format("%02d", calendar.get(Calendar.MONTH) + 1)
            val year: Int = calendar.get(Calendar.YEAR)
            "Đã kết thúc vào $days/$month/$year lúc $hours:$minutes"
        } catch (e: Exception) {
            date
        }
    }


    /**
     * Format Datetime của server
     */
    @SuppressLint("SimpleDateFormat")
    fun formatDateFromString(dateTimeString: String): Date? {
        //Thời gian trên server là múi giờ 0 và theo định dạng "yyyy-MM-dd HH:mm:ss"
        val oldDateFormat = SimpleDateFormat(FORMAT_SERVER)
        oldDateFormat.timeZone = TimeZone.getTimeZone("UTC")

        //Thời gian dưới client là múi giờ hiện tại và theo định dạng "HH:mm dd-MM-yyyy"
        val NEW_FORMAT = "HH:mm dd-MM-yyyy"
        val newDateFormat = SimpleDateFormat(NEW_FORMAT)
        newDateFormat.timeZone = TimeZone.getDefault()

        val originalDate = oldDateFormat.parse(dateTimeString)//Chuyển thời gian trên qua kiểu Date
        val newDateTimeString =
            originalDate?.let { newDateFormat.format(it) }//format lại theo kiểu dưới client
        return newDateTimeString?.let { newDateFormat.parse(it) }//Sau khi format thì parse ra kiểu Date lại
    }

    //Tính toán thời gian chat
    @SuppressLint("SimpleDateFormat")
    fun changeFormatTimeMessageChat(strDate: String): String {
        val OLD_FORMAT = "MM-dd-yyyy HH:mm:ss"
        val of = SimpleDateFormat(OLD_FORMAT)
        of.timeZone = TimeZone.getTimeZone("UTC")

        val NEW_FORMAT = "dd/MM/yyyy HH:mm:ss"
        val af = SimpleDateFormat(NEW_FORMAT)
        af.timeZone = TimeZone.getDefault()

        return try {
            val dateTime = of.parse(strDate)
            af.format(dateTime!!)
        } catch (e: java.lang.Exception) {
            strDate
        }
    }

    //Format lại thời gian chat cho last_message(Xài tạm đến khi format datetime của server thống nhất 1 kiểu)
    @SuppressLint("SimpleDateFormat")
    fun changeFormatTimeLastMessageWhenChat(strDate: String): String {
        val OLD_FORMAT = "dd/MM/yyyy HH:mm:ss"
        val of = SimpleDateFormat(OLD_FORMAT)
        val af = SimpleDateFormat(FORMAT_SERVER)

        return try {
            val dateTime = of.parse(strDate)
            af.format(dateTime!!)
        } catch (e: Exception) {
            strDate
        }
    }

    /**
     * Lấy thời gian hiện tại theo múi giờ hiện tại theo format mong muốn
     * Tham số pattern là format mong muốn (VD: dd/MM/yyyy HH:mm:ss)
     */
    @SuppressLint("SimpleDateFormat")
    fun getCurrentTimeFormat(pattern: String, isTimeZoneUTC: Boolean): String {
        val dateFormat = SimpleDateFormat(pattern)
        dateFormat.timeZone =
            if (isTimeZoneUTC) TimeZone.getTimeZone("UTC") else TimeZone.getDefault()
        return dateFormat.format(Date())
    }
}