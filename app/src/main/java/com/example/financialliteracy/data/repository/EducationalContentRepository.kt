package com.example.financialliteracy.data.repository

import com.example.financialliteracy.data.datasource.EducationalContentDataSource
import com.example.financialliteracy.model.Education.EducationalContent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EducationalContentRepository @Inject constructor(
    private val dataSource: EducationalContentDataSource
) {
    fun getEducationalContents(): Flow<List<EducationalContent>> = dataSource.getEducationalContents()
}
