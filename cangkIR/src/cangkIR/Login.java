package cangkIR;


import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Login  {

    GridPane gridPane;
    BorderPane borderPane;

    Label login, username, password;
    TextField tfusername;
    PasswordField pfpassword;
    Button btnlogin;
    Hyperlink registerLink;

    Alert emptyAlert;


   

    public void initializeComponents() {
        login = new Label("Login");
        login.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        username = new Label("Username");
        password = new Label("Password");

        tfusername = new TextField();
        pfpassword = new PasswordField();
        btnlogin = new Button("Login");

        tfusername.setPromptText("Input your username here");
        tfusername.setFocusTraversable(false);

        pfpassword.setPromptText("Input your password here");
        pfpassword.setFocusTraversable(false);

        registerLink = new Hyperlink("Don't have an account yet? Register here!");
        registerLink.setTextFill(Color.CORNFLOWERBLUE);
        registerLink.setOnAction(e-> {
        	
        	  Stage currentStage = (Stage) borderPane.getScene().getWindow(); 
         	 currentStage.close(); 
         	 Register reg = new Register();
         	 reg.initialize();
              Scene scene = new Scene(reg.getRoot(), 800, 800);
              Stage stage = new Stage();
              stage.setScene(scene);
              stage.setTitle("cangkIR");
              stage.show();
        });
        
        gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 50, 50, 50));
        gridPane.setVgap(5);
        gridPane.setHgap(5);

        GridPane.setConstraints(login, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(username, 1, 1, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(tfusername, 1, 2, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(password, 1, 3, 1, 1, HPos.LEFT, VPos.CENTER);
        GridPane.setConstraints(pfpassword, 1, 4, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(btnlogin, 1, 5, 1, 1, HPos.CENTER, VPos.CENTER);
        GridPane.setConstraints(registerLink, 1, 6, 1, 1, HPos.CENTER, VPos.CENTER);

        
        gridPane.getChildren().addAll(login, username, tfusername, password, pfpassword, btnlogin, registerLink);
        
       
        
        
        ColumnConstraints colConstraints = new ColumnConstraints();
        colConstraints.setFillWidth(true);
        colConstraints.setHgrow(Priority.ALWAYS);
        gridPane.getColumnConstraints().addAll(new ColumnConstraints(), colConstraints);
    
        borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        borderPane.setPadding(new Insets(200, 50, 50, 50));
     

        btnlogin.setOnAction(e -> validateLogin());
    }


    public void validateLogin() {
    	    String username = tfusername.getText();
    	    String password = pfpassword.getText();

    	    if (username.isEmpty() || password.isEmpty()) {
    	        emptyAlert();
    	    } else {
    	        Connect connect = Connect.getInstance();
    	        String query = "SELECT * FROM msuser WHERE Username = '" + username + "' AND UserPassword = '" + password + "'";

    	        ResultSet resultSet = connect.execQuery(query);
    	        try {
    	            if (resultSet.next()) {
    	            	 String role = resultSet.getString("UserRole");
    	                String userId = connect.getUserIdFromUsername(username);
    	                if (userId != null) {
    	                    if (role.equalsIgnoreCase("admin")) {
    	                        openAdminPage(userId);
    	                    	System.out.println("login admin");
    	                    } else if (role.equalsIgnoreCase("user")) {
    	                        openCustomerPage(userId);
    	                    	System.out.println("logincustomer");
    	                    }
    	                }
    	            } else {
    	                invalidCredentialsAlert();
    	            }
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	        }
    	    }
    	
    }

    public void openAdminPage(String userId) {
    	Stage currentStage = (Stage) borderPane.getScene().getWindow();
	    currentStage.close();
	    

	    Management adminPage = new Management();
	    adminPage.setUserId(userId);
	    adminPage.init();
	    Scene scene = new Scene(adminPage.getRoot(), 800, 800);
	    Stage stage = new Stage();
	    stage.setScene(scene);
	    stage.setTitle("Admin Page");
	    stage.show();
    }
    
   public void openCustomerPage(String userId){
	    Stage currentStage = (Stage) borderPane.getScene().getWindow();
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
    
    public void invalidCredentialsAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Login Failed");
        alert.setContentText("Invalid username or password");
        alert.showAndWait();
    }

    public void emptyAlert() {
        Alert loginAlert = new Alert(Alert.AlertType.WARNING);
        loginAlert.setHeaderText("Login Failed");
        loginAlert.setContentText("Please fill username and password");
        loginAlert.showAndWait();
    }

    public BorderPane getRoot() {
    
  
        BorderPane.setAlignment(gridPane, Pos.CENTER); 
        return borderPane;
    }

}
