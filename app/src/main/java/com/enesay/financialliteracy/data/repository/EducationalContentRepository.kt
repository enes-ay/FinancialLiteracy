package com.enesay.financialliteracy.data.repository

import com.enesay.financialliteracy.data.datasource.EducationalContentDataSource
import com.enesay.financialliteracy.model.Education.EducationalContent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EducationalContentRepository @Inject constructor(
    private val dataSource: EducationalContentDataSource
) {
    fun getEducationalContents(): Flow<List<EducationalContent>> = dataSource.getEducationalContents()
    fun getEducationalContentDetail(categoryId: String): Flow<EducationalContent?> = dataSource.getContentDetail(categoryId)
    fun getLatestBalance(userId: String): Flow<Double?> = dataSource.getLatestBalance(userId)
}
