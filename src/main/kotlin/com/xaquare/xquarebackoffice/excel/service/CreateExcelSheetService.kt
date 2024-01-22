package com.xaquare.xquarebackoffice.excel.service

import org.springframework.stereotype.Service
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import javax.servlet.http.HttpServletResponse

@Service
class CreateExcelSheetService {

    fun execute(response: HttpServletResponse) {
        val workbook: Workbook = XSSFWorkbook()
        val sheet: Sheet = workbook.createSheet("사용자 등록").apply {
            defaultColumnWidth = 30
        }

        // Header
        val headerCellStyle: CellStyle = workbook.createCellStyle().apply {
            setBorderStyle(BorderStyle.THIN)
            fillForegroundColor = IndexedColors.GREY_80_PERCENT.index
            fillPattern = FillPatternType.SOLID_FOREGROUND
            setFont(workbook.createFont().apply {
                color = IndexedColors.WHITE.index
            })
        }

        val headerNames = arrayOf("이름", "입학년도", "생일", "학년", "반", "번호")

        val headerRow: Row = sheet.createRow(0)
        headerNames.forEachIndexed { index, header ->
            val headerCell: Cell = headerRow.createCell(index).apply {
                setCellValue(header)
                cellStyle = headerCellStyle
            }
        }

        // Body
        val bodyCellStyle: CellStyle = workbook.createCellStyle().apply {
            setBorderStyle(BorderStyle.THIN)
        }

        val bodyData = arrayOf(
            arrayOf("ex) 김도경", "ex) 2023", "ex) 2007,09,19", "ex) 2", "ex) 4", "ex) 2"),
        )

        bodyData.forEachIndexed { rowIndex, bodyRowData ->
            // bodyData 배열의 각 행 에 대한 루프
            val bodyRow: Row = sheet.createRow(rowIndex + 1)
            // Excel 시트에 새로운 행을 생성하고 해당 행 객체를 bodyRow에 할당

            bodyRowData.forEachIndexed { cellIndex, data ->
                // 각 행(row)에 대한 데이터 배열(bodyRowData)의 각 열(column)에 대한 루프
                val bodyCell: Cell = bodyRow.createCell(cellIndex).apply {
                    // 현재 행(row)의 현재 열(column)에 대한 Excel 셀 객체를 생성하고 해당 셀 객체를 bodyCell 에 할당합니다.
                    setCellValue(data)
                    cellStyle = bodyCellStyle
                }
            }
        }

        //file
        val fileName = "spring_excel_download"
        response.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
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
