package nl.bank.pakketpolis;


import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
@ActiveProfiles({"test"})
public class PakketServiceControllerTest {

    @InjectMocks
    PakketPolisController controller;

    @Mock
    PakketPolisService service;

    @Test
    public void getPakketPolisMetLevensverzekeringTest_alleen_leven() {
        try {
            String soortPakket = "alleen_leven";
            Double verzekerdkapitaal = 60000d;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date geboortedatum = format.parse("1999-09-06");
            Integer looptijd = 360;

            PakketPolis pakketPolis = new PakketPolis();

            PakketPolis expectedPakketPolis = new PakketPolis();
            expectedPakketPolis.getLevensverzekering().setNaam("Levensverzekering");
            expectedPakketPolis.getLevensverzekering().setVerzekerdbedrag(verzekerdkapitaal);
            expectedPakketPolis.getLevensverzekering().setPremie(33.5);
            expectedPakketPolis.setKortingspercentage(2.0d);

            Mockito.when(service.getLevensverzekering(pakketPolis, verzekerdkapitaal, geboortedatum, looptijd)).thenReturn(expectedPakketPolis);

            ResponseEntity<PakketPolis> response = controller.getPakketPolisMetLevensverzekering(soortPakket, verzekerdkapitaal, geboortedatum, looptijd);
            Assertions.assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isEqualTo(expectedPakketPolis);
        } catch (ParseException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void getPakketPolisMetLevensverzekeringTest_alleen_leven_geboortedatum_null() {
        String soortPakket = "alleen_leven";
        Double verzekerdkapitaal = 60000d;
        Date geboortedatum = null;
        Integer looptijd = 360;

        PakketPolis pakketPolis = new PakketPolis();

        PakketPolis expectedPakketPolis = new PakketPolis();

        Mockito.when(service.getLevensverzekering(pakketPolis, verzekerdkapitaal, geboortedatum, looptijd)).thenReturn(pakketPolis);

        ResponseEntity<PakketPolis> response = controller.getPakketPolisMetLevensverzekering(soortPakket, verzekerdkapitaal, geboortedatum, looptijd);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo(expectedPakketPolis);

    }


    @Test
    public void getPakketPolisMetLevensverzekeringTest_inboedel() {
        try {
            String soortPakket = "inboedel";
            Double verzekerdkapitaal = 100000d;
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date geboortedatum = format.parse("1999-09-06");
            Integer looptijd = 360;

            PakketPolis expectedPakketPolis = new PakketPolis();

            ResponseEntity<PakketPolis> response = controller.getPakketPolisMetLevensverzekering(soortPakket, verzekerdkapitaal, geboortedatum, looptijd);
            Assertions.assertThat(response.getStatusCode()).isEqualTo(org.springframework.http.HttpStatus.OK);
            Assertions.assertThat(response.getBody()).isEqualTo(expectedPakketPolis);
        } catch (ParseException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
