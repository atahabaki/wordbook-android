/*
 *  WordBook - An android application for those who aims to learn a new language.
 *  Copyright (C) 2021 A. Taha Baki
 *
 *  This file is part of WordBook.
 *
 *  WordBook is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  WordBook is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with WordBook.  If not, see <https://www.gnu.org/licenses/>.
 */

package dev.atahabaki.wordbook.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dev.atahabaki.wordbook.workers.ReminderWorker

fun Context.toggleReminderWorkers(isDisabled: Boolean,
        period: NotificationPeriod,
        channelID: String) {
    val workManager = WorkManager.getInstance(this)
    if (isDisabled) {
            workManager.cancelUniqueWork(ReminderWorker.CHANNEL_ID)
    }
    else {
        val workReq = PeriodicWorkRequestBuilder<ReminderWorker>(
            period.repeatInterval, period.repeatIntervalTimeUnit,
            period.flexInterval, period.flexIntervalTimeUnit).build()
        workManager.enqueueUniquePeriodicWork(channelID, ExistingPeriodicWorkPolicy.KEEP, workReq)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun Context.toggleReminderNotifications(isDisabled: Boolean, channel: NotificationChannel) {
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as
            NotificationManager
    when (isDisabled) {
        true -> notificationManager.deleteNotificationChannel(channel.id)
        else -> notificationManager.createNotificationChannel(channel)
    }
}