package za.co.standardbank.falconnotifier.service;

import org.springframework.stereotype.Component;

@Component
public class ReportFactory {
    public Report generateReport(ReportType type){
        Report report = null;
        switch (type){
            case CASES_BY_ANALYSTS:
                report = new CasesByAnalystsReport();
                break;
            case FALCON_ICM_CASE_STATUS:
                report = new FalconIcmCaseStatusReport();
                break;
            default:
                throw new IllegalArgumentException("Wrong report type: " + type);
        }
        return report;
    }

}
