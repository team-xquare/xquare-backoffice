package com.xaquare.xquarebackoffice.infrastructure.excel.service

import com.xaquare.xquarebackoffice.infrastructure.excel.ExcelProperties
import com.xaquare.xquarebackoffice.infrastructure.excel.dto.ExcelData
import com.xaquare.xquarebackoffice.infrastructure.excel.exception.DBAccessException
import com.xaquare.xquarebackoffice.infrastructure.excel.exception.DataFormatException
import com.xaquare.xquarebackoffice.infrastructure.excel.service.query.ExcelQuery
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.apache.commons.io.FilenameUtils
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.sql.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Service
class GetExcelSheetService(
    private val properties: ExcelProperties,
    private val excelQuery: ExcelQuery
) {

    fun execute(file: MultipartFile) {
        val dataList: MutableList<ExcelData> = ArrayList()

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

                val excelData = ExcelData(
                    name = row.getCell(0).stringCellValue.toString(),
                    entranceYear = row.getCell(1).numericCellValue.toInt(),
                    birthDay = getLocalDateFromString(row.getCell(2).toString())?.toString() ?: "",
                    grade = row.getCell(3).numericCellValue.toInt(),
                    classNum = row.getCell(4).numericCellValue.toInt(),
                    num = row.getCell(5).numericCellValue.toInt(),
                )
                dataList.add(excelData)
            }
            saveExcelDataToDB(dataList)
        } finally {
            excel?.close()
        }
    }

    private fun saveExcelDataToDB(dataList: List<ExcelData>) {
        val jdbcUrl = "${properties.scheme}://${properties.host}:${properties.port}/${properties.database}"
        val username = properties.username
        val password = properties.password

        var connection: Connection? = null
        var preparedSql: PreparedStatement? = null

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password)
            connection.autoCommit = false

            val sql = excelQuery.executeQuery()

            preparedSql = connection.prepareStatement(sql)

            preparedSql.apply {
                for (excelData in dataList) {
                    setString(1, excelData.name)
                    setInt(2, excelData.entranceYear)
                    setString(3, excelData.birthDay)
                    setInt(4, excelData.grade)
                    setInt(5, excelData.classNum)
                    setInt(6, excelData.num)
                    addBatch()
                }
            }

            preparedSql.executeBatch()
            connection.commit()
        } catch (e: SQLException) {
            connection?.rollback()
            throw DBAccessException
        } finally {
            preparedSql?.close()
            connection?.close()
        }
    }

    private fun getLocalDateFromString(dateString: String): LocalDate? {
        val format = DateTimeFormatter.ofPattern("yyyy,MM,dd")
        return try {
            LocalDate.parse(dateString, format)
        } catch (e: Exception) {
            throw DataFormatException
        }
    }
}
