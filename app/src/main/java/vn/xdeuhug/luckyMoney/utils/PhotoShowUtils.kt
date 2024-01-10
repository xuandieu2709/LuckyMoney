package vn.xdeuhug.luckyMoney.utils

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.common.base.Strings
import vn.xdeuhug.luckyMoney.R

object PhotoShowUtils {

    fun getBitmap(urlImage: String?, context: Context): Bitmap {
        val bitmapAvatar = if (Strings.isNullOrEmpty(urlImage)) {
            Glide.with(context)
                .asBitmap()
                .load(
                    R.drawable.ic_user_default
                )
                .circleCrop()
                .placeholder(R.drawable.ic_user_default)
                .error(R.drawable.ic_user_default)
                .submit(100, 100)
                .get()
        } else {
            try {
                Glide.with(context)
                    .asBitmap()
                    .load(urlImage)
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .apply(
                        RequestOptions().placeholder(R.drawable.ic_user_default)
                            .error(R.drawable.ic_user_default)
                    )
                    .submit(100, 100)
                    .get()
            } catch (e: Exception) {
                Glide.with(context)
                    .asBitmap()
                    .load(
                        R.drawable.ic_user_default
                    )
                    .circleCrop()
                    .placeholder(R.drawable.ic_user_default)
                    .error(R.drawable.ic_user_default)
                    .submit(100, 100)//480 320
                    .get()
            }
        }
        return bitmapAvatar
    }
}