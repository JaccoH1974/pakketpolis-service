package nl.bank.pakketpolis.interfaces.levensverzekering;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient (name = "levensverzekering-service", url="http://${levensverzekering-service.host}:${levensverzekering-service.port}")
public interface LevensVerzekeringRestClient {

        @RequestMapping(method = RequestMethod.GET, value = "/bank/verzekering/leven")
        Double getPremie(@RequestParam("verzekerdkapitaal") String verzekerdkapitaal,
                         @RequestParam("geboortedatum") String geboortedatum,
                         @RequestParam("looptijd") String looptijd);

}