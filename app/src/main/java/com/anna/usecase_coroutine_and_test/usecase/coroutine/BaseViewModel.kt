package com.anna.usecase_coroutine_and_test.usecase.coroutine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anna.usecase_coroutine_and_test.constant.UiState
import kotlinx.coroutines.CoroutineExceptionHandler

open class BaseViewModel<T> : ViewModel() {

    fun uiState(): LiveData<UiState> = uiState
    protected val uiState: MutableLiveData<UiState> = MutableLiveData()

    fun pokemonInfo(): LiveData<T> = pokemonInfo
    protected val pokemonInfo = MutableLiveData<T>()


    val coroutineException get() =  CoroutineExceptionHandler { _, throwable ->
        uiState.value = UiState.Error("Network Request failed!! $throwable")
    }

}