package nl.bank.pakketpolis;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.bank.pakketpolis.interfaces.levensverzekering.LevensVerzekeringRestClient;
import nl.bank.pakketpolis.interfaces.levensverzekering.Levensverzekering;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
@NoArgsConstructor
public class PakketPolisService {

    @Autowired
    LevensVerzekeringRestClient restClient;

    public PakketPolis getLevensverzekering(PakketPolis pakketPolis, Double verzekerdkapitaal, Date geboortedatum, Integer looptijd) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strGeboortedatum = dateFormat.format(geboortedatum);

        Levensverzekering levensverzekering = restClient.getPremie(verzekerdkapitaal.toString(), strGeboortedatum, looptijd.toString());
        pakketPolis.getLevensverzekering().setNaam("Levensverzekering");
        pakketPolis.getLevensverzekering().setPremie(levensverzekering.getPremie());
        pakketPolis.getLevensverzekering().setVerzekerdbedrag(verzekerdkapitaal);
        pakketPolis.setKortingspercentage(2.0d);
        return pakketPolis;
    }
}
