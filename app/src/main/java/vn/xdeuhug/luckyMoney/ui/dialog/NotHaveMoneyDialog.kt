package vn.xdeuhug.luckyMoney.ui.dialog

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import vn.xdeuhug.base.BaseDialog
import vn.xdeuhug.base.action.AnimAction
import vn.xdeuhug.luckyMoney.R
import vn.xdeuhug.luckyMoney.cache.MusicCache
import vn.xdeuhug.luckyMoney.databinding.DialogNotHaveMoneyBinding

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 17 / 01 / 2024
 */
class NotHaveMoneyDialog {
    class Builder(context: Context) :
        BaseDialog.Builder<Builder>(context) {
        private var binding: DialogNotHaveMoneyBinding =
            DialogNotHaveMoneyBinding.inflate(LayoutInflater.from(context))

        lateinit var onCompleted: OnCompleted
        //
        private var mediaPlayer: MediaPlayer? = null

        private var music = MusicCache.getMusic()

        fun onCompleted(onCompleted: OnCompleted): Builder = apply {
            this.onCompleted = onCompleted
        }

        init {
            setContentView(binding.root)
            setAnimStyle(AnimAction.ANIM_TOAST)
            setCanceledOnTouchOutside(false)
            setBackgroundDimEnabled(true)
            setCancelable(false)
            setClickButton()
        }

        override fun addOnDismissListener(listener: BaseDialog.OnDismissListener): Builder {
            if(mediaPlayer != null)
            {
                mediaPlayer!!.stop()
            }
            return super.addOnDismissListener(listener)

        }

        override fun addOnCancelListener(listener: BaseDialog.OnCancelListener): Builder {
            if(mediaPlayer != null)
            {
                mediaPlayer!!.stop()
            }
            return super.addOnCancelListener(listener)
        }

        private fun setClickButton() {
            binding.btnAdd.setOnClickListener {
                playAudioPlayer()
                dismiss()
                onCompleted.onAdd()
            }

            binding.btnBack.setOnClickListener {
                playAudioPlayer()
                dismiss()
                onCompleted.onClose()
            }

        }

        private fun playAudioPlayer() {
            //set up MediaPlayer
            if(music.isSaveCache && music.sound)
            {
                try {
                    if(mediaPlayer == null)
                    {
                        mediaPlayer = MediaPlayer.create(getContext(), R.raw.tap_notification)
                    }
                    mediaPlayer!!.start()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        interface OnCompleted {
            fun onClose()
            fun onAdd()
        }
    }
}