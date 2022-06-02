package za.co.standardbank.falconnotifier.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface Report<T> {


    public void generate(List<T> list, String name) throws Exception;

    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    public default void createRowCell(Row row, int cellIndex, String cellName) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(cellName);
    }

    public default String formatTimestamp(Timestamp t) {
        return new SimpleDateFormat("dd-MMM-YY hh.mm.ss.SSSSSSSSS a").format(t);
    }

    public default void createFile(XSSFWorkbook workbook, String folderName, String fileName)
            throws FileNotFoundException, IOException {
        try (FileOutputStream outFile = new FileOutputStream(new File(folderName + "report_" + fileName + ".xlsx"))) {
            workbook.write(outFile);
        }
    }

    public default String fileName(String name) {
        if(name == null) {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            return dateFormat.format(timestamp);
        }
        return name;
    }

}
