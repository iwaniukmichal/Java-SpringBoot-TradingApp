package pl.edu.pw.zpoif.exchangeit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.edu.pw.zpoif.exchangeit.model.alphavintage.GlobalQuote;
import pl.edu.pw.zpoif.exchangeit.model.alphavintage.TimeSeriesEntry;
import pl.edu.pw.zpoif.exchangeit.models.ControllerTest;
import pl.edu.pw.zpoif.exchangeit.service.TestDataBaseService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TradingDataControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestDataBaseService testDatabaseService;

    @Test
    public void basicInfoTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        testTradingDataEndpoints(testDatabaseService.getControllerTestsRepository().findByEndpointName("basic"),
                                 new AdditionalValidator() {
                                     @Override
                                     public void accept(ControllerTest test, String body)
                                     throws JsonProcessingException {
                                         if (test.getResponseType().equals("ok")) {
                                             GlobalQuote globalQuote =
                                                     objectMapper.readValue(body, GlobalQuote.class);
                                             Assert.assertNotNull(globalQuote.getSymbol());
                                             Assert.assertTrue(
                                                     globalQuote.getLatestTradingDay().isBefore(LocalDate.now()));
                                             Assert.assertTrue(globalQuote.getVolume() > 0);
                                         }
                                     }
                                 }
                                );
    }

    @Test
    public void basicChartTest() throws Exception {
        chartTest("basic_chart");
    }

    private void chartTest(String name) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        testTradingDataEndpoints(testDatabaseService.getControllerTestsRepository().findByEndpointName(name),
                                 new AdditionalValidator() {
                                     @Override
                                     public void accept(ControllerTest test, String body)
                                     throws JsonProcessingException {
                                         if (test.getResponseType().equals("ok")) {
                                             List<TimeSeriesEntry> timeSeriesEntries = objectMapper.readValue(body,
                                                                                                              new TypeReference<List<TimeSeriesEntry>>() {
                                                                                                              }
                                                                                                             );

                                             Assert.assertTrue(timeSeriesEntries.size() > 0);
                                             for (TimeSeriesEntry entry : timeSeriesEntries) {
                                                 Assert.assertNotNull(entry.getColor());
                                                 Assert.assertTrue(entry.getLow() < entry.getHigh());
                                                 Assert.assertEquals(entry.getOpen() + entry.getRaise(),
                                                                     entry.getClose(), 0.00001
                                                                    );
                                                 Assert.assertTrue(entry.getDateTime().isBefore(LocalDateTime.now()));
                                             }
                                         }
                                     }
                                 }
                                );
    }

    private void testTradingDataEndpoints(List<ControllerTest> tests, AdditionalValidator additionalValidator)
    throws Exception {
        for (ControllerTest test : tests) {
            MvcResult result = mockMvc.perform(switch (test.getRequestType()) {
                case "post" -> post(test.getUri());
                case "put" -> put(test.getUri());
                default -> get(test.getUri());
            }).andExpect(switch (test.getResponseType()) {
                case "error" -> status().isBadRequest();
                case "internal_error" -> status().isInternalServerError();
                default -> status().isOk();
            }).andReturn();
            String body = result.getResponse().getContentAsString();

            if (test.getExpectedContent().length() > 0) {
                Assert.assertEquals(body, test.getExpectedContent());
            }
            additionalValidator.accept(test, body);
        }
    }

    @Test
    public void volumeChartTest() throws Exception {
        chartTest("volume_chart");
    }

    @Test
    public void advancedDataTest() throws Exception {
        testTradingDataEndpoints(testDatabaseService.getControllerTestsRepository().findByEndpointName("basic"));
    }

    private void testTradingDataEndpoints(List<ControllerTest> tests) throws Exception {
        testTradingDataEndpoints(tests, (t, b) -> {
        });
    }

    @Test
    public void integrationTest() throws Exception {
        testTradingDataEndpoints(testDatabaseService.getControllerTestsRepository().findByEndpointName("integration"));
    }

    @FunctionalInterface
    interface AdditionalValidator {
        void accept(ControllerTest test, String body) throws Exception;
    }
}
