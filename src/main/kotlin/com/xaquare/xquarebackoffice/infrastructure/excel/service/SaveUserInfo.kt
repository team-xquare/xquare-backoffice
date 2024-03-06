package com.xaquare.xquarebackoffice.infrastructure.excel.service

import org.springframework.stereotype.Service
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import javax.servlet.http.HttpServletResponse
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook

@Service
class SaveUserInfo {

    fun execute(response: HttpServletResponse) {
        val workbook: Workbook = XSSFWorkbook()
        val sheet: Sheet = workbook.createSheet("xquare").apply {
            defaultColumnWidth = 30
        }

        // Header
        val headerCellStyle: CellStyle = workbook.createCellStyle().apply {
            setBorderStyle(BorderStyle.THIN)
            fillForegroundColor = IndexedColors.BLACK1.index
            fillPattern = FillPatternType.SOLID_FOREGROUND
            setFont(
                workbook.createFont().apply {
                    color = IndexedColors.WHITE.index
                }
            )
        }

        val headerNames = arrayOf("이름", "아이디", "비밀번호", "학년", "반", "번호", "프로필 사진")

        val headerRow: Row = sheet.createRow(0)
        headerNames.forEachIndexed { i, header ->
            val headerCell: Cell = headerRow.createCell(i).apply {
                setCellValue(header)
                cellStyle = headerCellStyle
            }
        }

        // Body
        val bodyCellStyle: CellStyle = workbook.createCellStyle().apply {
            setBorderStyle(BorderStyle.THIN)
        }

        val bodyData = arrayOf(
            arrayOf("예시) 홍길동", "예시) gildong", "예시) Gildong1!", "예시) 1", "예시) 1", "예시) 1", "예시) http:// ")
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

        // File
        val fileName = "xquare.spreadsheetml_download"
        response.contentType = "application/xquare.spreadsheetml.sheet"
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
}
