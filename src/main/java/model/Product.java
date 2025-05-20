package model;

public class Product {
	private int productCode;
	private String productName;
	private int price;
	private int codeVersion;
	
	public int getProductCode() {
		return productCode;
	}
	
	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public int getCodeVersion() {
		return codeVersion;
	}
	
	public void setCodeVersion(int codeVersion) {
		this.codeVersion = codeVersion;
	}
 
}
