/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import java.time.LocalDate;
import java.time.LocalTime;

public class CatchRecord {
    private final int catchId;
    private final int fisherfolkId;
    private final int speciesId;        // optional, if you want to keep the id
    private final String speciesName;   // shown in UI
    private final double quantity;
    private final double pricePerKilo;
    private final double totalValue;
    private final LocalDate catchDate;
    private final LocalTime dockingTime;  // optional
    private final String remarks;         // optional

    // Constructor
    public CatchRecord(int catchId, int fisherfolkId, int speciesId, String speciesName,
                       double quantity, double pricePerKilo, double totalValue,
                       LocalDate catchDate, LocalTime dockingTime, String remarks) {
        this.catchId = catchId;
        this.fisherfolkId = fisherfolkId;
        this.speciesId = speciesId;
        this.speciesName = speciesName;
        this.quantity = quantity;
        this.pricePerKilo = pricePerKilo;
        this.totalValue = totalValue;
        this.catchDate = catchDate;
        this.dockingTime = dockingTime;
        this.remarks = remarks;
    }

    // Getters
    public int getCatchId() { return catchId; }
    public int getFisherfolkId() { return fisherfolkId; }
    public int getSpeciesId() { return speciesId; }
    public String getSpeciesName() { return speciesName; }
    public double getQuantity() { return quantity; }
    public double getPricePerKilo() { return pricePerKilo; }
    public double getTotalValue() { return totalValue; }
    public LocalDate getCatchDate() { return catchDate; }
    public LocalTime getDockingTime() { return dockingTime; }
    public String getRemarks() { return remarks; }
}

