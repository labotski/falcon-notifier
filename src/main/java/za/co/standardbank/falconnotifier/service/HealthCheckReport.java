package za.co.standardbank.falconnotifier.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import za.co.standardbank.falconnotifier.entites.HealthCheckEntity;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

public class HealthCheckReport implements Report<HealthCheckEntity>{

    private String folderName;

    public HealthCheckReport(String folderName) {
        this.folderName = folderName;
    }

    @Override
    public void generate(List<HealthCheckEntity> list, String name) throws Exception {
        InputStream file = NotifierService.class.getClassLoader().getResourceAsStream("report_health_check.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        Iterator<Sheet> sheetIterator = workbook.iterator();
        while (sheetIterator.hasNext()) {
            int i = 1;
            Sheet sheet = sheetIterator.next();
            if (sheet.getSheetName().equalsIgnoreCase("Export Worksheet")) {
                for (HealthCheckEntity entity : list) {
                    int j = 0;
                    Row row = sheet.createRow(i++);
                    createRowCell(row, j++, entity.getCasesImportToICM().toString());
                    createRowCell(row, j++, entity.getCreditTrnCount().toString());
                    createRowCell(row, j++, entity.getDebitTrnCount().toString());
                    createRowCell(row, j++, entity.getCaseCreateFilterCount().toString());
                }
            }
        }
        createFile(workbook, folderName, fileName(name) + "_size_" + list.size());
        file.close();
        workbook.close();
    }
}
