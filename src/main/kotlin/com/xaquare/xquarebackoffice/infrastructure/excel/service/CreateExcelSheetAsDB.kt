package com.xaquare.xquarebackoffice.infrastructure.excel.service

import com.xaquare.xquarebackoffice.infrastructure.excel.ExcelProperties
import com.xaquare.xquarebackoffice.infrastructure.excel.dto.ExcelData
import com.xaquare.xquarebackoffice.infrastructure.excel.service.query.ExcelQuery
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import java.sql.Connection
import java.sql.DriverManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletResponse

@Service
class CreateExcelSheetAsDB(
    private val properties: ExcelProperties,
    private val query: ExcelQuery

) {
    fun excute(response: HttpServletResponse) {

        val workbook: Workbook = XSSFWorkbook()
        val sheet: Sheet = workbook.createSheet("xquare_userInfo").apply {
            defaultColumnWidth = 30
        }

        val headerCellStyle: CellStyle = workbook.createCellStyle().apply {
            setBorderStyle(BorderStyle.THIN)
            fillForegroundColor = IndexedColors.BLACK1.index
            fillPattern = FillPatternType.SOLID_FOREGROUND
            setFont(workbook.createFont().apply {
                color = IndexedColors.WHITE.index
            })
        }

        val headerNames = arrayOf("이름", "입학년도", "생일", "학년", "반", "번호")

        val headerRow: Row = sheet.createRow(0)
        headerNames.forEachIndexed { i, header ->
            val headerCell: Cell = headerRow.createCell(i).apply {
                setCellValue(header)
                cellStyle = headerCellStyle
            }
        }

        //Body
        val bodyCellStyle: CellStyle = workbook.createCellStyle().apply {
            setBorderStyle(BorderStyle.THIN)
        }

        val bodyData = arrayOf(
            arrayOf("예시) 홍길동", "예시) 2023", "예시) 2024,01,01", "예시) 1", "예시) 1", "예시) 1"),
        )

        bodyData.forEachIndexed { i, bodyRowData ->
            val bodyRow: Row = sheet.createRow(i + 1)
            bodyRowData.forEachIndexed { j, data ->
                val bodyCell: Cell = bodyRow.createCell(j).apply {
                    setCellValue(data)
                    cellStyle = bodyCellStyle
                }
            }
        }

        val dataList: MutableList<ExcelData> = ArrayList()
        val jdbcUrl = "jdbc:mysql://${properties.host}:${properties.port}/${properties.database}"
        val username = properties.username
        val password = properties.password

        var connection: Connection? = null

        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password)

            val query = "${query.selectQuery()} ${properties.database}"
            val statement = connection.createStatement()
            val sqlResult = statement.executeQuery(query)

            while (sqlResult.next()) {
                val data = ExcelData(
                    name = sqlResult.getString("name"),
                    entranceYear = sqlResult.getInt("entrance_year"),
                    birthDay = sqlResult.getString("birth_day"),
                    grade = sqlResult.getInt("grade"),
                    classNum = sqlResult.getInt("class_num"),
                    num = sqlResult.getInt("num")
                )
                dataList.add(data)
            }
        } finally {
            connection?.close()
        }

        dataList.forEachIndexed { i, data ->
            val row: Row = sheet.createRow(i + 2).apply {
                createCell(0).setCellValue(data.name)
                createCell(1).setCellValue((data.entranceYear).toDouble())
                createCell(2).setCellValue(formatBirthday(data.birthDay))
                createCell(3).setCellValue((data.grade).toDouble())
                createCell(4).setCellValue((data.classNum).toDouble())
                createCell(5).setCellValue((data.num).toDouble())
                forEach { cell ->
                    cell.cellStyle = bodyCellStyle
                }
            }

            row.forEach { cell ->
                cell.cellStyle = bodyCellStyle
            }
        }

        val fileName = "xquare_userInfo.spreadsheetml_download"
        response.contentType = "application/xquare_userInfo.spreadsheetml.sheet"
        response.setHeader("Content-Disposition", "attachment;filename=$fileName.xlsx")
        val servletOutputStream = response.outputStream

        workbook.write(servletOutputStream)
        workbook.close()
        servletOutputStream.flush()
        servletOutputStream.close()
    }

    private fun CellStyle.setBorderStyle(style: BorderStyle) {
        borderLeft = style
        borderRight = style
        borderTop = style
        borderBottom = style
    }

    private fun formatBirthday(birthday: String): String {
        val originalString = LocalDate.parse(birthday, DateTimeFormatter.ISO_DATE)
        return originalString.format(DateTimeFormatter.ofPattern("yyyy,MM,dd"))
    }
}
