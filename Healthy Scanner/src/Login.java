import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Login implements EventHandler<ActionEvent> {
     VBox vb;
    HBox hb1, hb2;

    
     Hyperlink registerLink;
     Label username, password, login;
     PasswordField pfpassword;
     TextField tfusername;
     Button btnlogin;

    Alert emptyAlert;

    public void initialize() {
   

        login = new Label("Login");
        login.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        username = new Label("Username");

        password = new Label("Password");

        tfusername = new TextField();
        pfpassword = new PasswordField();
        btnlogin = new Button("Login");
        btnlogin.setMaxSize(50, 300);

        tfusername.setPromptText("Input your username here");
        tfusername.setFocusTraversable(false);

        pfpassword.setPromptText("Input your password here");
        pfpassword.setFocusTraversable(false);

        registerLink = new Hyperlink("Don't have an account yet? Register here!");
        registerLink.setTextFill(Color.CORNFLOWERBLUE);
        registerLink.setOnAction(e -> openRegisterPage());

        vb = new VBox(5);
        vb.setPadding(new Insets(10, 50, 50, 50));

        hb1 = new HBox();
        hb1.getChildren().add(username);

        hb2 = new HBox();
        hb2.getChildren().add(password);

        vb.getChildren().addAll(login, hb1, tfusername, hb2, pfpassword, btnlogin, registerLink);
        vb.setAlignment(Pos.CENTER);
        
        btnlogin.setOnAction(e -> {
        validateLogin();
        });
    }

    private void openRegisterPage() {
		// TODO Auto-generated method stub
    	  Stage currentStage = (Stage) vb.getScene().getWindow(); 
    	 currentStage.close(); 
    	 Register register = new Register();
    	 register.initialize();
         Scene scene = new Scene(register.getRoot(), 800, 600);
         Stage stage = new Stage();
         stage.setScene(scene);
         stage.setTitle("HealthyScanner");
         stage.show();
         
	}

    public void validateLogin() {
        String username = tfusername.getText();
        String password = pfpassword.getText();

        Connect connect = Connect.getInstance();
        String query = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";

        ResultSet resultSet = connect.execQuery(query);
        try {
            if (resultSet.next()) {
            	String userId = connect.getUserIdFromUsername(username); 
                if (userId != null) {
                    openHalamanFisik(userId); 
                } 
            } else {
                initAlert();
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void openHalamanFisik(String userId) {
		// TODO Auto-generated method stub
    	Stage currentStage = (Stage) vb.getScene().getWindow();
        currentStage.close();

        HalamanFisik fisik = new HalamanFisik();
        fisik.setUserId(userId); 
        fisik.getData();
        fisik.inittable();
        VBox fisikLayout = fisik.getRoot();
        Scene scene = new Scene(fisikLayout, 900, 600);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Halaman Fisik");
        stage.show();
         
	}
    
	public void initAlert() {
        
        Alert loginAlert = new Alert(Alert.AlertType.WARNING);
        loginAlert.setHeaderText("Login Failed");
        loginAlert.setContentText("Invalid username or password!");
        loginAlert.showAndWait();
    }

    @Override
    public void handle(ActionEvent event) {
    	 if (event.getSource() == btnlogin) {
    	        String username = tfusername.getText();
    	        String password = pfpassword.getText();
    	        if (username.isEmpty() || password.isEmpty()) {
    	            initAlert(); 
    	        } else {
    	            validateLogin(); 
    	        }
        }
    }

    public VBox getRoot() {
        return vb;
    }
    
    
}