/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import java.time.LocalDate;

public class TransactionRecord {
    private final int transactionId;
    private final int fisherId;
    private final String buyerName;
    private final double qtySold;
    private final double unitPrice;
    private final double totalPrice;
    private final String status;
    private final LocalDate transactionDate; // optional, if wanted to show date later

    public TransactionRecord(int transactionId, int fisherId,
                             String buyerName, double qtySold,
                             double unitPrice, double totalPrice,
                             String status) {
        this(transactionId, fisherId, buyerName, qtySold, unitPrice, totalPrice, status, null);
    }

    public TransactionRecord(int transactionId, int fisherId,
                             String buyerName, double qtySold,
                             double unitPrice, double totalPrice,
                             String status, LocalDate transactionDate) {
        this.transactionId = transactionId;
        this.fisherId = fisherId;
        this.buyerName = buyerName;
        this.qtySold = qtySold;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.status = status;
        this.transactionDate = transactionDate;
    }

    // getters
    public int getTransactionId() { return transactionId; }
    public int getFisherId() { return fisherId; }
    public String getBuyerName() { return buyerName; }
    public double getQtySold() { return qtySold; }
    public double getUnitPrice() { return unitPrice; }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public LocalDate getTransactionDate() { return transactionDate; }
}

