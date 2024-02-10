package com.xaquare.xquarebackoffice.infrastructure.excel.controller

import com.xaquare.xquarebackoffice.infrastructure.excel.service.CreateExcelSheetAsDB
import com.xaquare.xquarebackoffice.infrastructure.excel.service.CreateExcelSheetService
import com.xaquare.xquarebackoffice.infrastructure.excel.service.GetExcelSheetService

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/excel")
class ExcelController(
    private val createExcelSheetService: CreateExcelSheetService,
    private val getExcelSheetService: GetExcelSheetService,
    private val createExcelSheetAsDB: CreateExcelSheetAsDB
) {
    @GetMapping
    fun createExcelSheet(httpServletResponse: HttpServletResponse) =
        createExcelSheetService.execute(httpServletResponse)

    @PostMapping
    fun saveExcelInfo(file: MultipartFile) =
        getExcelSheetService.execute(file)

    @GetMapping("/userInfo")
    fun createExcelSheetAsDD(httpServletResponse: HttpServletResponse) =
        createExcelSheetAsDB.execute(httpServletResponse)
}
