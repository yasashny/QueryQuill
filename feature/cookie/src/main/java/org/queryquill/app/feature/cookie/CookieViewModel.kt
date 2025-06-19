/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

package org.queryquill.app.feature.cookie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.queryquill.app.core.data.CookieRepository

internal class CookieViewModel(
    private val repository: CookieRepository

) : ViewModel() {

    private val _cookieState = MutableStateFlow<CookieUiState>(CookieUiState.Loading)
    val cookieState = _cookieState.onStart { loadData() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), CookieUiState.Loading)

    companion object {
        private const val DEFAULT_COOKIE =
            "a=b; Expires=Mon, 1 Jan 2345 11:11:23 GMT; Domain=example.com; Path=/"
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getCookies().collect { list ->
                _cookieState.update {
                    CookieUiState.Success(list.map { el ->
                        CookieModel(el)
                    })
                }
            }
        }
    }

    fun onEvent(updateCookie: UpdateCookie) {
        when (updateCookie) {
            UpdateCookie.Add -> {
                updateState { list ->
                     list + listOf(CookieModel(DEFAULT_COOKIE))
                }
            }

            is UpdateCookie.Delete -> {
                updateState { list ->
                    list.filterIndexed { index, _ -> index != updateCookie.id }
                }

            }

            is UpdateCookie.Update -> {
                updateState { list ->
                    list.mapIndexed { index, cookie ->
                        if (index == updateCookie.id) updateCookie.newCookieState else cookie
                    }
                }
            }
        }
    }

    private fun updateState(transform: (List<CookieModel>) -> List<CookieModel>) {
        val currentState = cookieState.value
        if (currentState is CookieUiState.Success) {
            _cookieState.update {
                CookieUiState.Success(transform(currentState.list))
            }
        }
    }

    fun saveCookie() {
        (cookieState.value as? CookieUiState.Success)?.let { state ->
            viewModelScope.launch {
                repository.updateCookie(state.list.map { cookieModel -> cookieModel.cookie })
            }
        }
    }
}