package com.xaquare.xquarebackoffice.infrastructure.excel.controller

import com.xaquare.xquarebackoffice.infrastructure.excel.service.GetUserInfo
import com.xaquare.xquarebackoffice.infrastructure.excel.service.SaveUserInfo
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
    private val saveUserInfo: SaveUserInfo,
    private val getExcelSheetService: GetExcelSheetService,
    private val getUserInfo: GetUserInfo
) {
    @GetMapping
    fun createExcelSheet(httpServletResponse: HttpServletResponse) =
        saveUserInfo.execute(httpServletResponse)

    @PostMapping
    fun saveExcelInfo(file: MultipartFile) =
        getExcelSheetService.execute(file)

    @GetMapping("/userInfo")
    fun getUserInfo(httpServletResponse: HttpServletResponse) =
        getUserInfo.execute(httpServletResponse)
}
