package nl.bank.pakketpolis;

import com.github.tomakehurst.wiremock.client.WireMock;
import nl.bank.pakketpolis.PakketPolis;
import nl.bank.pakketpolis.PakketPolisService;
import nl.bank.pakketpolis.interfaces.levensverzekering.LevensVerzekeringRestClient;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = PakketPolisServiceApplication.class)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles({"test"})
public class PakketPolisServiceTest {

    @Autowired
    PakketPolisService service;

    @Autowired
    LevensVerzekeringRestClient restClient;

    @Before
    public void setUp() {

        WireMock.stubFor(WireMock.get(
                WireMock.urlPathEqualTo("/bank/verzekering/leven"))
                .withQueryParam("verzekerdkapitaal", WireMock.equalTo("60000.0"))
                .withQueryParam("geboortedatum", WireMock.equalTo("1999-09-06"))
                .withQueryParam("looptijd", WireMock.equalTo("360"))
                .willReturn(WireMock.aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(HttpStatus.SC_OK)
                        .withBody("{\n" +
                                "    \"premie\": 33.5,\n" +
                                "    \"risicoprofiel\": \"risico matig\"\n" +
                                "}")));
    }

    @Test
    public void getLevensverzekeringTest() {
        try {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date geboortedatum = format.parse("1999-09-06");
            Integer looptijd = 360;
            PakketPolis pakketPolis = new PakketPolis();
            Double verzekerdkapitaal = 60000d;
            PakketPolis expectedPakketPolis = new PakketPolis();
            expectedPakketPolis.getLevensverzekering().setNaam("Levensverzekering");
            expectedPakketPolis.getLevensverzekering().setVerzekerdbedrag(verzekerdkapitaal);
            expectedPakketPolis.getLevensverzekering().setPremie(33.5);
            expectedPakketPolis.setKortingspercentage(2.0d);

            PakketPolis resultPakketPolis = service.getLevensverzekering(pakketPolis, verzekerdkapitaal, geboortedatum, looptijd);

            Assertions.assertThat(resultPakketPolis).isEqualTo(expectedPakketPolis);

        } catch (ParseException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
