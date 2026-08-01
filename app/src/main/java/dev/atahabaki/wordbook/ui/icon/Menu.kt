/*
 *  WordBook - personal vocabulary with a clean, distraction-free experience.
 *  Copyright (C) 2026 A. Taha Baki
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
package dev.atahabaki.wordbook.ui.icon

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val Menu = ImageVector.Builder(
    name = "Menu",
    defaultWidth = 32.dp,
    defaultHeight = 32.dp,
    viewportWidth = 32f,
    viewportHeight = 32f
).apply {
    path(
        fill = SolidColor(Color(0xFF000000))
    ) {
        moveTo(5f, 21f)
        horizontalLineTo(27f)
        verticalLineTo(23f)
        horizontalLineTo(5f)
        verticalLineTo(21f)
        close()
    }
    path(
        fill = SolidColor(Color(0xFF000000))
    ) {
        moveTo(5f, 9f)
        horizontalLineTo(27f)
        verticalLineTo(11f)
        horizontalLineTo(5f)
        verticalLineTo(9f)
        close()
    }
    path(
        fill = SolidColor(Color(0xFF000000))
    ) {
        moveTo(5f, 15f)
        horizontalLineTo(27f)
        verticalLineTo(17f)
        horizontalLineTo(5f)
        verticalLineTo(15f)
        close()
    }
}.build()

@Preview
@Composable
private fun MenuIcon() {
    Icon(
        imageVector = Menu,
        contentDescription = null
    )
}
