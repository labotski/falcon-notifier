package za.co.standardbank.falconnotifier.service;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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
	
	@Value("${start}")
	private String startDate;
	
	@Value("${end}")
	private String endDate;

	@EventListener(ApplicationReadyEvent.class)
	public void doSomething() throws IOException, ParseException {
		
		
		LocalDate start = LocalDate.parse(startDate),
		          end   = LocalDate.parse(endDate);
		
		for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1))
		{

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedString = date.format(formatter);
			List<NotifierEntity> data = notifierRespository.fetchData(formattedString);
			log.info("Fetch done, size: {}", data.size());
			notifierService.generateReport(data, formattedString);
			log.info("Report is done");
		}
		
		
	}

}
