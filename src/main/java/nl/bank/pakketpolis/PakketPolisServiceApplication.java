package nl.bank.pakketpolis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PakketPolisServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PakketPolisServiceApplication.class, args);
    }

}
