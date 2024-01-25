package com.xaquare.xquarebackoffice.infrastructure.excel.service

import com.xaquare.xquarebackoffice.infrastructure.excel.dto.ExcelData
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import org.apache.commons.io.FilenameUtils
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.springframework.beans.factory.annotation.Value
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Service
class GetExcelSheetService(
    @Value("\${service.scheme}")
    private val scheme: String,
    @Value("\${service.host}")
    private val host: String,
    @Value("\${service.port}")
    private val port: Int,
    @Value("\${service.database}")
    private val database: String,
    @Value("\${service.username}")
    private val username: String,
    @Value("\${service.password}")
    private val password: String
) {

    @Transactional
    fun execute(file: MultipartFile) {
        val dataList: MutableList<ExcelData> = ArrayList()

        val extension = FilenameUtils.getExtension(file.originalFilename)

        if (extension != "xlsx" && extension != "xls") {
            TODO("예외처리")
        }

        var workbook: Workbook? = null

        try {
            if (extension == "xlsx") {
                workbook = XSSFWorkbook(file.inputStream)
            } else if (extension == "xls") {
                workbook = HSSFWorkbook(file.inputStream)
            }

            val worksheet = workbook!!.getSheetAt(0)

            for (i in 2 until worksheet.physicalNumberOfRows) {
                val row = worksheet.getRow(i)
                val cellType1 = row.getCell(1).cellType
                val cellType3 = row.getCell(3).cellType
                val cellType4 = row.getCell(4).cellType
                val cellType5 = row.getCell(5).cellType


                val excelData = ExcelData(
                    name = row.getCell(0).stringCellValue.toString(),
                    entranceYear = when (cellType1) {
                        CellType.STRING -> row.getCell(1).stringCellValue
                        CellType.NUMERIC -> row.getCell(1).numericCellValue.toInt().toString()
                        else -> null
                    }?: "",
                    birthDay = getLocalDateFromString(row.getCell(2).toString())?.toString() ?: "",
                    grade = when (cellType3) {
                        CellType.STRING -> row.getCell(3).stringCellValue
                        CellType.NUMERIC -> row.getCell(3).numericCellValue.toInt().toString()
                        else -> null
                    } ?:"",
                    classNum = when (cellType4) {
                        CellType.STRING -> row.getCell(4).stringCellValue
                        CellType.NUMERIC -> row.getCell(4).numericCellValue.toInt().toString()
                        else -> null
                    } ?:"",
                    num = when (cellType5) {
                        CellType.STRING -> row.getCell(5).stringCellValue
                        CellType.NUMERIC -> row.getCell(5).numericCellValue.toInt().toString()
                        else -> null
                    } ?:"",
                )
                dataList.add(excelData)
            }
            saveExcelDataToDB(dataList)
        } finally {
            workbook?.close()
        }
    }

    private fun saveExcelDataToDB(dataList: List<ExcelData>) {
        val jdbcUrl = "${scheme}://${host}:${port}/${database}"
        val username = username
        val password = password

        var connection: Connection? = null
        var preparedStatement: PreparedStatement? = null

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password)
            connection.autoCommit = false

            val sql = """
                INSERT INTO excel (name, entrance_year, birth_day, grade, class_num, num)
                VALUES (?, ?, ?, ?, ?, ?)
            """.trimIndent()

            preparedStatement = connection.prepareStatement(sql)

            for (excelData in dataList) {
                preparedStatement.setString(1, excelData.name)
                preparedStatement.setString(2, excelData.entranceYear)
                preparedStatement.setString(3, excelData.birthDay)
                preparedStatement.setString(4, excelData.grade)
                preparedStatement.setString(5, excelData.classNum)
                preparedStatement.setString(6, excelData.num)
                preparedStatement.addBatch()
            }

            preparedStatement.executeBatch()
            connection.commit()
        } catch (e: SQLException) {
            connection?.rollback()
            throw e
        } finally {
            preparedStatement?.close()
            connection?.close()
        }
    }

    private fun getLocalDateFromString(dateString: String): LocalDate? {
        val format = DateTimeFormatter.ofPattern("yyyy,MM,dd")
        return try {
            LocalDate.parse(dateString, format)
        } catch (e: Exception) {
            null
            //입력 형태 오류
        }
    }
}
