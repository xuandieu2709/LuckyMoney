package vn.xdeuhug.luckyMoney.ui.dialog

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import vn.xdeuhug.base.BaseDialog
import vn.xdeuhug.base.action.AnimAction
import vn.xdeuhug.luckyMoney.R
import vn.xdeuhug.luckyMoney.cache.MusicCache
import vn.xdeuhug.luckyMoney.databinding.DialogSoundBinding

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 15 / 01 / 2024
 */
class SoundDialog {
    class Builder(context: Context) :
        BaseDialog.Builder<Builder>(context) {
        private var binding: DialogSoundBinding =
            DialogSoundBinding.inflate(LayoutInflater.from(context))

        lateinit var onCompleted: OnCompleted
        //
        private var mediaPlayer: MediaPlayer? = null

        private var music = MusicCache.getMusic()

        fun onCompleted(onCompleted: OnCompleted): Builder = apply {
            this.onCompleted = onCompleted
        }

        init {
            setContentView(binding.root)
            setViewMusic()
            setAnimStyle(AnimAction.ANIM_TOAST)
            setCanceledOnTouchOutside(false)
            setBackgroundDimEnabled(true)
            setCancelable(false)
            setButtonCheckedChange()
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
            binding.btnSave.setOnClickListener {
                playAudioPlayer()
                dismiss()
                onCompleted.onClose()
            }
        }

        private fun setButtonCheckedChange() {
            binding.btnMusic.onCheckedChange { _, isChecked ->
                music.music = isChecked
                MusicCache.saveMusic(music)
//                playAudioPlayer()
            }

            binding.btnSound.onCheckedChange { _, isChecked ->
                music.sound = isChecked
                MusicCache.saveMusic(music)
//                playAudioPlayer()
            }
        }

        private fun setViewMusic() {
            if(music.isSaveCache)
            {
                binding.btnSound.isChecked = music.sound
                binding.btnMusic.isChecked = music.music
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
        }
    }
}