package ru.nikita.weatherdiplom.utils.dialogManager

import android.app.AlertDialog
import android.content.Context
import ru.nikita.weatherdiplom.R

object LocationDialog {
    fun settingsLocation(context: Context, listener: DialogClickListener) {
        AlertDialog.Builder(context)
            .setIcon(R.drawable.ic_info_outline_24)
            .setTitle(R.string.attention)
            .setMessage(R.string.gps_question)
            .setPositiveButton(R.string.ok) { _, _ ->
                listener.onClick()
            }
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .show()
    }
}