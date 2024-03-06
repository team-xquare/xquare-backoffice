package com.xaquare.xquarebackoffice.infrastructure.excel.presentation

import com.xaquare.xquarebackoffice.infrastructure.excel.service.FindAllUserService
import com.xaquare.xquarebackoffice.infrastructure.excel.service.FindUserByAccountIdService
import com.xaquare.xquarebackoffice.infrastructure.excel.service.GetUserInfo
import com.xaquare.xquarebackoffice.infrastructure.excel.service.SaveUserInfo
import com.xaquare.xquarebackoffice.infrastructure.excel.service.GetExcelSheetService

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import javax.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PathVariable

@RestController
@RequestMapping("/excel")
class ExcelController(
    private val saveUserInfo: SaveUserInfo,
    private val getExcelSheetService: GetExcelSheetService,
    private val getUserInfo: GetUserInfo,
    private val findUserByAccountIdService: FindUserByAccountIdService,
    private val findAllUserService: FindAllUserService
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

    @GetMapping("/find/{accountId}")
    fun findUser(@PathVariable(name = "accountId") accountId: String) =
        findUserByAccountIdService.execute(accountId)

    @GetMapping("/find/all")
    fun findAllUser() = findAllUserService.execute()
}
