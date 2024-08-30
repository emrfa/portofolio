package cangkIR;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class CartPage {
	 private VBox layout,vblistcart,vbnamecart, vbcart1,vbcart2;
	 
	 private HBox hbcart;
	 
	 private String userId;
	 private String usernamecart;

	    private Label deleteItemLabel,courierLabel,courierPrice,totalPrice,usercart;
	   
	    
	    
	    private TableView<Cart> tableView = new TableView<>();

	    ObservableList<Cart> cartdata = FXCollections.observableArrayList();
	    
	    private Button deleteItemButton;
	    private Button checkoutButton;

	    private ComboBox<String> courierComboBox;
	    private CheckBox insuranceCheckBox;
	    
	    Menu menucart;
	    MenuBar mbcart;
	    MenuItem homecart, cartpage,logoutcart ;
	    
		public void setUserId(String userId) {
	        this.userId = userId;
	    }
		
	    
	    public void init (){
	    	setUserId(userId);
	    	Connect connect = Connect.getInstance();
	    	layout = new VBox(50);
	    	getData();
	    	
	    	vbcart1 = new VBox(15);
	    	vbcart2 = new VBox(15);
	    	
	    	
	    	vblistcart = new VBox(10);
	    	vblistcart.setPadding(new Insets(10,10,10,5));
	    	vbnamecart = new VBox(20);
	    	vbnamecart.setPadding(new Insets(100,1,1,1));
	    	
	    	hbcart = new HBox(20);
//	    	menu	
	    	menucart = new Menu("Menu");
	    	mbcart = new MenuBar();
	    		
	    	homecart = new MenuItem("Home");
	    	cartpage = new MenuItem("Cart");
	    	logoutcart = new MenuItem("Log Out");
	    	
//	    	cb
	    	 insuranceCheckBox = new CheckBox("Use Delivery Insurance");
	    	 insuranceCheckBox.setSelected(false);
	    	

//	    	Label
	    	usercart = new Label("");
	    	usernamecart = connect.getUsernameFromUserID(userId);
	    	usercart.setText(usernamecart + "'s Cart");
	    	usercart.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
	  
	    	deleteItemLabel = new Label("Delete Item");
	    	deleteItemLabel.setFont(Font.font("Verdana", FontWeight.BOLD,15));
	    	
	    	courierLabel = new Label("Courier");
	    	courierLabel.setFont(Font.font("Verdana", FontWeight.BOLD,15));
	    	
	    	courierPrice = new Label("Courier Price : ");
	    	courierPrice.setFont(Font.font("Verdana", FontWeight.BOLD,15));
	    	
	    	totalPrice = new Label("Total Price : ");
	    	totalPrice.setFont(Font.font("Verdana", FontWeight.BOLD,15));
	    	
	    	
//	    	menu
	    	

	    	menucart.getItems().add(homecart);
	    	menucart.getItems().add(cartpage);
	    	menucart.getItems().add(logoutcart);
	    	mbcart.getMenus().add(menucart);
	    	
//	    	btn
	    	deleteItemButton = new Button("Delete");
	    	checkoutButton = new Button("Checkout");
	    	
//	    	cmb
	    	courierComboBox = new ComboBox<>();
	        courierComboBox.getItems().addAll("JNA", "TAKA", "LoinParcel", "IRX", "JINJA");
	        courierComboBox.setValue("JNA");
	        courierComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
	            int price = Courier.getCourierPriceByName(newValue);
	            courierPrice.setText("Courier Price: " + price);
	        });
	        
	        int defaultCourierPrice = getCourierPrice("JNA");
	        int initialTotalPrice = getTotalPrice() + defaultCourierPrice; 
	        totalPrice.setText("Total Price: " + String.valueOf(initialTotalPrice));
	        
	        courierComboBox.setOnAction(event -> {
	            String selectedCourier = courierComboBox.getValue();

	            
	            int courierPrice = getCourierPrice(selectedCourier);

	            int currentTotal = getTotalPrice(); 
	            int total = currentTotal + courierPrice;
	            totalPrice.setText("Total Price: " + String.valueOf(total));
	        });
	        
	        insuranceCheckBox.setOnAction(event -> {
	            int courierPrice = getCourierPrice(courierComboBox.getValue());
	            int currentTotal = getTotalPrice(); 

	            if (insuranceCheckBox.isSelected()) {
	                int insurancePrice = 2000; 
	                int total = currentTotal + insurancePrice + courierPrice;
	                totalPrice.setText("Total Price: " + total);
	            } else {
	                int insurancePrice = 0;
	                int total = currentTotal - insurancePrice + courierPrice;
	                totalPrice.setText("Total Price: " + total);
	            }
	        });
	        
	        deleteItemButton.setOnAction(event -> {
	            Cart selectedCartItem = tableView.getSelectionModel().getSelectedItem(); 

	            if (selectedCartItem != null) {
	               
	                Connect dbConnect = Connect.getInstance(); 
	                String deleteQuery = "DELETE FROM cart WHERE CupID = '" + selectedCartItem.getCupID() + "'";
	                dbConnect.execUpdate(deleteQuery);


	                cartdata.remove(selectedCartItem);
	                int currentTotal = getTotalPrice();
	                totalPrice.setText("Total Price: " + String.valueOf(currentTotal));
	                Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                alert.setTitle("Deletion information");
	                alert.setHeaderText(null);
	                alert.setContentText("Cart deleted successfully");

	                alert.showAndWait();
	            } else {
	             
	                Alert alert = new Alert(Alert.AlertType.WARNING);
	                alert.setTitle("Deletion information");
	                alert.setHeaderText(null);
	                alert.setContentText("Please select item to delete");

	                alert.showAndWait();
	            }
	        });
	        
	        checkoutButton.setOnAction(event -> {
	            
	            Label confirmationLabel = new Label("Are you sure you want to purchase?");
	            confirmationLabel.setFont(Font.font("Verdana", FontWeight.BOLD,15));

	            
	            HBox hbbtn1 = new HBox(15);
	            Button yesButton = new Button("Yes");
	            Button noButton = new Button("No");
	            hbbtn1.getChildren().addAll(yesButton, noButton);
	            hbbtn1.setAlignment(Pos.CENTER);
	            yesButton.setOnAction(e -> {
	            	   emptyUserCart();

	              
	                   createNewTransaction();

	                
	                 

	                   Alert successAlert = new Alert(AlertType.INFORMATION);
	                   successAlert.setTitle("Checkout Successful");
	                   successAlert.setHeaderText(null);
	                   successAlert.setContentText("Checkout was successful!");
	                   successAlert.showAndWait();

	             
	                Stage stage = (Stage) yesButton.getScene().getWindow();
	                stage.close();
	            });

	            noButton.setOnAction(e -> {
	                Stage stage = (Stage) noButton.getScene().getWindow();
	                stage.close();
	            });

	            
	            VBox layout = new VBox(10);
	            layout.getChildren().addAll(confirmationLabel, hbbtn1);
	            layout.setAlignment(Pos.CENTER);
	            layout.setPadding(new Insets(10));

	            
	            Stage confirmationStage = new Stage();
	            confirmationStage.initModality(Modality.APPLICATION_MODAL); 
	            confirmationStage.setTitle("Checkout Confirmation");
	            confirmationStage.setScene(new Scene(layout, 800, 800)); 
	            confirmationStage.show();
	        });
	        
	        logoutcart.setOnAction(e-> {
	        	openlogin();
	        });
	        
	        homecart.setOnAction(e-> {
	        	openuserpage();
	        });
	        
