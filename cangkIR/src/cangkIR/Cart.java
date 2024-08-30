package cangkIR;



public class Cart {
	private String UserID;
	private String CupID;
	private String CupName;
	private int quantity;
	private int cupPrice;
	private int total;
	
	
	
	  public Cart(String userID, String cupID, String cupName, int cupPrice, int quantity, int total) {
	        this.UserID = userID;
	        this.CupID = cupID;
	        this.CupName = cupName;
	        this.quantity = quantity;
	        this.cupPrice = cupPrice;
	        this.total = total;
	    }
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getCupID() {
		return CupID;
	}
	public void setCupID(String cupID) {
		CupID = cupID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getCupPrice() {
		return cupPrice;
	}
	public void setCupPrice(int cupPrice) {
		this.cupPrice = cupPrice;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getCupName() {
		return CupName;
	}
	public void setCupName(String cupName) {
		CupName = cupName;
	}
	

	
}
