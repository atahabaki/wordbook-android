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
package dev.atahabaki.wordbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AppBarWithSearch
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.Preview
import dev.atahabaki.wordbook.ui.icon.Cancel
import dev.atahabaki.wordbook.ui.icon.Create
import dev.atahabaki.wordbook.ui.icon.LeftChevron
import dev.atahabaki.wordbook.ui.icon.Menu
import dev.atahabaki.wordbook.ui.icon.Search
import dev.atahabaki.wordbook.ui.theme.AppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                ModalNavigationDrawer(
                    drawerState= drawerState,
                    gesturesEnabled = true,
                    drawerContent = {
                        ModalDrawerSheet {
                            Text(text = getString(R.string.app_name))
                            HorizontalDivider()
//                            NavigationDrawerItem()
                        }
                    }
                ) {
                    val searchBarState = rememberSearchBarState()
                    val textFieldState = rememberTextFieldState()
                    val scrollBehavior = SearchBarDefaults.enterAlwaysSearchBarScrollBehavior()
                    val appBarWithSearchColors = SearchBarDefaults.appBarWithSearchColors(
                        searchBarColors = SearchBarDefaults.containedColors(state = searchBarState)
                    )
                    val scope = rememberCoroutineScope()
                    val inputField = @Composable {
                        SearchBarDefaults.InputField(
                            textFieldState = textFieldState,
                            searchBarState = searchBarState,
                            colors = appBarWithSearchColors.searchBarColors.inputFieldColors,
                            onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
                            placeholder = {
                                Text(modifier = Modifier.clearAndSetSemantics {}, text = "Search")
                            },
                            leadingIcon = {
                                if (searchBarState.targetValue == SearchBarValue.Expanded) {
                                    IconButton(onClick = {
                                        scope.launch {
                                            searchBarState.animateToCollapsed()
                                        }
                                    }) {
                                        Icon(LeftChevron, contentDescription = null)
                                    }
                                } else {
                                    Icon(Search, contentDescription = null)
                                }
                            },
                            trailingIcon = {
                                if (textFieldState.text.isEmpty()) {
                                    Unit
                                } else {
                                    IconButton(onClick = {
                                        scope.launch {
                                            textFieldState.clearText()
                                        }
                                    }) {
                                        Icon(Cancel, contentDescription = null)
                                    }
                                }
                            }
                        )
                    }
                    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                        AppBarWithSearch(
                            state = searchBarState,
                            scrollBehavior = scrollBehavior,
                            inputField = inputField,
                            colors = appBarWithSearchColors,
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    },
                                ) {
                                    Icon(Menu, contentDescription = null)
                                }
                            },
                            actions = {}
                        )
                        ExpandedFullScreenSearchBar(
                            state = searchBarState,
                            inputField = inputField,
                            colors = appBarWithSearchColors.searchBarColors
                        ) {
                        }
                    }) { innerPadding ->
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)) {
                            WordBookHorizontalFloatingToolbar(modifier = Modifier.align(Alignment.BottomCenter))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WordBookHorizontalFloatingToolbar(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    HorizontalFloatingToolbar(
        modifier = modifier,
        expanded = expanded,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        expanded = !expanded
                    }
                }
            ) {
                Icon(imageVector = Create, contentDescription = null)
            }
        }
    ) {
//        listOf(
//        ).forEach {
//            IconButton(onClick = {
//                expanded = false
//            }) {
//                Icon(imageVector = ImageVector.vectorResource(it), contentDescription = null)
//            }
//        }
    }
}

@Preview(showBackground = true)
@Composable
fun WordBookHorizontalFloatingToolbarPreview() {
    AppTheme {
        WordBookHorizontalFloatingToolbar()
    }
}