//	    	table

	    	 tableView = new TableView<>();
	         tableView.setMaxWidth(500);
	         tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	         TableColumn<Cart, String> cupNameColumn = new TableColumn<>("Cup Name");
	         cupNameColumn.setCellValueFactory(new PropertyValueFactory<>("CupName"));
	         TableColumn<Cart, Integer> cupPriceColumn = new TableColumn<>("Cup Price");
	         cupPriceColumn.setCellValueFactory(new PropertyValueFactory<>("CupPrice"));
	         TableColumn<Cart, Integer> quantityColumn = new TableColumn<>("Quantity");
	         quantityColumn.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
	         TableColumn<Cart, Integer> totalColumn = new TableColumn<>("Total");
	         totalColumn.setCellValueFactory(new PropertyValueFactory<>("Total"));
	         tableView.getColumns().addAll(cupNameColumn, cupPriceColumn, quantityColumn, totalColumn); 
	         tableView.setItems(cartdata);
	    	
	    	
	         
	         vbcart1.getChildren().addAll(usercart,tableView);
	         vbcart2.getChildren().addAll(deleteItemLabel, deleteItemButton,courierLabel,courierComboBox,courierPrice,insuranceCheckBox,totalPrice,checkoutButton);
	         hbcart.getChildren().addAll(vbcart1,vbcart2);
	         
	    	layout.getChildren().addAll(mbcart,hbcart);

	    }
	    
	    public void getData() {
	        cartdata.clear(); 
	        Connect connect = Connect.getInstance();
	        String query = "SELECT * FROM cart WHERE UserID = '" + userId + "'";
	        ResultSet resultSet = connect.execQuery(query);

	        try {
	            while (resultSet.next()) {
	                String CupID = resultSet.getString("CupID");
	                int quantity = resultSet.getInt("Quantity");

	       
	                String cupInfoQuery = "SELECT CupName, CupPrice FROM mscup WHERE CupID = '" + CupID + "'";
	                ResultSet cupInfoResultSet = connect.execQuery(cupInfoQuery);

	                if (cupInfoResultSet.next()) {
	                    String CupName = cupInfoResultSet.getString("CupName");
	                    int CupPrice = cupInfoResultSet.getInt("CupPrice");

	               
	                    int totalPrice = CupPrice * quantity;

	                    Cart cart = new Cart(userId,CupID, CupName, CupPrice, quantity, totalPrice);

	                
	                    cartdata.add(cart);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	  
	    public void openlogin() {
	        Stage currentStage = (Stage) layout.getScene().getWindow();
		    currentStage.close();
		    

		
		    Login loginpage = new Login();
		    loginpage.initializeComponents();
		    Scene scene = new Scene(loginpage.getRoot(), 800, 800);
		    Stage stage = new Stage();
		    stage.setScene(scene);
		    stage.setTitle("cangkIR");
		    stage.show();
	    }
	    
	    public void openuserpage() {
	    	 Stage currentStage = (Stage) layout.getScene().getWindow();
	 	    currentStage.close();
	 	    

	 	    UserPage userPage = new UserPage();
	 	    userPage.setUserId(userId);
	 	    userPage.initialize();
	 	    Scene scene = new Scene(userPage.getRoot(), 800, 800);
	 	    Stage stage = new Stage();
	 	    stage.setScene(scene);
	 	    stage.setTitle("User Page");
	 	    stage.show();
	    }
	    
	    private void emptyUserCart() {
	    	   Connect connect = Connect.getInstance();
	           String deleteQuery = "DELETE FROM cart WHERE UserID = '" + userId + "'";
	           connect.execUpdate(deleteQuery);
	           cartdata.clear(); 
	           tableView.getItems().clear(); 
	           totalPrice.setText("Total Price: 0"); 
	     	    }

	 
	    private void createNewTransaction() {
	    	 
	    	int totalTransactions = getTotalTransactionsFromDatabase();

	        String newTransactionID = generateTransactionID(totalTransactions);

	        String selectedCourierName = courierComboBox.getValue();
	        String selectedCourierID = Courier.getCourierIDByName(selectedCourierName);

	        int useDeliveryInsurance = insuranceCheckBox.isSelected() ? 1 : 0;
	        java.sql.Date transactionDate = new java.sql.Date(System.currentTimeMillis()); 
	        

	       
	        TransactionHeader transactionHeader = new TransactionHeader(newTransactionID, userId, selectedCourierID, useDeliveryInsurance, transactionDate);
	        insertTransactionIntoDatabase(transactionHeader);
//	        insertTransactionDetail(transactionHeader);


	        emptyUserCart();

	    }
	 
	  
	 
	    private void insertTransactionIntoDatabase(TransactionHeader transactionHeader) {
	        Connect connect = Connect.getInstance();

	  
	        String insertQuery = "INSERT INTO transactionheader (TransactionID, UserID, CourierID, UseDeliveryInsurance, TransactionDate) VALUES ('"
	                + transactionHeader.getTransactionID() + "', '"
	                + transactionHeader.getUserID() + "', '"
	                + transactionHeader.getCourierID() + "', "
	                + transactionHeader.getUseDeliveryInsurance() + ", '"
	                + transactionHeader.getTransactionDate() + "')";

	        // Eksekusi query INSERT
	        connect.execUpdate(insertQuery);
	    }

	    
	    private int getTotalTransactionsFromDatabase() {
	        Connect connect = Connect.getInstance();
	        String query = "SELECT COUNT(*) AS totalTransactions FROM TransactionHeader";
	        ResultSet resultSet = connect.execQuery(query);

	        try {
	            if (resultSet.next()) {
	                return resultSet.getInt("totalTransactions");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return 0; 
	    }

	    private String generateTransactionID(int totalTransactions) {
	        int transactionIndex = totalTransactions + 1;
	        String formattedIndex = String.format("%03d", transactionIndex); 

	        return "TR" + formattedIndex;
	    }
	    
	    private int getCourierPrice(String courierName) {
	        return Courier.getCourierPriceByName(courierName);
	    }
	    

	    
	    private int getTotalPrice() {
	        int total = 0;

	        for (Cart cartItem : cartdata) {
	            int cupPrice = getCupPriceById(cartItem.getCupID());
	            total += cupPrice * cartItem.getQuantity();
	        }

	        return total;
	    }
	    
	    private int getCupPriceById(String cupID) {
	        Connect connect = Connect.getInstance();
	        String query = "SELECT CupPrice FROM mscup WHERE CupID = '" + cupID + "'";
	        ResultSet resultSet = connect.execQuery(query);

	        try {
	            if (resultSet.next()) {
	                return resultSet.getInt("CupPrice");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return 0; 
	    }
	    
	    public VBox getRoot() {
	    	return layout;
	    }
}
