package vn.xdeuhug.luckyMoney.constants

/**
 * @Author: Bùi Hửu Thắng
 * @Date: 03/10/2022
 */
object
AppConstants {



    //Config call api
    const val ANDROID_SUPPLIER = 5
    const val APP_OS_NAME = "supplier_android"
    const val HTTP_METHOD_POST = 1
    const val HTTP_METHOD_GET = 0
    const val PROJECT_ID = "net.techres.supplier.api"

    const val METHOD_POST = "POST"



    const val CACHE_USER = "CACHE_USER"

    const val CACHE_HTTP_LOG = "CACHE_HTTP_LOG"
    const val CACHE_RESTAURANT = "CACHES_RESTAURANT"
    const val CACHE_CONFIG = "CACHE_CONFIG"
    const val TERMS_OF_USE = "https://techres.vn/quy-dinh-su-dung"
    const val PRIVACY_POLICY = "https://techres.vn/chinh-sach-bao-mat"



    //upload
    const val HEADER_NAME = "Authorization"
    const val UPLOAD_IMAGE = 0 // type up image

    const val MEDIA_ID = "medias[0][media_id]"
    const val TYPE = "medias[0][type]"
    const val FILE = "medias[0][file]"

    const val PUSH_TOKEN = "PUSH_TOKEN"
    const val USER_ID = "USER_ID"
    const val MEDIA_DATA = "MEDIA_DATA"
    const val MEDIA_DATA_TYPE = "MEDIA_DATA_TYPE"
    const val MEDIA_POSITION = "MEDIA_POSITION"
    const val DATA_MEDIA_TYPE = "DATA_MEDIA_TYPE"
    const val TYPE_FILE_CHAT = "TYPE_FILE_CHAT"

    //Dialog Input Type
    const val INTEGER_INPUT_TABLE = 1
    const val DECIMAL_INPUT_TABLE = 3
    const val PERCENT_INPUT_TABLE = 4 //
    const val INTEGER_INPUT_TABLE_MIN_HUNDRED_ZERO = 5
    const val SHIFT_INPUT = -1
    const val MAX_TEXT_LENGTH = 255

    const val TYPE_INVENTORY = "TYPE_INVENTORY"
    const val KEY_RESTAURANT_BRANCH_ID = "KEY_RESTAURANT_BRANCH_ID"
    const val KEY_TOTAL = "KEY_TOTAL"
    const val KEY_BOOLEAN = "KEY_BOOLEAN"



    const val FOLDER_APP = "techres/supplier"//folder lưu file của App
    const val EXPIRED = 1
    const val LOCATION = 2
    const val PROGRESS = "progress"
    const val DOWNLOAD_CACHE = "DOWNLOAD_CACHE"
    const val FILE_DOWNLOAD = "FILE_DOWNLOAD"
    const val TYPE_DOWNLOAD = "TYPE_DOWNLOAD"

    const val KEY_DATA = "KEY_DATA"

    const val TYPE_WARNING = 1
    const val TYPE_NOTE = 2
    const val TYPE_RESET_PASSWORD = 3
    const val TYPE_NOTIFICATION = 4
    const val TYPE_EMPLOYEE_CREATED = 5
    const val TYPE_LOCK_AND_UNLOCK_EMPLOYEE = 6
    const val TYPE_CONFIRM_IN_PAYMENT_REQUEST = 7
    const val TYPE_UPDATE_INFORMATION_EMPLOYEE = 8
    const val PRICE_SELECTED = true
    const val PERCENT_SELECTED = false


    const val TYPE_RESTAURANT = 0
    const val TYPE_RESTAURANT_BRAND = 1
    const val TYPE_RESTAURANT_BRANCH = 2


    const val OBJECT_ID = "OBJECT_ID"
    const val NOTIFICATION_TYPE = "NOTIFICATION_TYPE"
    const val JSON_ADDITION_DATA = "JSON_ADDITION_DATA"

    const val TYPE_MATERIAL = 1000
    const val TYPE_UNIT = 2000
    const val TYPE_MATERIAL_UPDATE = 2100
    const val TYPE_UNIT_SPECIFICATIONS = 3000
    const val TYPE_CATEGORY = 4000
    const val PAGE_SIZE = 20
    const val MATERIAL_ON = 1
    const val MATERIAL_OFF = 0
    // Dialog Choose Order
    const val ORDER_FINISHED = 4 // Hoàn tất - Trạng thái cuối cùng
    const val PAYMENT_STATUS_ZERO = 0



    ////payment status
    const val WAITING_PAYMENT = 0 //"Chờ thanh toán"

    const val WAITING_CONFIRM = 1 //"Chờ xác nhận thanh toán"
    const val PAID = 2 //"Đã thanh toán"
    const val CANCELLED = 3 //"Đã hủy"

    const val ON_LOGOUT = "kick-out.supplier-"
}