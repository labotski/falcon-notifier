package za.co.standardbank.falconnotifier.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import za.co.standardbank.falconnotifier.model.NotifierEntity;
import za.co.standardbank.falconnotifier.repository.NotifierRepository;

@Component
@Slf4j
public class NotifierScheduler {

	@Autowired
	private NotifierRepository notifierRespository;

	@Autowired
	private NotifierService notifierService;

	@Scheduled(cron = "0 0 5 * * ?", zone="Africa/Johannesburg")
	public void doSomething() throws IOException {
		List<NotifierEntity> data = notifierRespository.fetchData();
		log.info("Fetch done, size: {}", data.size());
		notifierService.generateReport(data);
		log.info("Report is done");
	}

}
