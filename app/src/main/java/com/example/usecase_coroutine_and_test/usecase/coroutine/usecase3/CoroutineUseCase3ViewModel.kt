package com.example.usecase_coroutine_and_test.usecase.coroutine.usecase3

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.usecase_coroutine_and_test.constant.UiState
import com.example.usecase_coroutine_and_test.core.api.PokemonCline
import com.example.usecase_coroutine_and_test.core.model.response.PokemonListResponse
import com.example.usecase_coroutine_and_test.data.PokemonApiResultOrder
import com.example.usecase_coroutine_and_test.repo.PokemonRepository
import com.example.usecase_coroutine_and_test.repo.PokemonRepositoryImpl
import com.example.usecase_coroutine_and_test.usecase.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import org.mockito.internal.matchers.Null

class CoroutineUseCase3ViewModel(val repository: PokemonRepository) : BaseViewModel<Null>() {

    val sequentiallyResultOrder: LiveData<List<PokemonApiResultOrder>> get() = _sequentiallyResultOrder
    val concurrentlyResultOrder: LiveData<List<PokemonApiResultOrder>> get() = _concurrentlyResultOrder
    private var _sequentiallyResultOrder = MutableLiveData<List<PokemonApiResultOrder>>()
    private var _concurrentlyResultOrder = MutableLiveData<List<PokemonApiResultOrder>>()

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                CoroutineUseCase3ViewModel(repository = PokemonRepositoryImpl(PokemonCline.apiService))
            }
        }
    }

    fun getPokemonSequentially() {
        viewModelScope.launch {
            val one = repository.fetchPokemonList(1)
            val two = repository.fetchPokemonList(2)
            val three = repository.fetchPokemonList(3)
            val resultList: List<Result<PokemonListResponse>> = listOf(one, two, three)
            val orderList: ArrayList<PokemonApiResultOrder> = arrayListOf()
            resultList.forEachIndexed { index, result ->
                if (result.isSuccess) {
                    try {
                        Log.d("TEST", "同步 = $index + result = ${result.getOrThrow().results.first().name}")
                        orderList.add(PokemonApiResultOrder(orderNum = index, responseText= result.getOrThrow().results.first().name))
                    } catch (e: Exception) {
                        uiState.value = UiState.Error(e.message.toString())
                    }
                } else {
                    uiState.value = UiState.Error()
                }
            }

            if (orderList.isNotEmpty()) {
                _sequentiallyResultOrder.value = orderList
                uiState.value = UiState.Success
            }
        }
    }

    fun getPokemonConcurrently() {
        val oneDeferred = viewModelScope.async { repository.fetchPokemonList(1) }
        val twoDeferred = viewModelScope.async { repository.fetchPokemonList(2) }
        val threeDeferred = viewModelScope.async { repository.fetchPokemonList(3) }
        val orderList: ArrayList<PokemonApiResultOrder> = arrayListOf()
        viewModelScope.launch {
            val deferredList = awaitAll(oneDeferred, twoDeferred, threeDeferred)
            try {
                deferredList.forEachIndexed { index, result ->
                    if (result.isSuccess) {
                        Log.d("TEST", "非同步 = $index +　result = ${result.getOrThrow().results.first().name}")
                        orderList.add(PokemonApiResultOrder(index, result.getOrThrow().results.first().name))
                    } else {
                        uiState.value = UiState.Error()
                    }
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error(e.message.toString())
            }

            if (orderList.isNotEmpty()) {
                _concurrentlyResultOrder.value = orderList
                uiState.value = UiState.Success
            }
        }
    }


}