package za.co.standardbank.falconnotifier.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import za.co.standardbank.falconnotifier.entites.HealthCheckEntity;
import za.co.standardbank.falconnotifier.entites.falcon.CsCaseStatusEntity;
import za.co.standardbank.falconnotifier.entites.falcon.FalconEntity;
import za.co.standardbank.falconnotifier.entites.icm.FalconCaseIdEntity;
import za.co.standardbank.falconnotifier.repository.falcon.FalconRepository;
import za.co.standardbank.falconnotifier.repository.icm.IcmRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<CsCaseStatusEntity> fetchCsCaseStatusReport() throws Exception {
        List<FalconCaseIdEntity> caseIds = icmRepository.fetchFalconCaseIds();
        log.info("Icm fetch done");
        List<String> listIds = caseIds.stream().map(FalconCaseIdEntity::getFalconCaseId).collect(Collectors.toList());
        List<CsCaseStatusEntity> falconList = falconRepository.fetchCsCaseStatuses();
        List<CsCaseStatusEntity> collect = falconList.stream().filter(i -> !listIds.contains(i.getCaseId())).collect(Collectors.toList());
        log.info("Fetch case statuses done, filter size: {}", collect.size());
        return collect;
    }

    @Scheduled(cron = "0 0 5 * * ?", zone = "Africa/Johannesburg")
    public void doSomething() throws Exception {
        List<FalconEntity> data = falconRepository.fetchData();
        log.info("Fetch done, size: {}", data.size());
        factory.generateReport(ReportType.CASES_BY_ANALYSTS).generate(data, null);
        log.info("Report is done");
        List<CsCaseStatusEntity> collect = fetchCsCaseStatusReport();
        log.info("Start - Update extract case data flag");
        falconRepository.updateExtractCaseData(collect.stream().map(CsCaseStatusEntity::getCaseId).collect(Collectors.toList()));
        log.info("Finished - Update extract case data flag");
        factory.generateReport(ReportType.FALCON_ICM_CASE_STATUS).generate(collect, null);
    }

    @Scheduled(cron = "0 0 6 * * ?", zone = "Africa/Johannesburg")
    public void fetchAgainCaseStatusAfterUpdateProcessFlag() throws Exception {
        log.info("Start - Fetch data after update process flag");
        factory.generateReport(ReportType.FALCON_ICM_CASE_STATUS_UPDATE_PROCESS_FLAG).generate(fetchCsCaseStatusReport(), null);
        log.info("Finished - Fetch data after update process flag");
    }

    @Scheduled(cron = "0 0 7 * * ?", zone = "Africa/Johannesburg")
    public void healthCheckReport() throws Exception {
        log.info("Start - Fetch data health check");
        Long healthCheckIcCases = icmRepository.healthCheckIcCases();
        Long creditTrnCount = falconRepository.healthCheckCreditTrn();
        Long debitTrnCount = falconRepository.healthCheckDebitTrn();
        Long filterQueryCount = falconRepository.healthCheckFilterQuery();
        factory.generateReport(ReportType.HEALTH_CHECK).generate(Arrays.asList(new HealthCheckEntity(healthCheckIcCases, creditTrnCount, debitTrnCount, filterQueryCount)), null);
        log.info("Finished - Fetch data health check");
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() throws Exception {
        LocalDate start = LocalDate.parse(startDate), end = LocalDate.parse(endDate);
        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String formattedDate = date.format(formatter);
            List<FalconEntity> data = falconRepository.fetchDataByDate(formattedDate);
            log.info("Fetch done, size: {}", data.size());
            factory.generateReport(ReportType.CASES_BY_ANALYSTS).generate(data, formattedDate);
            log.info("Report date is done");
            List<FalconCaseIdEntity> caseIds = icmRepository.fetchFalconCaseIdsByDate(formattedDate);
            log.info("Icm date fetch done");
            List<String> listIds = caseIds.stream().map(FalconCaseIdEntity::getFalconCaseId).collect(Collectors.toList());
            List<CsCaseStatusEntity> falconList = falconRepository.fetchCsCaseStatusesByDate(formattedDate);
            List<CsCaseStatusEntity> collect = falconList.stream().filter(i -> !listIds.contains(i.getCaseId())).collect(Collectors.toList());
            log.info("Fetch case statuses by date is done , filter size: {}", collect.size());
            factory.generateReport(ReportType.FALCON_ICM_CASE_STATUS).generate(collect, formattedDate);
        }
    }

}
