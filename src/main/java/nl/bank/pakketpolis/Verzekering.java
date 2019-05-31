package nl.bank.pakketpolis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Verzekering {
    private String naam;
    private Double premie;
    private Double verzekerdbedrag;

}
