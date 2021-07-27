package dev.atahabaki.wordbook.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SocialLink (
    @StringRes val name: Int,
    @StringRes val uri: Int,
    @DrawableRes val drawable: Int
)