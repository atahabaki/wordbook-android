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

import androidx.annotation.StringRes
import dev.atahabaki.wordbook.R
import java.util.concurrent.TimeUnit

enum class NotificationPeriod(val value: Int, @StringRes val period: Int,
                              val repeatInterval: Long, val repeatIntervalTimeUnit: TimeUnit,
                              val flexInterval: Long, val flexIntervalTimeUnit: TimeUnit) {
    MIN_15(0, R.string.period_min_15,
        15, TimeUnit.MINUTES,
        5, TimeUnit.MINUTES),
    MIN_30(1, R.string.period_min_30,
        30, TimeUnit.MINUTES,
        10, TimeUnit.MINUTES),
    HOUR_1(2, R.string.period_1_hour,
        1, TimeUnit.HOURS,
        15, TimeUnit.MINUTES),
    HOURS_2(3, R.string.period_2_hours,
        2, TimeUnit.HOURS,
        30, TimeUnit.MINUTES),
    HOURS_4(4, R.string.period_4_hours,
        4, TimeUnit.HOURS,
        1, TimeUnit.HOURS),
    DAILY(5, R.string.period_daily,
        1, TimeUnit.DAYS,
        2, TimeUnit.HOURS),
}