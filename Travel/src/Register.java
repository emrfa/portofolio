
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.application.Application;
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

public class Register {
	
	VBox vb;
	HBox hb1,hb2,hb3,hb4,hb5,hb6;
	
	Label lblregister,lblusername,lblemail,lblpassword,lblgender;
	
	TextField usernametf,emailtf;
	PasswordField pfPassword;
	
	Button btnregister;
	RadioButton male,female;
	ToggleGroup genderGroup;
	
	Scene scene;
	
	 Hyperlink loginhyp;
	
	public void initialize() {
		
		 loginhyp = new Hyperlink("Already have an account? login here!");
	     loginhyp.setTextFill(Color.CORNFLOWERBLUE);
	     loginhyp.setOnAction(e -> openLoginPage());
		
		 lblregister = new Label("Register");
		 lblregister.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
		
		 lblusername = new Label("Username");
		 lblemail = new Label("Email");
		 lblpassword = new Label("Password");
		 lblgender = new Label("Gender");
		
		 male = new RadioButton("Male");
		 female = new RadioButton("Female");
		
		 btnregister = new Button("Register");
		
		
		 usernametf = new TextField();
		 emailtf = new TextField();
	
		
		 pfPassword = new PasswordField();
		
		usernametf.setPromptText("Input your username here");
		usernametf.setFocusTraversable(false); 
		
		emailtf.setPromptText("Input your email here");
		emailtf.setFocusTraversable(false);
		
		pfPassword.setPromptText("Input your password here");
		pfPassword.setFocusTraversable(false); 
		
		
		hb1 = new HBox();
		hb2 = new HBox();
		hb3 = new HBox();
		hb4 = new HBox();
		hb5 = new HBox();

		
		hb1.getChildren().add(lblusername);
		hb2.getChildren().add(lblemail);
		hb3.getChildren().add(lblpassword);
		hb4.getChildren().add(lblgender);
		hb5.getChildren().addAll(male, female);
		
		 genderGroup = new ToggleGroup();
		male.setToggleGroup(genderGroup);
		female.setToggleGroup(genderGroup);
		
		vb = new VBox(5);
		vb.getChildren().addAll(lblregister, hb1, usernametf, hb2, emailtf, hb3, pfPassword, hb4, hb5, btnregister,loginhyp);
		vb.setAlignment(Pos.CENTER);
	    vb.setPadding(new Insets(10, 50, 50, 50));
		
	    btnregister.setOnAction(e-> {
	    	validateRegis();
	    });
	
	}
	
	public void openLoginPage () {
		 Stage currentStage = (Stage) vb.getScene().getWindow();
		    currentStage.close();
		    

		
		    Login loginpage = new Login();
		    loginpage.initializeComponents();
		    Scene scene = new Scene(loginpage.getRoot(), 800, 800);
		    Stage stage = new Stage();
		    stage.setScene(scene);
		    stage.setTitle("Travel");
		    stage.show();
	}
	
	public void validateRegis() {
		String userId = generateId();
	       String username = usernametf.getText();
	        String email = emailtf.getText();
	        String password = pfPassword.getText();
	        Connect connect = Connect.getInstance();
	      
	        


	        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || (genderGroup.getSelectedToggle() == null)) {
	            showAlert("Please fill in all fields.");
	            return;
	        }

	        RadioButton selectedRadioButton = (RadioButton) genderGroup.getSelectedToggle();
	        String gender = selectedRadioButton.getText();
	     
	        if (connect.usernameExistsInDatabase(username)) {
	            showAlert("Username already exists.");
	            return;
	        }

	        if (connect.emailExistsInDatabase(email)) {
	            showAlert("Email already exists.");
	            return;
	        }

	
	        if (!email.endsWith("@gmail.com")) {
	            showAlert("Email must end with @gmail.com.");
	            return;
	        }

	   
	        if (password.length() < 8 || password.length() > 15 || !password.matches("[a-zA-Z0-9]+")) {
	            showAlert("Password must be alphanumeric with a length between 8 and 15 characters.");
	            return;
	        }

	

	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Registration");
	        alert.setHeaderText(null);
	        alert.setContentText("Registration succesful!");
	        alert.showAndWait();

	        
	    	User user = new User(userId, username, email, password, gender);
			connect.regisData(user.getUserId(), user.getUsername(), user.getUserEmail(), user.getUserPassword(), user.getUserGender());
	    }

	public String generateId() {
		  Connect connect = Connect.getInstance();
		  String query = "SELECT UserID FROM msuser ORDER BY userId DESC LIMIT 1";
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
	
	    private void showAlert(String message) {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Registration");
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	
	
	public VBox getRoot() {
		return vb;

	}
	
}