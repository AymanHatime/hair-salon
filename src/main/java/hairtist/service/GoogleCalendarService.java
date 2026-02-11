package hairtist.service;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleCalendarService {

	private static final String APPLICATION_NAME = "HairTist";
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final String SERVICE_ACCOUNT_KEY_FILE = "src/main/resources/credentials/credfiletest.json";

	private Calendar getCalendarService() throws GeneralSecurityException, IOException {
		GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(SERVICE_ACCOUNT_KEY_FILE))
						.createScoped(Collections.singletonList("https://www.googleapis.com/auth/calendar"));

		return new Calendar.Builder(
						GoogleNetHttpTransport.newTrustedTransport(),
						JSON_FACTORY,
						credential
		).setApplicationName(APPLICATION_NAME).build();
	}

	public void addEvent(String calendarId, String summary, String description, String startDateTime, String endDateTime, String timeZone) throws Exception {
		Calendar service = getCalendarService();

		// Create new event.
		Event event = new Event()
						.setSummary(summary)
						.setDescription(description)
						.setStart(new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(startDateTime)).setTimeZone(timeZone))
						.setEnd(new EventDateTime().setDateTime(new com.google.api.client.util.DateTime(endDateTime)).setTimeZone(timeZone));

		// Insert the event in the calendar
		event = service.events().insert(calendarId, event).execute();

		System.out.printf("Event created: %s\n", event.getHtmlLink());
	}
}
