package by.gstu.sales.model;

public class Transaction {
    private String transactionId;
    private String date;
    private String productId;
    private double amount;
    private String region;

    public Transaction(String transactionId, String date, String productId, double amount, String region) {
        this.transactionId = transactionId;
        this.date = date;
        this.productId = productId;
        this.amount = amount;
        this.region = region;
    }

    public String getTransactionId() { return transactionId; }
    public String getDate() { return date; }
    public String getProductId() { return productId; }
    public double getAmount() { return amount; }
    public String getRegion() { return region; }

    @Override
    public String toString() {
        return transactionId + "," + date + "," + productId + "," + amount + "," + region;
    }
}