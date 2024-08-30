package cangkIR;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class UserPage{

	private String userId;

	VBox vbuser,vblistuser,vbnameuser;
	HBox hbuser, hbmenuuser;
	
	Spinner<Integer> ammountSpinner;
	SpinnerValueFactory<Integer> ammountFactory;
	
	Menu menuuser;
	MenuBar mbuser;
	MenuItem homeuser,cartuser,logoutuser;
	
	Button btncartuser;
	
	ComboBox cupTypeComboBox;
	
//	TextField searchBox;
	
	TableView <Cup> cuptableuser;
	
	  ObservableList<Cup> cupdata = FXCollections.observableArrayList();
	
	Label cuplistuser,cupnameuser,priceuser;
	
	public void setUserId(String userId) {
        this.userId = userId;
    }
	
	public void initialize() {
		System.out.println(userId);
		getData();
		
//	ComboBox
		  cupTypeComboBox = new ComboBox<>();
		    cupTypeComboBox.getItems().addAll("All", "Wooden", "Glass", "Ceramic", "Plastic"); // Add other types as needed
		    cupTypeComboBox.setValue("All"); // Default value to show all cups
		
		
//		scene
	vbuser = new VBox(50);
	

	
	vblistuser = new VBox(10);
	vblistuser.setPadding(new Insets(10,10,10,5));
	vbnameuser = new VBox(20);
	vbnameuser.setPadding(new Insets(100,1,1,1));
	
	hbuser = new HBox();
//	menu	
	menuuser = new Menu("Menu");
	mbuser = new MenuBar();
		
	homeuser = new MenuItem("Home");
	cartuser = new MenuItem("Cart");
	logoutuser = new MenuItem("Log Out");
	

//	Label
	cuplistuser = new Label("Cup List");
	cuplistuser.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
	
	cupnameuser = new Label("Cup Name");
	cupnameuser.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
	
	priceuser = new Label("Price");
	priceuser.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
	
//	Spinner
	ammountSpinner = new Spinner();
	ammountSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20));
//	menu
	

	menuuser.getItems().add(homeuser);
	menuuser.getItems().add(cartuser);
	menuuser.getItems().add(logoutuser);
	mbuser.getMenus().add(menuuser);

//	table
	cuptableuser = new TableView<>();

	
    TableColumn<Cup, String> namecol = new TableColumn<>("Cup Name");
    namecol.setMinWidth(150);
    namecol.setCellValueFactory(new PropertyValueFactory<>("CupName"));
    
    TableColumn<Cup, Integer> pricecol = new TableColumn<>("Cup Price");
    pricecol.setMinWidth(150);
    pricecol.setCellValueFactory(new PropertyValueFactory<>("CupPrice"));
    cuptableuser.getColumns().addAll(namecol, pricecol); 
    cuptableuser.setMaxWidth(300);
    cuptableuser.setItems(cupdata);
