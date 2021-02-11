package com.bezkoder.spring.security.postgresql.utils;

import com.bezkoder.spring.security.postgresql.models.Product;
import com.bezkoder.spring.security.postgresql.models.Size;
import com.bezkoder.spring.security.postgresql.models.Sticker;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExcelExport {
    public static String parseDate(Date date){
        String pattern = "dd-MM-yyyy HH:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String newDate = simpleDateFormat.format(date);
        return newDate;
    }
    public static ByteArrayInputStream barcodeExcel(List<Sticker> stickers, Integer super_id) throws IOException {
        String[] COLUMNs = {"Заказ №", "ID клиента","Штрих-код", "Время заказа ","Размер", "Количество",
                "Ценник", "Имя заказчика", "Адрес","Телефон", "Комментарий"};
        try(
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();
        ){
            CreationHelper createHelper = workbook.getCreationHelper();

            Sheet sheet = workbook.createSheet("Баркод");

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setColor(IndexedColors.BLUE.getIndex());

            CellStyle headerCellStyle = workbook.createCellStyle();
            headerCellStyle.setFont(headerFont);

            // Row for Header
            Row headerRow = sheet.createRow(0);

            // Header
            for (int col = 0; col < COLUMNs.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(COLUMNs[col]);
                cell.setCellStyle(headerCellStyle);
            }

            int rowIdx = 1;
            for (Sticker sticker : stickers) {
                if (super_id == sticker.getId()) {
                for (Product product : sticker.getProducts()) {
                    for (Size size : product.getSizes()) {

                        Row row = sheet.createRow(rowIdx++);

                        row.createCell(0).setCellValue(sticker.getId());
                        row.createCell(1).setCellValue(sticker.getClient_id());
                        row.createCell(2).setCellValue(product.getBarcode());
                        row.createCell(3).setCellValue(parseDate(sticker.getCreatedAt()));
                        row.createCell(4).setCellValue(size.getSize());
                        row.createCell(5).setCellValue(size.getAmount());
                        row.createCell(6).setCellValue(product.getPrice());
                        row.createCell(7).setCellValue(sticker.getName());
                        row.createCell(8).setCellValue(sticker.getAddress());
                        row.createCell(9).setCellValue(sticker.getPhone());
                        row.createCell(10).setCellValue(sticker.getComment());
                    }
                }
                }
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
