package cangkIR;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Management {

	private String userId;
	
	Label management, cupnameadmin, cuppriceadmin;
	TextField admincupname,admincupprice;
	
	Menu menuadmin;
	MenuBar mbadmin;
	MenuItem logoutadmin,homeadmin;
	
	private VBox rootadmin, vbadmin1,vbadmin2;
	HBox hboxadmin;
	Button addcup,updateprice,removecup;
	TableView <Cup> cuptableadmin;
	ObservableList<Cup> cupdataadmin = FXCollections.observableArrayList();
	
	public void setUserId(String userId) {
        this.userId = userId;
    }
	
	public void init() {
		
		menuadmin = new Menu("Menu");
		logoutadmin = new MenuItem("Log out");
		homeadmin = new MenuItem("Cup Management");
		mbadmin = new MenuBar();
		
		menuadmin.getItems().addAll(homeadmin,logoutadmin);
		mbadmin.getMenus().add(menuadmin);
		
		
		
		getData();
		cuptableadmin = new TableView<>();
		management = new Label("Cup Management");
		management.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		
		cupnameadmin = new Label("Cup Name");
		
		admincupname = new TextField();
		admincupname.setPromptText("Input cupname here");
        admincupname.setFocusTraversable(false);
		
        cuppriceadmin = new Label("Cup Price");
        admincupprice = new TextField();
        admincupprice.setPromptText("Input cup price here");
        admincupprice.setFocusTraversable(false);
		
		addcup = new Button("Add cup");
		updateprice = new Button("Update Price");
		removecup = new Button ("Remove Cup");
		
		
		vbadmin1 = new VBox(15);
		vbadmin1.getChildren().addAll(management,cuptableadmin);
		
		vbadmin2 = new VBox(15);
		vbadmin2.getChildren().addAll(cupnameadmin,admincupname,cuppriceadmin,admincupprice, addcup,updateprice,removecup);
		
		hboxadmin = new HBox(10);
		hboxadmin.getChildren().addAll(vbadmin1,vbadmin2);
		
		TableColumn<Cup, String> namecol = new TableColumn<>("Cup Name");
	    namecol.setMinWidth(150);
	    namecol.setCellValueFactory(new PropertyValueFactory<>("CupName"));
	    
	    TableColumn<Cup, Integer> pricecol = new TableColumn<>("Cup Price");
	    pricecol.setMinWidth(150);
	    pricecol.setCellValueFactory(new PropertyValueFactory<>("CupPrice"));
	    cuptableadmin.getColumns().addAll(namecol, pricecol); 
	    cuptableadmin.setMaxWidth(300);
	    cuptableadmin.setItems(cupdataadmin);
	    
	    cuptableadmin.setOnMouseClicked(event -> {
	        Cup selectedCup = cuptableadmin.getSelectionModel().getSelectedItem(); 
	        if (selectedCup != null) {
	            admincupname.setText(selectedCup.getCupName()); 
	            admincupprice.setText(String.valueOf(selectedCup.getCupPrice())); 
	        }
	    });
	    
	    removecup.setOnAction(e-> {
	    	removeCupFromDatabase();
	    });
	    
	    updateprice.setOnAction(event -> {
	        updateCupPrice();
	        cuptableadmin.refresh();
	    });
	    
	    addcup.setOnAction(e -> {
	    	addNewCup();
	    });
	    
	    logoutadmin.setOnAction(e-> {
	    	 Stage currentStage = (Stage) rootadmin.getScene().getWindow();
			    currentStage.close();
			    

			
			    Login loginpage = new Login();
			    loginpage.initializeComponents();
			    Scene scene = new Scene(loginpage.getRoot(), 800, 800);
			    Stage stage = new Stage();
			    stage.setScene(scene);
			    stage.setTitle("cangkIR");
			    stage.show();
	    });
	    
	    rootadmin= new VBox(15);
	    
	    rootadmin.getChildren().addAll(mbadmin, hboxadmin);
	    
		
	}
	
	private void removeCupFromDatabase() { 
		Cup selectedCup = cuptableadmin.getSelectionModel().getSelectedItem();

    if (selectedCup != null) {
        String cupID = selectedCup.getCupID(); 
        String deleteQuery = "DELETE FROM mscup WHERE CupID = '" + cupID + "'";
        
        Connect connect = Connect.getInstance();
        connect.execUpdate(deleteQuery); // 
        
       
        cupdataadmin.remove(selectedCup);
        admincupname.clear();
        admincupprice.clear();
    } else {
    	 Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setTitle("Error");
         alert.setHeaderText("Cup Management");
         alert.setContentText("Please Select Cup To delete");
         alert.showAndWait();
    }
	}
	
	private void updateCupPrice() {
	    Cup selectedCup = cuptableadmin.getSelectionModel().getSelectedItem();

	    if (selectedCup != null) {
	        String updatedPriceText = admincupprice.getText();
	        
	        try {
	            int updatedPrice = Integer.parseInt(updatedPriceText);

	            if (updatedPrice >= 5000 && updatedPrice <= 1000000) {
	                
	                String cupID = selectedCup.getCupID();
	                String updateQuery = "UPDATE mscup SET CupPrice = " + updatedPrice + " WHERE CupID = '" + cupID + "'";
	                
	                Connect connect = Connect.getInstance();
	                connect.execUpdate(updateQuery);

	            
	                selectedCup.setCupPrice(updatedPrice);
	            } else {
	            	 Alert alert = new Alert(Alert.AlertType.ERROR);
	                 alert.setTitle("Error");
	                 alert.setHeaderText("Cup Management");
	                 alert.setContentText("Price must be 5000 - 1000000");
	                 alert.showAndWait();
	            }
	        } catch (NumberFormatException e) {
	        	 Alert alert = new Alert(Alert.AlertType.ERROR);
                 alert.setTitle("Error");
                 alert.setHeaderText("Cup Management");
                 alert.setContentText("Price must be a number");
                 alert.showAndWait();
	        }
	    } else {
	    	 Alert alert = new Alert(Alert.AlertType.ERROR);
	         alert.setTitle("Error");
	         alert.setHeaderText("Cup Management");
	         alert.setContentText("Please Select Cup To update");
	         alert.showAndWait();
	    }
	}
	
	private void addNewCup() {
        String newCupName = admincupname.getText();
        String newCupPriceText = admincupprice.getText();
        Connect connect = Connect.getInstance();

        if (!newCupName.isEmpty() && !newCupPriceText.isEmpty()) {
            try {
                int newCupPrice = Integer.parseInt(newCupPriceText);
                if (newCupPrice >= 5000 && newCupPrice <= 1000000) {
                    boolean isNameUnique = !Cup.isCupNameExists(cupdataadmin, newCupName);

                    if (isNameUnique) {
                        String newCupID = Cup.generateCupID();
                  
                        Cup newCup = new Cup(newCupID, newCupName, newCupPrice);
                        connect.addCupToDatabase(newCupID, newCupName, newCupPrice);
                        cupdataadmin.add(newCup);
                      
                      
                        admincupname.clear();
                        admincupprice.clear();
                        
                        showAlert(Alert.AlertType.INFORMATION, "Add Success", "New Cup Added!");
                    } else {
                        showAlert(Alert.AlertType.ERROR, "Error",
                                "Cup already exists");
                    }
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error",
                            "Price must be between 5000 - 1000000!");
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Price must be number");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Please fill out all fields");
        }
    }

  
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText("Cup Management");
        alert.setContentText(message);
        alert.showAndWait();
    }
	
	
	private void getData() {
		// TODO Auto-generated method stub
		cupdataadmin.clear();
    	Connect connect = Connect.getInstance();
     	String query = "SELECT * from mscup ";
    	ResultSet resultSet = connect.execQuery(query);
    	
        try {
            

            while (resultSet.next()) {
                String CupID = resultSet.getString("CupID");
                String CupName = resultSet.getString("CupName");
                int CupPrice = resultSet.getInt("CupPrice");
        

                cupdataadmin.add(new Cup(CupID, CupName, CupPrice));
            }

            
        } catch (SQLException e) {
            e.printStackTrace();
    
        }
	}

	public VBox getRoot() {
		return rootadmin;
	}
}
