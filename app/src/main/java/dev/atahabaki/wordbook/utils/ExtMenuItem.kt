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

import android.view.Menu
import android.view.MenuItem
import androidx.annotation.AnimRes
import androidx.navigation.*

fun MenuItem.onNavDestinationSelected(
        navController: NavController,
        @AnimRes enterAnim: Int,
        @AnimRes exitAnim: Int): Boolean {
    fun findStartDestination(graph: NavGraph): NavDestination {
        var startDestination: NavDestination = graph
        while (startDestination is NavGraph) {
            val parent = startDestination
            startDestination = parent.findNode(parent.startDestination)!!
        }
        return startDestination
    }
    val builder = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(enterAnim)
            .setPopEnterAnim(enterAnim)
            .setExitAnim(exitAnim)
            .setPopExitAnim(exitAnim)
    if ((order and Menu.CATEGORY_SECONDARY) == 0)
        builder.setPopUpTo(findStartDestination(navController.graph).id, false)
    return try {
        navController.navigate(this.itemId, null, builder.build())
        true
    }
    catch (e: IllegalArgumentException) {
        false
    }
}