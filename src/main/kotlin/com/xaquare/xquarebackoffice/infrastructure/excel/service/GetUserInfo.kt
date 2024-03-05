package com.xaquare.xquarebackoffice.infrastructure.excel.service

import com.xaquare.xquarebackoffice.domain.entity.User
import com.xaquare.xquarebackoffice.domain.persistence.UserPersistenceAdapter
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletResponse
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.springframework.transaction.annotation.Transactional

@Service
class GetUserInfo(
    private val userPersistenceAdapter: UserPersistenceAdapter
) {

    @Transactional(readOnly = true)
    fun execute(response: HttpServletResponse) {
        val workbook: Workbook = XSSFWorkbook()
        val sheet: Sheet = workbook.createSheet("xquare_userInfo").apply {
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

        val headerNames = arrayOf("이름", "아이디", "비밀번호", "학년", "반", "번호", "프로필사진")

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

        val userList: List<User> = userPersistenceAdapter.findAll()

        userList.forEachIndexed { index, user ->
            val bodyRow: Row = sheet.createRow(index + 1)
            bodyRow.createCell(0).setCellValue(user.name)
            bodyRow.createCell(1).setCellValue(user.accountId)
            bodyRow.createCell(2).setCellValue(user.password)
            bodyRow.createCell(3).setCellValue(user.grade.toDouble())
            bodyRow.createCell(4).setCellValue(user.classNum.toDouble())
            bodyRow.createCell(5).setCellValue(user.num.toDouble())
            bodyRow.createCell(6).setCellValue(user.profile)
            bodyRow.forEach { cell ->
                cell.cellStyle = bodyCellStyle
            }
        }

        // File
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
}
