package za.co.standardbank.falconnotifier.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import za.co.standardbank.falconnotifier.entites.falcon.CsCaseStatusEntity;
import za.co.standardbank.falconnotifier.entites.falcon.FalconEntity;

@Service
public class NotifierService {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

	public void createRowCell(Row row, int cellIndex, String cellName) {
		Cell cell = row.createCell(cellIndex);
		cell.setCellValue(cellName);
	}

	private String formatTimestamp(Timestamp t) {
		return new SimpleDateFormat("dd-MMM-YY hh.mm.ss.SSSSSSSSS a").format(t);
	}

	public void generateReport(List<FalconEntity> data) throws IOException {
		InputStream file = NotifierService.class.getClassLoader().getResourceAsStream("report.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		Iterator<Sheet> sheetIterator = workbook.iterator();
		while (sheetIterator.hasNext()) {
			int i = 1;
			Sheet sheet = sheetIterator.next();
			if (sheet.getSheetName().equalsIgnoreCase("Export Worksheet")) {
				for (FalconEntity entity : data) {
					int j = 0;
					Row row = sheet.createRow(i++);
					createRowCell(row, j++, entity.getCaseHistoryId());
					createRowCell(row, j++, entity.getCaseId());
					createRowCell(row, j++, entity.getTenantCd());
					createRowCell(row, j++, entity.getCaseReferenceNum());
					createRowCell(row, j++, entity.getCaseLevelCd());
					createRowCell(row, j++, entity.getCaseTypev2Cd());
					createRowCell(row, j++, entity.getCclientXid());
					createRowCell(row, j++, formatTimestamp(entity.getCactionDttm()));
					createRowCell(row, j++, entity.getFraudTypeCd());
					createRowCell(row, j++, entity.getQueueName());
					createRowCell(row, j++, entity.getCaseStatusTypeCd());
					createRowCell(row, j++, entity.getCaseStatusCd());
					createRowCell(row, j++, entity.getActionUserPartyId());
					createRowCell(row, j++, entity.getAccountReferenceXid());
					createRowCell(row, j++, entity.getCaseActionCd1());
					createRowCell(row, j++, entity.getCaseActionCd2());
					createRowCell(row, j++, entity.getCaseActionCd3());
					createRowCell(row, j++, formatTimestamp(entity.getCaseCreatedDttm()));
					createRowCell(row, j++, formatTimestamp(entity.getCaseOpenedDttm()));
					createRowCell(row, j++, entity.getProtectedAmt());
					createRowCell(row, j++, entity.getCreateOpenLossAmt());
					createRowCell(row, j++, entity.getOpenClosedLossAmt());
					createRowCell(row, j++, entity.getClosedLossAmt());
					createRowCell(row, j++, entity.getUserPartyId());
					createRowCell(row, j++, entity.getFirstName());
					createRowCell(row, j++, entity.getLastName());
					createRowCell(row, j++, entity.getActiveTennantCd());
					createRowCell(row, j++, formatTimestamp(entity.getCreatedDttm()));
					createRowCell(row, j++, entity.getCreatedByUserId());
					createRowCell(row, j++, entity.getCreatedByModuleCd());
					createRowCell(row, j++, entity.getLastUpdatedByUserId());
					createRowCell(row, j++, formatTimestamp(entity.getLastUpdatedDttm()));
					createRowCell(row, j++, entity.getRowVersionSeq());
					createRowCell(row, j++, entity.getRowStatusCd());
					createRowCell(row, j++, entity.getUserName());
					createRowCell(row, j++, entity.getLocaleCd());
					createRowCell(row, j++, entity.getTimeZoneCd());
					createRowCell(row, j++, entity.getSupervisorId());
					createRowCell(row, j++, entity.getSupervisorFlg());
					createRowCell(row, j++, entity.getDefaultTenantCd());
					createRowCell(row, j++, entity.getDefaultLocaleCd());
					createRowCell(row, j++, entity.getPreferedThemeName());
					createRowCell(row, j++, entity.getPriWorkGrpId());
					createRowCell(row, j++, ""); // client xid duplicate fields
					createRowCell(row, j++, entity.getStartingPage());
				}
			}
		}

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		try (FileOutputStream outFile = new FileOutputStream(
				new File("report_" + dateFormat.format(timestamp) + ".xlsx"))) {
			workbook.write(outFile);
		}

		file.close();

		workbook.close();

	}

	public void generateCaseStatusReport(List<CsCaseStatusEntity> filteredCaseStatusList) throws IOException {
		InputStream file = NotifierService.class.getClassLoader()
				.getResourceAsStream("report_falcon_icm_case_status.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		Iterator<Sheet> sheetIterator = workbook.iterator();
		while (sheetIterator.hasNext()) {
			int i = 1;
			Sheet sheet = sheetIterator.next();
			if (sheet.getSheetName().equalsIgnoreCase("Export Worksheet")) {
				for (CsCaseStatusEntity entity : filteredCaseStatusList) {
					int j = 0;
					Row row = sheet.createRow(i++);
					createRowCell(row, j++, entity.getCaseId());
					createRowCell(row, j++, entity.getCaseStatus());
					createRowCell(row, j++, entity.getLastUpdate().toString());
					createRowCell(row, j++, entity.getCreated().toString());
				}
			}
		}

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		try (FileOutputStream outFile = new FileOutputStream(
				new File("report_" + dateFormat.format(timestamp) + ".xlsx"))) {
			workbook.write(outFile);
		}

		file.close();

		workbook.close();

	}

}
