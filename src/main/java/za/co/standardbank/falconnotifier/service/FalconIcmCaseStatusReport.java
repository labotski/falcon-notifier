package za.co.standardbank.falconnotifier.service;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import za.co.standardbank.falconnotifier.entites.falcon.CsCaseStatusEntity;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

public class FalconIcmCaseStatusReport implements Report<CsCaseStatusEntity> {

    @Override
    public void generate(List<CsCaseStatusEntity> list) throws Exception {
        InputStream file = NotifierService.class.getClassLoader().getResourceAsStream("report_falcon_icm_case_status.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        Iterator<Sheet> sheetIterator = workbook.iterator();
        while (sheetIterator.hasNext()) {
            int i = 1;
            Sheet sheet = sheetIterator.next();
            if (sheet.getSheetName().equalsIgnoreCase("Export Worksheet")) {
                for (CsCaseStatusEntity entity : list) {
                    int j = 0;
                    Row row = sheet.createRow(i++);
                    createRowCell(row, j++, entity.getCaseId());
                    createRowCell(row, j++, entity.getCaseStatus());
                    createRowCell(row, j++, entity.getLastUpdate().toString());
                    createRowCell(row, j++, entity.getCreated().toString());
                }
            }
        }
        createFile(workbook, "FalconIcmCaseStatus/", fileName());
        file.close();
        workbook.close();
    }
}
