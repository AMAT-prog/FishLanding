/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
// TransactionViewRow.java  (a UI-friendly row with fisher+species names)
//import java.time.LocalDateTime;
//
//public class TransactionViewRow {
//    private final int id;
//    private final String buyerName;
//    private final String fisherfolkName;
//    private final String speciesName;  // fish type from catch->species
//    private final double qtySold;
//    private final double unitPrice;
//    private final double totalPrice;
//    private final String paymentMethod; // Cash/Credit/Bank Transfer/Other
//    private final String paymentStatus; // Paid/Partial/Unpaid
//    private final LocalDateTime txnDate;
//
//    public TransactionViewRow(int id, String buyerName, String fisherfolkName, String speciesName,
//                              double qtySold, double unitPrice, double totalPrice,
//                              String paymentMethod, String paymentStatus, LocalDateTime txnDate) {
//        this.id = id;
//        this.buyerName = buyerName;
//        this.fisherfolkName = fisherfolkName;
//        this.speciesName = speciesName;
//        this.qtySold = qtySold;
//        this.unitPrice = unitPrice;
//        this.totalPrice = totalPrice;
//        this.paymentMethod = paymentMethod;
//        this.paymentStatus = paymentStatus;
//        this.txnDate = txnDate;
//    }
//
//    public int getId() { return id; }
//    public String getBuyerName() { return buyerName; }
//    public String getFisherfolkName() { return fisherfolkName; }
//    public String getSpeciesName() { return speciesName; }
//    public double getQtySold() { return qtySold; }
//    public double getUnitPrice() { return unitPrice; }
//    public double getTotalPrice() { return totalPrice; }
//    public String getPaymentMethod() { return paymentMethod; }
//    public String getPaymentStatus() { return paymentStatus; }
//    public LocalDateTime getTxnDate() { return txnDate; }
//}
//

import java.time.LocalDateTime;

public class TransactionViewRow {
    // --- Raw IDs (needed for updates/deletes/joins) ---
    private final int transactionId;
    private final int fisherfolkId;
    private final int catchId;

    // --- Display fields for the UI ---
    private final String buyerName;
    private final String fisherfolkName;
    private final String speciesName;  // fish type (catch → species)
    private final double qtySold;
    private final double unitPrice;
    private final double totalPrice;
    private final String paymentMethod; // Cash/Credit/Bank Transfer/Other
    private final String remarks;
    private final String paymentStatus; // Paid/Partial/Unpaid
    private final LocalDateTime txnDate;

    // --- Constructor ---
    public TransactionViewRow(int transactionId, int fisherfolkId, int catchId,
                              String buyerName, String fisherfolkName, String speciesName,
                              double qtySold, double unitPrice, double totalPrice,
                              String paymentMethod, String remarks, String paymentStatus, LocalDateTime txnDate) {
        this.transactionId = transactionId;
        this.fisherfolkId = fisherfolkId;
        this.catchId = catchId;
        this.buyerName = buyerName;
        this.fisherfolkName = fisherfolkName;
        this.speciesName = speciesName;
        this.qtySold = qtySold;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.remarks = remarks;
        this.paymentStatus = paymentStatus;
        this.txnDate = txnDate;
    }

    // --- Getters ---
    public int getTransactionId() { return transactionId; }
    public int getFisherfolkId()  { return fisherfolkId; }
    public int getCatchId()       { return catchId; }

    public String getBuyerName() { return buyerName; }
    public String getFisherfolkName() { return fisherfolkName; }
    public String getSpeciesName() { return speciesName; }
    public double getQtySold() { return qtySold; }
    public double getUnitPrice() { return unitPrice; }
    public double getTotalPrice() { return totalPrice; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getRemarks() { return remarks; }
    public String getPaymentStatus() { return paymentStatus; }
    public LocalDateTime getTxnDate() { return txnDate; }
}
