/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class InventoryRow {
    private final int speciesId;
    private final String speciesName;
    private final double purchasedQty;
    private final double soldQty;
    private final double balanceQty;
    private final double lastPurchasePrice;
    private final double avgPurchasePrice;
    private final double sellingPrice;
//    private final double restockThreshold;
    private final java.time.LocalDateTime updatedAt;

    public InventoryRow(int speciesId, String speciesName, double purchasedQty, double soldQty,
                        double balanceQty, double lastPurchasePrice, double avgPurchasePrice,
                        double sellingPrice, java.time.LocalDateTime updatedAt) {
        this.speciesId = speciesId;
        this.speciesName = speciesName;
        this.purchasedQty = purchasedQty;
        this.soldQty = soldQty;
        this.balanceQty = balanceQty;
        this.lastPurchasePrice = lastPurchasePrice;
        this.avgPurchasePrice = avgPurchasePrice; // NOT SHOWN IN UI
        this.sellingPrice = sellingPrice;
//        this.restockThreshold = restockThreshold; // NOT SHOWN IN UI
        this.updatedAt = updatedAt;
    }
    public int getSpeciesId() { return speciesId; }
    public String getSpeciesName() { return speciesName; }
    public double getPurchasedQty() { return purchasedQty; }
    public double getSoldQty() { return soldQty; }
    public double getBalanceQty() { return balanceQty; }
    public Double getLastPurchasePrice() { return lastPurchasePrice; }
    public Double getSellingPrice() { return sellingPrice; }
    public java.time.LocalDateTime getUpdatedAt() { return updatedAt; }
}

