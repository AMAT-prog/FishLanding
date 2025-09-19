/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
//for combo box on fish type (ADDING TRANSACTION)
// CatchOption.java  (for the Fish Type / Catch combo)
import java.time.LocalDate;

public class CatchOption {
    private final int catchId;
    private final int fisherId;
    private final int speciesId;
    private final String speciesName;
    private final double pricePerKilo;
    private final double remainingQty;
    private final LocalDate catchDate;

    public CatchOption(int catchId, int fisherId, int speciesId, String speciesName,
                       double pricePerKilo, double remainingQty, LocalDate catchDate) {
        this.catchId = catchId;
        this.fisherId = fisherId;
        this.speciesId = speciesId;
        this.speciesName = speciesName;
        this.pricePerKilo = pricePerKilo;
        this.remainingQty = remainingQty;
        this.catchDate = catchDate;
    }
    public int getCatchId() { return catchId; }
    public int getFisherId() { return fisherId; }
    public int getSpeciesId() { return speciesId; }
    public String getSpeciesName() { return speciesName; }
    public double getPricePerKilo() { return pricePerKilo; }
    public double getRemainingQty() { return remainingQty; }
    public LocalDate getCatchDate() { return catchDate; }

    @Override public String toString() {
        // nice label in the dropdown
        return speciesName + " • " + catchDate + " • rem: " + String.format("%.2f", remainingQty) + " kg";
    }
}

