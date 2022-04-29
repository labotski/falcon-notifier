package za.co.standardbank.falconnotifier.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import za.co.standardbank.falconnotifier.model.NotifierEntity;

@Service
public class NotifierService {

	public void createRowCell(Row row, int cellIndex, String cellName) {
		Cell cell = row.createCell(cellIndex);
		cell.setCellValue(cellName);
	}

	public void createHeader(Sheet sheet) {
		Row row = sheet.createRow(0);
		int i = 0;
		createRowCell(row, i++, "CaseHistoryId");
		createRowCell(row, i++, "CaseId");
		createRowCell(row, i++, "TenantCd");
		createRowCell(row, i++, "CaseReferenceNum");
		createRowCell(row, i++, "CaseLevelCd");
		createRowCell(row, i++, "CaseTypev2Cd");
		createRowCell(row, i++, "CclientXid");
		createRowCell(row, i++, "CactionDttm");
		createRowCell(row, i++, "FraudTypeCd");
		createRowCell(row, i++, "QueueName");
		createRowCell(row, i++, "CaseStatusTypeCd");
		createRowCell(row, i++, "CaseStatusCd");
		createRowCell(row, i++, "ActionUserPartyId");
		createRowCell(row, i++, "AccountReferenceXid");
		createRowCell(row, i++, "CaseCreatedDttm");
		createRowCell(row, i++, "ProtectedAmt");
		createRowCell(row, i++, "CaseActionCd1");
		createRowCell(row, i++, "CaseActionCd2");
		createRowCell(row, i++, "CaseActionCd3");
		createRowCell(row, i++, "CaseCreatedDttm");
		createRowCell(row, i++, "CaseOpenedDttm");
		createRowCell(row, i++, "ProtectedAmt");
		createRowCell(row, i++, "CreateOpenLossAmt");
		createRowCell(row, i++, "ClosedLossAmt");
		createRowCell(row, i++, "UserPartyId");
		createRowCell(row, i++, "FirstName");
		createRowCell(row, i++, "LastName");
		createRowCell(row, i++, "ActiveTennantCd");
		createRowCell(row, i++, "CreatedDttm");
		createRowCell(row, i++, "CreatedByUserId");
		createRowCell(row, i++, "CreatedByModuleCd");
		createRowCell(row, i++, "LastUpdatedByUserId");
		createRowCell(row, i++, "LastUpdatedDttm");
		createRowCell(row, i++, "RowVersionSeq");
		createRowCell(row, i++, "RowStatusCd");
		createRowCell(row, i++, "UserName");
		createRowCell(row, i++, "LocaleCd");
		createRowCell(row, i++, "TimeZoneCd");
		createRowCell(row, i++, "SupervisorId");
		createRowCell(row, i++, "SupervisorFlg");
		createRowCell(row, i++, "DefaultTenantCd");
		createRowCell(row, i++, "PreferedThemeName");
	}

	public void generateReport(List<NotifierEntity> data, String date) throws IOException {
		InputStream file = NotifierService.class.getClassLoader().getResourceAsStream("report.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		Iterator<Sheet> sheetIterator = workbook.iterator();
		while (sheetIterator.hasNext()) {
			int i = 1;
			Sheet sheet = sheetIterator.next();
			createHeader(sheet);
			for (NotifierEntity entity : data) {
				int j = 0;
				Row row = sheet.createRow(i++);
				createRowCell(row, j++, entity.getCaseHistoryId());
				createRowCell(row, j++, entity.getCaseId());
				createRowCell(row, j++, entity.getTenantCd());
				createRowCell(row, j++, entity.getCaseReferenceNum());
				createRowCell(row, j++, entity.getCaseLevelCd());
				createRowCell(row, j++, entity.getCaseTypev2Cd());
				createRowCell(row, j++, entity.getCclientXid());
				createRowCell(row, j++, entity.getCactionDttm());
				createRowCell(row, j++, entity.getFraudTypeCd());
				createRowCell(row, j++, entity.getQueueName());
				createRowCell(row, j++, entity.getCaseStatusTypeCd());
				createRowCell(row, j++, entity.getCaseStatusCd());
				createRowCell(row, j++, entity.getActionUserPartyId());
				createRowCell(row, j++, entity.getAccountReferenceXid());
				createRowCell(row, j++, entity.getCaseActionCd1());
				createRowCell(row, j++, entity.getCaseActionCd2());
				createRowCell(row, j++, entity.getCaseActionCd3());
				createRowCell(row, j++, entity.getCaseCreatedDttm());
				createRowCell(row, j++, entity.getCaseOpenedDttm());
				createRowCell(row, j++, entity.getProtectedAmt());
				createRowCell(row, j++, entity.getCreateOpenLossAmt());
				createRowCell(row, j++, entity.getOpenClosedLossAmt());
				createRowCell(row, j++, entity.getClosedLossAmt());
				createRowCell(row, j++, entity.getUserPartyId());
				createRowCell(row, j++, entity.getFirstName());
				createRowCell(row, j++, entity.getLastName());
				createRowCell(row, j++, entity.getActiveTennantCd());
				createRowCell(row, j++, entity.getCreatedDttm());
				createRowCell(row, j++, entity.getCreatedByUserId());
				createRowCell(row, j++, entity.getCreatedByModuleCd());
				createRowCell(row, j++, entity.getLastUpdatedByUserId());
				createRowCell(row, j++, entity.getLastUpdatedDttm());
				createRowCell(row, j++, entity.getRowVersionSeq());
				createRowCell(row, j++, entity.getRowStatusCd());
				createRowCell(row, j++, entity.getUserName());
				createRowCell(row, j++, entity.getLocaleCd());
				createRowCell(row, j++, entity.getTimeZoneCd());
				createRowCell(row, j++, entity.getSupervisorId());
				createRowCell(row, j++, entity.getSupervisorFlg());
				createRowCell(row, j++, entity.getDefaultTenantCd());
				createRowCell(row, j++, entity.getPreferedThemeName());

			}
		}

		

		try (FileOutputStream outFile = new FileOutputStream(
				new File("report_" + date + ".xlsx"))) {
			workbook.write(outFile);
		}

		file.close();

		workbook.close();

	}

}
