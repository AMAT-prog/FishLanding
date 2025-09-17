
import java.time.LocalDate;
import java.time.LocalTime;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class Catch {
    private int catchId;

    // Keep IDs for FK operations
    private int fisherfolkId;
    private int speciesId;

    // Extra fields for UI display
    private String fisherfolkName;
    private String speciesName;

    private double quantity;
    private double pricePerKilo;
    private double totalValue;     // quantity * pricePerKilo
    private LocalDate catchDate;
    private LocalTime dockingTime;
    private String remarks;



    // Constructor
    public Catch(
        int catchId,
        int fisherfolkId, String fisherfolkName,
        int speciesId,    String speciesName,
        double quantity, double pricePerKilo, double totalValue,
        LocalDate catchDate, LocalTime dockingTime, String remarks
    ) {
        this.catchId = catchId;
        this.fisherfolkId = fisherfolkId;
        this.fisherfolkName = fisherfolkName;
        this.speciesId = speciesId;
        this.speciesName = speciesName;
        this.quantity = quantity;
        this.pricePerKilo = pricePerKilo;
        this.totalValue = totalValue;
        this.catchDate = catchDate;
        this.dockingTime = dockingTime;
        this.remarks = remarks;
    }
    
    //getters
    public int getCatchId() { return catchId; }
    public int getFisherfolkId() { return fisherfolkId; }
    public String getFisherfolkName() { return fisherfolkName; }
    public int getSpeciesId() { return speciesId; }
    public String getSpeciesName() { return speciesName; }
    public double getQuantity() { return quantity; }
    public double getPricePerKilo() { return pricePerKilo; }
    public double getTotalValue() { return totalValue; }
    public LocalDate getCatchDate() { return catchDate; }
    public LocalTime getDockingTime() { return dockingTime; }
    public String getRemarks() { return remarks; }

    
    //setters
    public void setCatchId(int catchId) { this.catchId = catchId; }
    public void setFisherfolkId(int fisherfolkId) { this.fisherfolkId = fisherfolkId; }
    public void setFisherfolkName(String fisherfolkName) { this.fisherfolkName = fisherfolkName; }
    public void setSpeciesId(int speciesId) { this.speciesId = speciesId; }
    public void setSpeciesName(String speciesName) { this.speciesName = speciesName; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public void setPricePerKilo(double pricePerKilo) { this.pricePerKilo = pricePerKilo; }
    public void setTotalValue(double totalValue) { this.totalValue = totalValue; }
    public void setCatchDate(LocalDate catchDate) { this.catchDate = catchDate; }
    public void setDockingTime(LocalTime dockingTime) { this.dockingTime = dockingTime; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

}

