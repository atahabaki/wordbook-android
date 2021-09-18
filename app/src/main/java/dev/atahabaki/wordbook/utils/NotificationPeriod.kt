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

enum class NotificationPeriod(val value: Int, @StringRes val period: Int) {
    MIN_15(0, R.string.period_min_15),
    MIN_30(1, R.string.period_min_30),
    HOUR_1(2, R.string.period_1_hour),
    HOURS_2(3, R.string.period_2_hours),
    HOURS_4(4, R.string.period_4_hours),
    DAILY(5, R.string.period_daily)
}