//	Button
	
	btncartuser = new Button("Add to cart");
	btncartuser.setPrefSize(130, 35);
	
	
	vblistuser.getChildren().addAll(cupTypeComboBox,cuplistuser,cuptableuser);
	vbnameuser.getChildren().addAll(cupnameuser,ammountSpinner,priceuser,btncartuser);
	
	hbuser.getChildren().addAll(vblistuser,vbnameuser);
	
	vbuser.getChildren().addAll(mbuser,hbuser);

	
	   ammountSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
           calculatePrice(); 
       });

       
       cuptableuser.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
           if (newSelection != null) {
            
               Cup selectedCup = cuptableuser.getSelectionModel().getSelectedItem();

               
               cupnameuser.setText(selectedCup.getCupName());
               
              
               calculatePrice();
           }
       });
	
       btncartuser.setOnAction(event -> {
    	   
    	    Cup selectedCup = cuptableuser.getSelectionModel().getSelectedItem();

    	    if (selectedCup != null) {
    	        String userID = userId; 
    	        String cupID = selectedCup.getCupID(); 
    	        int quantity = ammountSpinner.getValue(); 
    	        
    	        
    	        String cupName = selectedCup.getCupName();
    	        int cupPrice = selectedCup.getCupPrice();
    	        int totalPrice = cupPrice * quantity;

    	        Cart cartItem = new Cart(userId, cupID, cupName, cupPrice, quantity, totalPrice);
    	        

    	        addToCart(userID, cupID, quantity); 
    	    } else {
    	        
    	        showAlert(AlertType.WARNING, "Warning", "Please select a cup.");
    	    }
    	});

       
       cartuser.setOnAction(e-> {
    	   openCartPage(userId);
       });
       
       logoutuser.setOnAction(e-> {
    	   Stage currentStage = (Stage) vbuser.getScene().getWindow();
		    currentStage.close();
		    

		
		    Login loginpage = new Login();
		    loginpage.initializeComponents();
		    Scene scene = new Scene(loginpage.getRoot(), 800, 800);
		    Stage stage = new Stage();
		    stage.setScene(scene);
		    stage.setTitle("cangkIR");
		    stage.show();
       });
       
       
       // Filtered list wrapping the original data
       FilteredList<Cup> filteredData = new FilteredList<>(cupdata, b -> true);

       // Add listener to search box
       cupTypeComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
    	    filteredData.setPredicate(cup -> {
    	        // If "All" is selected, show all items
    	        if (newValue == null || newValue.equals("All")) {
    	            return true;
    	        }

    	        // Cast newValue to String
    	        String selectedType = ((String) newValue).toLowerCase();

    	        // Filter based on cup type selected in ComboBox
    	        if (cup.getCupName().toLowerCase().contains(selectedType)) {
    	            return true; // Filter matches cup name
    	        }
    	        return false; // Does not match
    	    });
    	});

       // Bind the filtered list to the table
       SortedList<Cup> sortedData = new SortedList<>(filteredData);
       sortedData.comparatorProperty().bind(cuptableuser.comparatorProperty());

       cuptableuser.setItems(sortedData);
       
       
	}
	
	

    public void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
	public void openCartPage(String userId) {
		  Stage currentStage = (Stage) vbuser.getScene().getWindow();
		    currentStage.close();
		    
		    
		    CartPage cart = new CartPage();
		    cart.setUserId(userId);
		    cart.init();
		    Scene scene = new Scene(cart.getRoot(), 800, 800);
		    Stage stage = new Stage();
		    stage.setScene(scene);
		    stage.setTitle("User Page");
		    stage.show();

	}
    
	public void logout() {
		   Stage currentStage = (Stage) vbuser.getScene().getWindow();
		    currentStage.close();
		    

		
		    Login loginpage = new Login();
		    loginpage.initializeComponents();
		    Scene scene = new Scene(loginpage.getRoot(), 800, 800);
		    Stage stage = new Stage();
		    stage.setScene(scene);
		    stage.setTitle("cangkIR");
		    stage.show();
	}
    
	public void getData() {
    	cupdata.clear();
    	Connect connect = Connect.getInstance();
     	String query = "SELECT * from mscup ";
    	ResultSet resultSet = connect.execQuery(query);
    	
        try {
            

            while (resultSet.next()) {
                String CupID = resultSet.getString("CupID");
                String CupName = resultSet.getString("CupName");
                int CupPrice = resultSet.getInt("CupPrice");
        

                cupdata.add(new Cup(CupID, CupName, CupPrice));
            }

            
        } catch (SQLException e) {
            e.printStackTrace();
    
        }
        
    	
    }
	
	public void calculatePrice() {
	      Cup selectedCup = cuptableuser.getSelectionModel().getSelectedItem();
	        int quantity = ammountSpinner.getValue();
	        
	        if (selectedCup != null) {
	            int totalPrice = selectedCup.getCupPrice() * quantity;
	            priceuser.setText("Price: " + totalPrice);
	        }
	    }
	
	public void addToCart(String userID, String cupID, int quantity) {
	    Connect connect = Connect.getInstance();
	    String cartCheckQuery = "SELECT * FROM cart WHERE UserID = ? AND CupID = ?";
	    String insertQuery = "INSERT INTO cart (UserID, CupID, Quantity) VALUES (?, ?, ?)";
	    String updateQuery = "UPDATE cart SET Quantity = ? WHERE UserID = ? AND CupID = ?";

	    try {
	        PreparedStatement checkStmt = connect.con.prepareStatement(cartCheckQuery);
	        checkStmt.setString(1, userID);
	        checkStmt.setString(2, cupID);
	        ResultSet resultSet = checkStmt.executeQuery();

	        if (resultSet.next()) {
	     
	            int currentQuantity = resultSet.getInt("Quantity");
	            int newQuantity = currentQuantity + quantity;

	            PreparedStatement updateStmt = connect.con.prepareStatement(updateQuery);
	            updateStmt.setInt(1, newQuantity);
	            updateStmt.setString(2, userID);
	            updateStmt.setString(3, cupID);
	            updateStmt.executeUpdate();
	            showAlert(AlertType.INFORMATION, "Success", "Cart updated successfully!");
	        } else {
	    
	            PreparedStatement insertStmt = connect.con.prepareStatement(insertQuery);
	            insertStmt.setString(1, userID);
	            insertStmt.setString(2, cupID);
	            insertStmt.setInt(3, quantity);
	            insertStmt.executeUpdate();
	            showAlert(AlertType.INFORMATION, "Success", "Item added to the cart successfully!");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("Error adding item to the cart: " + e.getMessage());
	    }
	}
	
	
	public VBox getRoot() {
		return vbuser;
	}
	
	
}
	
	