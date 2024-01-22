package com.xaquare.xquarebackoffice.infrastructure.excel.service

import com.xaquare.xquarebackoffice.infrastructure.excel.dto.ExcelData
import org.apache.commons.io.FilenameUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import org.springframework.ui.Model
import org.springframework.web.multipart.MultipartFile
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@Service
class GetExcelSheetService {
    fun execute(file: MultipartFile, model: Model): String {
        val dataList: MutableList<ExcelData> = ArrayList()

        val extension = FilenameUtils.getExtension(file.originalFilename)

        if (extension != "xlsx" && extension != "xls") {
            TODO("예외처리")
        }

        var workbook: Workbook? = null

        try {
            if (extension == "xlsx") workbook = XSSFWorkbook(file.inputStream)
            else if (extension == "xls") workbook = HSSFWorkbook(file.inputStream)


            val worksheet = workbook!!.getSheetAt(0)

            for (i in 1 until worksheet.physicalNumberOfRows) {
                val row = worksheet.getRow(i)

                val excelData = ExcelData(
                    name = row.getCell(0).stringCellValue,
                    entranceYear = row.getCell(1).numericCellValue.toInt(),
                    birthDay = getLocalDateFromCell(row.getCell(2)) ?: LocalDate.now(),
                    grade = row.getCell(3).numericCellValue.toInt(),
                    classNum = row.getCell(4).numericCellValue.toInt(),
                    num = row.getCell(5).numericCellValue.toInt()
                )


                dataList.add(excelData)
            }

            model.addAttribute("datas", dataList)
        } finally {
            workbook?.close()
        }

        return "excelList"
    }

    private fun getLocalDateFromCell(cell: Cell): LocalDate? {
        return when (cell.cellType) {
            CellType.NUMERIC -> {
                val date = cell.dateCellValue
                val instant = Instant.ofEpochMilli(date.time)
                LocalDate.ofInstant(instant, ZoneId.systemDefault())
            }
            else -> null
        }
    }
}
