package com.xaquare.xquarebackoffice.infrastructure.excel.controller

import com.xaquare.xquarebackoffice.infrastructure.excel.service.CreateExcelSheetService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/excel")
class ExcelController(
    private val createExcelSheetService: CreateExcelSheetService
) {
    @GetMapping
    fun createExcelSheet(httpServletResponse: HttpServletResponse) =
        createExcelSheetService.execute(httpServletResponse)
}
