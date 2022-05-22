package za.co.standardbank.falconnotifier.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import za.co.standardbank.falconnotifier.entites.falcon.CsCaseStatusEntity;
import za.co.standardbank.falconnotifier.entites.falcon.FalconEntity;
import za.co.standardbank.falconnotifier.entites.icm.FalconCaseIdEntity;
import za.co.standardbank.falconnotifier.repository.falcon.FalconRepository;
import za.co.standardbank.falconnotifier.repository.icm.IcmRepository;

@Service
@Slf4j
public class NotifierService {
    private FalconRepository falconRepository;
    private IcmRepository icmRepository;
    private ReportFactory factory;
    @Value("${start}")
    private String startDate;
    @Value("${end}")
    private String endDate;
    @Autowired
    public NotifierService(FalconRepository falconRepository, IcmRepository icmRepository, ReportFactory factory) {
        this.falconRepository = falconRepository;
        this.icmRepository = icmRepository;
        this.factory = factory;
    }

    @Scheduled(cron = "0 0 5 * * ?", zone = "Africa/Johannesburg")
    public void doSomething() throws Exception {
        List<FalconEntity> data = falconRepository.fetchData();
        log.info("Fetch done, size: {}", data.size());
        factory.generateReport(ReportType.CASES_BY_ANALYSTS).generate(data);
        log.info("Report is done");
        List<FalconCaseIdEntity> caseIds = icmRepository.fetchFalconCaseIds();
        log.info("Icm fetch done");
        List<String> listIds = caseIds.stream().map(FalconCaseIdEntity::getFalconCaseId).collect(Collectors.toList());
        List<CsCaseStatusEntity> falconList = falconRepository.fetchCsCaseStatuses();
        List<CsCaseStatusEntity> collect = falconList.stream().filter(i -> !listIds.contains(i.getCaseId())).collect(Collectors.toList());
        log.info("Fetch case statuses done, filter size: {}", collect.size());
        factory.generateReport(ReportType.FALCON_ICM_CASE_STATUS).generate(collect);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() throws Exception {
        LocalDate start = LocalDate.parse(startDate), end = LocalDate.parse(endDate);
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = date.format(formatter);
            List<FalconEntity> data = falconRepository.fetchDataByDate(formattedDate);
            log.info("Fetch done, size: {}", data.size());
            factory.generateReport(ReportType.CASES_BY_ANALYSTS).generate(data);
            log.info("Report date is done");
            List<FalconCaseIdEntity> caseIds = icmRepository.fetchFalconCaseIdsByDate(formattedDate);
            log.info("Icm date fetch done");
            List<String> listIds = caseIds.stream().map(FalconCaseIdEntity::getFalconCaseId).collect(Collectors.toList());
            List<CsCaseStatusEntity> falconList = falconRepository.fetchCsCaseStatusesByDate(formattedDate);
            List<CsCaseStatusEntity> collect = falconList.stream().filter(i -> !listIds.contains(i.getCaseId())).collect(Collectors.toList());
            log.info("Fetch case statuses by date is done , filter size: {}", collect.size());
            factory.generateReport(ReportType.FALCON_ICM_CASE_STATUS).generate(collect);
        }
    }

}
