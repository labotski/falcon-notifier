package za.co.standardbank.falconnotifier.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import za.co.standardbank.falconnotifier.entites.falcon.CsCaseStatusEntity;
import za.co.standardbank.falconnotifier.entites.falcon.FalconEntity;
import za.co.standardbank.falconnotifier.entites.icm.FalconCaseIdEntity;
import za.co.standardbank.falconnotifier.repository.falcon.FalconRepository;
import za.co.standardbank.falconnotifier.repository.icm.IcmRepository;

@Component
@Slf4j
public class NotifierScheduler {

	@Autowired
	private FalconRepository falconRepository;

	@Autowired
	private IcmRepository icmRepository;

	@Autowired
	private NotifierService notifierService;

	// @Scheduled(cron = "0 0 5 * * ?", zone = "Africa/Johannesburg")

	@EventListener(ApplicationReadyEvent.class)
	public void doSomething() throws IOException {
		List<FalconEntity> data = falconRepository.fetchData();
		log.info("Fetch done, size: {}", data.size());
		notifierService.generateReport(data);
		log.info("Report is done");

		List<FalconCaseIdEntity> caseIds = icmRepository.fetchFalconCaseIds();
		log.info("Icm fetch done");

		List<String> listIds = caseIds.stream().map(FalconCaseIdEntity::getFalconCaseId).collect(Collectors.toList());

		List<CsCaseStatusEntity> falconList = falconRepository.fetchCsCaseStatuses();

		List<CsCaseStatusEntity> collect = falconList.stream().filter(i -> !listIds.contains(i.getCaseId()))
				.collect(Collectors.toList());

		log.info("Fetch case statuses done, filter size: {}", collect.size());

		notifierService.generateCaseStatusReport(collect);

	}

}
