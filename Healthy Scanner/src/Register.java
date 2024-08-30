
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Register  {
	
	

	
	 VBox vbreg;
	 HBox hbreg1,hbreg2,hbreg3,hbreg4,hbreg5,hbreg6;
	
	 Label registerreg,usernamereg,emailreg,passwordreg;
	
	 TextField tfusernamereg,tfemailreg;
	 PasswordField pfpasswordreg;
	 
	 Button btnregisterreg;
	
	
	 Hyperlink loginLink;

	public void initialize() {
		
		
		 loginLink = new Hyperlink("Already have an account? login here!");
	     loginLink.setTextFill(Color.CORNFLOWERBLUE);
	     loginLink.setOnAction(e -> openLoginPage());

	        
		registerreg = new Label("Register");
		registerreg.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		
		 usernamereg = new Label("username");
		 emailreg = new Label("email");
		 passwordreg = new Label("password");
		
		
		 btnregisterreg = new Button("Register");
		
		
		 tfusernamereg = new TextField();
		 tfemailreg = new TextField();
	
		
		 pfpasswordreg = new PasswordField();
		
		tfusernamereg.setPromptText("Input your username here");
		tfusernamereg.setFocusTraversable(false); 
		
		tfemailreg.setPromptText("Input your email here");
		tfemailreg.setFocusTraversable(false);
		
		pfpasswordreg.setPromptText("Input your password here");
		pfpasswordreg.setFocusTraversable(false); 
		
		
		hbreg1 = new HBox();
		hbreg2 = new HBox();		
		hbreg3 = new HBox();

		
		hbreg1.getChildren().add(usernamereg);
		hbreg2.getChildren().add(emailreg);
		hbreg3.getChildren().add(passwordreg);
		vbreg = new VBox(5);
		
		vbreg.getChildren().addAll(registerreg, hbreg1, tfusernamereg, hbreg2, tfemailreg, hbreg3, pfpasswordreg, btnregisterreg,loginLink);
		vbreg.setAlignment(Pos.CENTER);
	    vbreg.setPadding(new Insets(10, 50, 50, 50));
	    
	    btnregisterreg.setOnAction(e -> {
            if (isValidInput()) {
                registerUser();
            } else {
                Alert invalidAlert = new Alert(Alert.AlertType.WARNING);
                invalidAlert.setHeaderText("Invalid Input");
                invalidAlert.setContentText("Please fill out all fields!");
                invalidAlert.showAndWait();
            }
        });
    }
	
	  private boolean isValidInput() {
	        return !tfusernamereg.getText().isEmpty() &&
	               !tfemailreg.getText().isEmpty() &&
	               !pfpasswordreg.getText().isEmpty();
	    }

	private String generateUserID() {
	    Connect connect = Connect.getInstance();
	    String query = "SELECT userId FROM users ORDER BY userId DESC LIMIT 1";
	    try {
	        ResultSet resultSet = connect.execQuery(query);
	        if (resultSet.next()) {
	            String lastUserID = resultSet.getString("userId");
	            int lastNumber = Integer.parseInt(lastUserID.substring(2));
	            int nextNumber = lastNumber + 1;
	            String formattedNumber = String.format("%03d", nextNumber);
	            return "US" + formattedNumber;
	        } else {
	            return "US001"; 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	public void registerUser() {
		Connect connect = Connect.getInstance();
		String userId = generateUserID(); 
		String username = tfusernamereg.getText();
		String password = pfpasswordreg.getText();
		String email = tfemailreg.getText();
		
		User newUser = new User(userId, username, email, password);
		connect.insertUserData(newUser.getUserId(), newUser.getUsername(), newUser.getEmail(), newUser.getPassword());
		
	    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
	    successAlert.setHeaderText("Registration Successful");
	    successAlert.setContentText("You have successfully registered!");
	    successAlert.showAndWait();

	    openLoginPage();
	}
	
    private void openLoginPage() {
		// TODO Auto-generated method stub
    	  Stage currentStage = (Stage) vbreg.getScene().getWindow(); 
     	 currentStage.close(); 
    	 Login loginpage = new Login();
    	 loginpage.initialize();
         Scene scene = new Scene(loginpage.getRoot(), 900, 600);
         Stage stage = new Stage();
         stage.setScene(scene);
         stage.setTitle("HealthyScanner");
         stage.show();
	}



	public VBox getRoot() {
        return vbreg;
    }
	

}

