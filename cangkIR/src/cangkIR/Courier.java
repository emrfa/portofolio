package cangkIR;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Courier {

	private String CourierID;
	private String CourierName;
	private int CourierPrice;
	
	
	
	
	public Courier(String courierID, String courierName, int courierPrice) {
		super();
		CourierID = courierID;
		CourierName = courierName;
		CourierPrice = courierPrice;
	}
	public String getCourierID() {
		return CourierID;
	}
	public void setCourierID(String courierID) {
		CourierID = courierID;
	}
	public String getCourierName() {
		return CourierName;
	}
	public void setCourierName(String courierName) {
		CourierName = courierName;
	}
	public int getCourierPrice() {
		return CourierPrice;
	}
	public void setCourierPrice(int courierPrice) {
		CourierPrice = courierPrice;
	}
	
	public static int getCourierPriceByName(String courierName) {
        Connect connect = Connect.getInstance();
        String query = "SELECT CourierPrice FROM mscourier WHERE CourierName = '" + courierName + "'";
        ResultSet resultSet = connect.execQuery(query);

        try {
            if (resultSet.next()) {
                return resultSet.getInt("CourierPrice");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0; 
    }
	
	public static String getCourierIDByName(String courierName) {
	     Connect connect = Connect.getInstance();
	        String query = "SELECT CourierID FROM mscourier WHERE CourierName = '" + courierName + "'";
	        ResultSet resultSet = connect.execQuery(query);

	        try {
	            if (resultSet.next()) {
	                return resultSet.getString("CourierID");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return ""; 
     
    }

	
}
