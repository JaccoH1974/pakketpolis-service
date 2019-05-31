package nl.bank.pakketpolis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PakketPolis {

    private Verzekering levensverzekering = new Verzekering();
    private Verzekering inboedelverzekering = new Verzekering();
    private Verzekering brandverzekering = new Verzekering();
    private Verzekering glasverzekering = new Verzekering();
    private Verzekering opstalverzekering = new Verzekering();
    private Double kortingspercentage;

}
