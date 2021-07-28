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

import dev.atahabaki.wordbook.R
import dev.atahabaki.wordbook.data.about.SocialLink

const val DICTIONARY_TABLE_NAME = "wordbook"
const val DATABASE_NAME = "wordbook.db"
const val FEEDBACK_URI = "https://github.com/atahabaki/wordbook-android/issues/"
const val COFFEE_URI = "https://www.buymeacoffee.com/atahabaki"

val SOCIAL_LINKS: List<SocialLink> = listOf(
    SocialLink(R.string.twitter, R.string.twitter_uri, R.drawable.ic_twitter),
    SocialLink(R.string.instagram, R.string.instagram_uri, R.drawable.ic_instagram),
    SocialLink(R.string.buymeacoffee, R.string.buymeacoffee_uri, R.drawable.ic_buymeacoffee),
    SocialLink(R.string.github, R.string.github_uri, R.drawable.ic_github),
    SocialLink(R.string.reddit, R.string.reddit_uri, R.drawable.ic_reddit),
    SocialLink(R.string.linkedin, R.string.linkedin_uri, R.drawable.ic_linkedin),
    SocialLink(R.string.youtube, R.string.youtube_uri, R.drawable.ic_youtube),
    SocialLink(R.string.medium, R.string.medium_uri, R.drawable.ic_medium),
    SocialLink(R.string.discord, R.string.discord_uri, R.drawable.ic_discord),
    SocialLink(R.string.telegram, R.string.telegram_uri, R.drawable.ic_telegram),
)