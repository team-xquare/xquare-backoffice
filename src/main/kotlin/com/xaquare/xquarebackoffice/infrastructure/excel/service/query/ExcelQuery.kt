package com.xaquare.xquarebackoffice.infrastructure.excel.service.query

import org.springframework.stereotype.Component

@Component
class ExcelQuery {
    fun executeQuery(): String {
        return """
            INSERT INTO excel (name, entrance_year, birth_day, grade, class_num, num)
            VALUES (?, ?, ?, ?, ?, ?)
        """
    }

    fun selectQuery(): String {
        return """
            SELECT * FROM
        """.trimIndent()
    }
}