package com.xaquare.xquarebackoffice.infrastructure.excel.service

import com.xaquare.xquarebackoffice.domain.entity.User
import com.xaquare.xquarebackoffice.domain.persistence.repository.UserRepository
import org.apache.commons.io.FilenameUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class GetExcelSheetService(
    private val userRepository: UserRepository
) {

    fun execute(file: MultipartFile) {
        val dataList: MutableList<User> = ArrayList()

        val extension = FilenameUtils.getExtension(file.originalFilename)

        if (extension != "xlsx" && extension != "xls") {
            TODO("예외처리")
        }

        var excel: Workbook? = null

        try {
            if (extension == "xlsx") {
                excel = XSSFWorkbook(file.inputStream)
            } else if (extension == "xls") {
                excel = HSSFWorkbook(file.inputStream)
            }

            val firstSheet = excel!!.getSheetAt(0)

            for (i in 2 until firstSheet.physicalNumberOfRows) {
                val row = firstSheet.getRow(i)

                val userData = User(
                    name = row.getCell(0).stringCellValue.toString(),
                    accountId = row.getCell(1).stringCellValue.toString(),
                    password = row.getCell(2).stringCellValue.toString(),
                    grade = row.getCell(3).numericCellValue.toInt(),
                    classNum = row.getCell(4).numericCellValue.toInt(),
                    num = row.getCell(5).numericCellValue.toInt(),
                    profile = row.getCell(6).stringCellValue.toString()
                )
                dataList.add(userData)
            }
            saveExcelDataToDB(dataList)
        } finally {
            excel?.close()
        }
    }

    private fun saveExcelDataToDB(dataList: List<User>) {
        dataList.forEach { it ->
            val user = User(
                name = it.name,
                accountId = it.accountId,
                password = it.password,
                grade = it.grade,
                classNum = it.classNum,
                num = it.num,
                profile = it.profile
            )
            userRepository.save(user)
        }
    }
}
