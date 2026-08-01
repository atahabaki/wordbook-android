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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val Search = ImageVector.Builder(
    name = "Search",
    defaultWidth = 32.dp,
    defaultHeight = 32.dp,
    viewportWidth = 32f,
    viewportHeight = 32f
).apply {
    path(
        stroke = SolidColor(Color(0xFF000000)),
        strokeLineWidth = 2f,
        strokeLineCap = StrokeCap.Round,
        strokeLineJoin = StrokeJoin.Round,
    ) {
        moveToRelative(13f, 5f)
        curveToRelative(-4.418278f, 0f, -8f, 3.581722f, -8f, 8f)
        curveToRelative(0f, 4.418278f, 3.581722f, 8f, 8f, 8f)
        curveToRelative(4.418278f, 0f, 8f, -3.581722f, 8f, -8f)
        curveToRelative(0f, -4.418278f, -3.581722f, -8f, -8f, -8f)
        close()
        moveToRelative(6f, 14f)
        lineToRelative(7f, 7f)
        close()
    }
}.build()

@Preview
@Composable
private fun SearchIcon() {
    Icon(
        imageVector = Search,
        contentDescription = null
    )
}
