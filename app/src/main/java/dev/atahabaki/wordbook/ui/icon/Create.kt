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

val Create = ImageVector.Builder(
    name = "Create",
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
        moveTo(10.363036f, 21.636965f)
        curveTo(8.242023f, 21.091309f, 6.1210546f, 20.545666f, 6.5605574f, 21.272854f)
        curveTo(7.00006f, 22.00004f, 9.99994f, 23.99996f, 10.33328f, 24.99998f)
        curveTo(10.66662f, 26f, 8.333333f, 26f, 6f, 26f)
    }
    path(
        fill = SolidColor(Color(0xFF000000))
    ) {
        moveTo(10.363036f, 21.636965f)
        lineTo(12f, 17f)
        lineTo(23f, 6f)
        lineTo(26f, 9f)
        lineTo(15f, 20f)
        close()
    }
}.build()

@Preview
@Composable
private fun CreateIcon() {
    Icon(
        imageVector = Create,
        contentDescription = null
    )
}