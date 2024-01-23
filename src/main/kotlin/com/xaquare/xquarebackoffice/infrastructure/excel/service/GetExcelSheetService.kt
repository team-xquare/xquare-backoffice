package com.xaquare.xquarebackoffice.infrastructure.excel.service

import com.xaquare.xquarebackoffice.infrastructure.excel.dto.ExcelData
import org.apache.commons.io.FilenameUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Service
class GetExcelSheetService {
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

            for (i in 1 until worksheet.physicalNumberOfRows) {
                val row = worksheet.getRow(i)
                val excelData = ExcelData(
                    name = row.getCell(0).stringCellValue,
                    entranceYear = row.getCell(1).stringCellValue,
                    birthDay = row.getCell(2).stringCellValue,
                    grade = row.getCell(3).stringCellValue,
                    classNum = row.getCell(4).stringCellValue,
                    num = row.getCell(5).stringCellValue
                )
                dataList.add(excelData)
            }
        } finally {
            workbook?.close()
        }
        TODO("type에러 고치기")
    }
   
   /* private fun getLocalDateFromCell(cell: Cell): LocalDate? {
        return when (cell.cellType) {
            CellType.NUMERIC -> {
                val date = cell.dateCellValue
                val instant = Instant.ofEpochMilli(date.time)
                LocalDate.ofInstant(instant, ZoneId.systemDefault())
            }
            else -> null
        }
    } */
}
