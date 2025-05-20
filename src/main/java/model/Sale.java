package model;

public class Sale {
	private String productName;
	private int productCode;
	private int quantity;
	private String salesDate;
	private String updateDate;
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getProductCode() {
		return productCode;
	}
	
	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}
	
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String getSalesDate() {
		return salesDate;
	}
	
	public void setSalesDate(String salesDate) {
		this.salesDate = salesDate;
	}
	
	public String getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	public Sale(String productName,int productCode, int quantity, String salesDate, String updateDate ) {
		this.productCode = productCode;
		this.productName = productName;
		this.quantity = quantity;
		this.salesDate = salesDate;
		this.updateDate = updateDate;
		
	}
	
}
