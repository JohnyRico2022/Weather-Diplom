package ru.nikita.weatherdiplom.utils.dialogManager

import android.app.AlertDialog
import android.content.Context
import ru.nikita.weatherdiplom.R

object AccessDialog {
    fun accessLocationLimited(context: Context, listener: DialogClickListener) {
        AlertDialog.Builder(context)
            .setIcon(R.drawable.ic_error_24_black)
            .setTitle(R.string.attention)
            .setMessage(R.string.location_question)
            .setPositiveButton(R.string.ok) { _, _ ->
                listener.onClick()
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .show()
    }
}