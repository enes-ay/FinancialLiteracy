package com.enesay.financialliteracy.model.Education

import androidx.annotation.Keep

@Keep
data class EducationalContent(
    val id: String = "",
    val content_name: String = "",
    val content_detail: String = "",
    val sections : List<SectionDetail> = emptyList()
)

@Keep
data class SectionDetail(
    val section_title: String = "",
    val section_detail: String = "",
)
