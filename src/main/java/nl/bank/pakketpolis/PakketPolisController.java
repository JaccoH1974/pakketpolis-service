package nl.bank.pakketpolis;

import lombok.extern.slf4j.Slf4j;
import nl.bank.pakketpolis.interfaces.levensverzekering.LevensVerzekeringRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;
import java.util.Date;

@Slf4j
@RestController
@RequestMapping("/verzekering/pakketpolis")
public class PakketPolisController {

    public final static String ALLEEN_LEVEN = "alleen_leven";
    @Autowired
    PakketPolisService service;

    @Autowired
    LevensVerzekeringRestClient restClient;

    @RequestMapping(value ="/{soortpakket}")
    ResponseEntity<PakketPolis> getPakketPolisMetLevensverzekering(
            @PathVariable("soortpakket") String soortPakket,
            @QueryParam("verzekerdkapitaal") Double verzekerdkapitaal,
            @QueryParam("geboortedatum") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date geboortedatum,
            @QueryParam("looptijd") Integer looptijd) {

        PakketPolis pakketPolis = new PakketPolis();
        if (ALLEEN_LEVEN.equalsIgnoreCase(soortPakket)) {
            pakketPolis = service.getLevensverzekering(pakketPolis, verzekerdkapitaal, geboortedatum, looptijd);
        }

        return  ResponseEntity.ok(pakketPolis);
    }
}
