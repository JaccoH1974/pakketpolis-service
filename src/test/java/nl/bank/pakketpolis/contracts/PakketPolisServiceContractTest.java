package nl.bank.pakketpolis.contracts;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import nl.bank.pakketpolis.interfaces.levensverzekering.LevensVerzekeringRestClient;
import nl.bank.pakketpolis.interfaces.levensverzekering.Levensverzekering;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        properties = {"levensverzekering-service.port = 9090"})
@AutoConfigureStubRunner(stubsMode = StubRunnerProperties.StubsMode.LOCAL,
 ids = "nl.bank.levensverzekering:levensverzekering-service:+:stubs:9090")
@ActiveProfiles({"test"})
public class PakketPolisServiceContractTest {

    @Autowired
    private LevensVerzekeringRestClient restClient;

    @Test
    public void goodRequest() throws Exception {
        Levensverzekering levensverzekering = restClient.getPremie("25000", "1974-01-01", "360");
        Assert.assertEquals(new Double(31.25d), levensverzekering.getPremie());
    }
    @Test
    public void badRequest() {
        try {
            Levensverzekering levensverzekering = restClient.getPremie("25000", null, "360");
            Assert.fail();
        } catch (FeignException e){
          log.info("Status {}", e.status());
          Assert.assertEquals(HttpStatus.SC_BAD_REQUEST, e.status());
        }
    }

}
