package com.example.financialliteracy.ui.presentation.CategoryDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialliteracy.data.repository.EducationalContentRepository
import com.example.financialliteracy.model.Education.EducationalContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryDetailViewmodel @Inject constructor(val repository: EducationalContentRepository) : ViewModel() {
    private val _categoryDetail = MutableStateFlow<EducationalContent?>(null)
    val categoryDetail: StateFlow<EducationalContent?> = _categoryDetail

    fun getCategoryDetail(categoryId: String) {
        viewModelScope.launch {
            repository.getEducationalContentDetail(categoryId)
                .catch { e -> Log.e("Error:", e.localizedMessage) }
                .collect { detail ->
                    _categoryDetail.value = detail
                }
        }
    }
}