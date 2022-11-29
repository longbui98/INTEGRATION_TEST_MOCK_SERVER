package timezones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.udacity.timezones.service.TimeZoneService;


public class TimeZoneServiceTest {
	static WireMockServer wireMock = new WireMockServer(WireMockConfiguration.DEFAULT_PORT);
	private static String serverPath = "http://localhost:8080";
	private static String path = "/api/timezone/";
	
	TimeZoneService timeZoneService;
	
	@BeforeAll
	static void setup() {
		wireMock.start();
	}
	
	@AfterAll
	static void stop() {
		wireMock.stop();
	}
	
	@BeforeEach
	void init() {
		wireMock.resetAll();
		timeZoneService = new TimeZoneService(serverPath);
	}
	
	@Test
	void availableTimezoneTest_passedValue_returnaVailableZones() {	
		String area = "ICT";
		String expected = "Vietnam, Thailand, Campodia, Laos";
		
		wireMock.stubFor(WireMock.get(path + area).willReturn(ResponseDefinitionBuilder
				.responseDefinition().withStatus(200).withBody(expected)));
		String response = timeZoneService.getAvailableTimezoneText(area);
		
		assertTrue(response.contains("Available timezones in " +  area + " are ".concat(expected)));
	}
}
