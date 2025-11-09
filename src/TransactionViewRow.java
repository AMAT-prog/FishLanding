/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class TransactionViewRow {
    private final int transactionId;
    private final int consumerId;
//    private final int catchId;
    private final int speciesId;

    private final String buyerName;
    private final String consumerName;
    // removed: private final String fisherfolkName;
    private final String speciesName;

    private final double qtySold;
    private final double unitPrice;
    private final double totalPrice;

    private final String paymentMethod;
    private final String remarks;
    private final String paymentStatus;
    private final java.time.LocalDateTime txnDate;

    public TransactionViewRow(int transactionId, int consumerId, int speciesId,
                              String buyerName, String consumerName, String speciesName,
                              double qtySold, double unitPrice, double totalPrice,
                              String paymentMethod, String remarks, String paymentStatus,
                              java.time.LocalDateTime txnDate) {
        this.transactionId = transactionId;
        this.consumerId = consumerId;
//        this.catchId = catchId;
        this.speciesId = speciesId;
        this.buyerName = buyerName;
        this.consumerName = consumerName;
        this.speciesName = speciesName;
        this.qtySold = qtySold;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.paymentMethod = paymentMethod;
        this.remarks = remarks;
        this.paymentStatus = paymentStatus;
        this.txnDate = txnDate;
    }

    public int getTransactionId() { return transactionId; }
    public int getConsumerId()    { return consumerId; }
//    public int getCatchId()       { return catchId; }
    public int getSpeciesId() { return speciesId; }
    public String getBuyerName()  { return buyerName; }
    public String getConsumerName(){ return consumerName; }
    public String getSpeciesName(){ return speciesName; }
    public double getQtySold()    { return qtySold; }
    public double getUnitPrice()  { return unitPrice; }
    public double getTotalPrice() { return totalPrice; }
    public String getPaymentMethod(){ return paymentMethod; }
    public String getRemarks()    { return remarks; }
    public String getPaymentStatus(){ return paymentStatus; }
    public java.time.LocalDateTime getTxnDate(){ return txnDate; }
}
