package com.enesay.financialliteracy.ui.presentation.Home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.enesay.financialliteracy.data.repository.AuthRepository
import com.enesay.financialliteracy.data.repository.EducationalContentRepository
import com.enesay.financialliteracy.model.Education.EducationalContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(val repository: EducationalContentRepository,
    val authRepository: AuthRepository) :
    ViewModel() {

    private val _educationalContent = MutableStateFlow<List<EducationalContent>>(emptyList())
    val educationalContent: StateFlow<List<EducationalContent>> = _educationalContent

    private val _latestBalance = MutableStateFlow<Double?>(null)
    val latestBalance: StateFlow<Double?> = _latestBalance

    init {
        val currentUser = authRepository.getCurrentUserId()
        getEducationalContents()
        if (currentUser != null) {
            getLatestBalance(currentUser)
        }
    }

    fun getEducationalContents() {
        viewModelScope.launch {
            repository.getEducationalContents().collect { contentList ->
                _educationalContent.value = contentList
            }
        }
    }

    fun getLatestBalance(userId: String){
        viewModelScope.launch {
            repository.getLatestBalance(userId).collect { balance ->
                _latestBalance.value = balance
                Log.d("home", "viewmodel balance $balance")
            }
        }
    }
}