package com.xaquare.xquarebackoffice.infrastructure.excel.dto

import java.time.LocalDate

data class ExcelData (
    val name: String,
    val entranceYear: Int,
    val birthDay: LocalDate,
    val grade: Int,
    val classNum: Int,
    val num: Int
)