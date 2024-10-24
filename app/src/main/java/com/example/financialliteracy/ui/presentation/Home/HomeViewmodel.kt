package com.example.financialliteracy.ui.presentation.Home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialliteracy.data.repository.EducationalContentRepository
import com.example.financialliteracy.model.Education.EducationalContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewmodel @Inject constructor(val repository: EducationalContentRepository) :
    ViewModel() {

    private val _educationalContent = MutableStateFlow<List<EducationalContent>>(emptyList())
    val educationalContent: StateFlow<List<EducationalContent>> = _educationalContent
    init {
        getEducationalContents()
    }

    fun getEducationalContents() {
        viewModelScope.launch {
            repository.getEducationalContents().collect { contentList ->
                _educationalContent.value = contentList
            }
        }

    }

}