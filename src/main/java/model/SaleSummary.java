package model;

public class SaleSummary {
    private int productCode;
    private String productName;
    private int totalQuantity;

    public SaleSummary(int productCode, String productName, int totalQuantity) {
        this.productCode = productCode;
        this.productName = productName;
        this.totalQuantity = totalQuantity;
    }

    public int getProductCode() { return productCode; }
    public String getProductName() { return productName; }
    public int getTotalQuantity() { return totalQuantity; }
}