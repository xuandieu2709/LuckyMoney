package vn.xdeuhug.luckyMoney.http.model

import com.google.gson.annotations.SerializedName

/**
 * @Author: NGUYEN THE DAT
 * @Date: 4/8/2023
 */
class VersionData {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("version")
    var version: String = ""

    @SerializedName("message")
    var message: String = ""

    @SerializedName("is_require_update")
    var isRequireUpdate: Int = 0
}