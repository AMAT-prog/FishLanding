/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
// InventoryOption.java
public class InventoryOption {
    private final int speciesId;
    private final String speciesName;
    private final double balanceQty;
    private final double sellingPrice;

    public InventoryOption(int speciesId, String speciesName, double balanceQty, double sellingPrice) {
        this.speciesId = speciesId;
        this.speciesName = speciesName;
        this.balanceQty = balanceQty;
        this.sellingPrice = sellingPrice;
    }
    public int getSpeciesId() { return speciesId; }
    public String getSpeciesName() { return speciesName; }
    public double getBalanceQty() { return balanceQty; }
    public double getSellingPrice() { return sellingPrice; }

    @Override public String toString() {
        return speciesName + " • rem: " + String.format("%.2f", balanceQty) + " kg • ₱" + String.format("%.2f", sellingPrice);
    }
}
