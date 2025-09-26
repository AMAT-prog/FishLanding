/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
//for dashboard recent landings
public class LandingRow {
    private final String fisherName;
    private final String speciesName;
    private final double quantityKg;
    private final double valuePhp;

    public LandingRow(String fisherName, String speciesName, double quantityKg, double valuePhp) {
        this.fisherName = fisherName;
        this.speciesName = speciesName;
        this.quantityKg = quantityKg;
        this.valuePhp = valuePhp;
    }
    public String getFisherName()  { return fisherName;  }
    public String getSpeciesName() { return speciesName; }
    public double getQuantityKg()  { return quantityKg;  }
    public double getValuePhp()    { return valuePhp;    }
}

