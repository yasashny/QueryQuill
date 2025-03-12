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

    // For LazyColumn key
    private var lastCookieId = 0

    companion object {
        private const val DEFAULT_COOKIE =
            "a=b; Expires=Mon, 1 Jan 2345 11:11:23 GMT; Domain=example.com; Path=/"
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getCookies().collect { list ->
                _cookieState.update {
                    CookieUiState.Success(list.map { el -> CookieModel(lastCookieId++, el) })
                }
            }
        }
    }

    fun onEvent(updateCookie: UpdateCookie) {
        when (updateCookie) {
            UpdateCookie.Add -> {
                updateState { list ->
                    listOf(CookieModel(lastCookieId++, DEFAULT_COOKIE)) + list
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