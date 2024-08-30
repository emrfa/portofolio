package cangkIR;

public class TransactionDetail {
	private String TransactionID;
	private String CupID;
	private int Quantity;
	
	
	public TransactionDetail(String transactionID, String cupID, int quantity) {
		super();
		TransactionID = transactionID;
		CupID = cupID;
		Quantity = quantity;
	}
	public String getTransactionID() {
		return TransactionID;
	}
	public void setTransactionID(String transactionID) {
		TransactionID = transactionID;
	}
	public String getCupID() {
		return CupID;
	}
	public void setCupID(String cupID) {
		CupID = cupID;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	
	
}
