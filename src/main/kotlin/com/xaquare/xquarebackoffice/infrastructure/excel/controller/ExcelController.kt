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
import org.springframework.web.bind.annotation.RequestParam

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
    fun saveExcelInfo(@RequestParam(name = "scheme")scheme: String, @RequestParam(name = "host")host: String, @RequestParam(name = "port")port: Int, @RequestParam(name = "database")database: String, @RequestParam(name = "username")username: String, @RequestParam(name = "password")password: String, file: MultipartFile) =
        getExcelSheetService.execute(scheme, host, port, database, username, password, file)

    @GetMapping("/userInfo")
    fun createExcelSheetAsDD(@RequestParam(name = "scheme")scheme: String, @RequestParam(name = "host")host: String, @RequestParam(name = "port")port: Int, @RequestParam(name = "database")database: String, @RequestParam(name = "username")username: String, @RequestParam(name = "password")password: String, httpServletResponse: HttpServletResponse) =
        createExcelSheetAsDB.execute(scheme, host, port, database, username, password, httpServletResponse)
}
