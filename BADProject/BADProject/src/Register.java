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
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Register {
    private BorderPane borderPane;
    private GridPane gridPane;

    private Label registerLabel, usernameLabel,  passwordLabel;
    private TextField tfUsername ;
    private PasswordField pfPassword;
    private Button btnRegister;
    private Hyperlink linklogin;
    
    private Connector connector;
    
    public Register() {
        this.connector = Connector.getInstance();
    }

    public void initialize() {
        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 50, 50, 50));

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        registerLabel = new Label("Register");
        registerLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        usernameLabel = new Label("username");
   
        passwordLabel = new Label("password");

        tfUsername = new TextField();
        tfUsername.setPromptText("Input your username here");
        tfUsername.setFocusTraversable(false);


        pfPassword = new PasswordField();
        pfPassword.setPromptText("Input your password here");
        pfPassword.setFocusTraversable(false);

        btnRegister = new Button("Register");

        linklogin = new Hyperlink("Already have an account? login here!");
        linklogin.setTextFill(Color.CORNFLOWERBLUE);
        linklogin.setOnAction(e -> openLoginPage());

        gridPane.add(registerLabel, 0, 0, 2, 1);
        gridPane.add(usernameLabel, 0, 1);
        gridPane.add(tfUsername, 1, 1);
        gridPane.add(passwordLabel, 0, 2);
        gridPane.add(pfPassword, 1, 2);
        gridPane.add(btnRegister, 0, 4, 2, 1);
        gridPane.add(linklogin, 0, 5, 2, 1);

        borderPane.setCenter(gridPane);
        
        btnRegister.setOnAction(e -> {
            if (isValidInput()) {
          
                registerUser();
            } else {
             
                showEmptyFieldAlert();
            }
        });

    }
    
    private boolean isValidInput() {
        return !tfUsername.getText().isEmpty() &&
               !pfPassword.getText().isEmpty();
    }

    private void showEmptyFieldAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Empty Fields");
        alert.setContentText("Harap isi semua fields!");
        alert.showAndWait();
    }

    private void registerUser() {
    	Connector connect = Connector.getInstance();
		String userId = generateUserID(); 
		String username = tfUsername.getText();
		String password = pfPassword.getText();
	
		User newUser = new User(userId, username, password);
		connect.addUser(newUser.getUserId(), newUser.getUsername(), newUser.getPassword());
		
	    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
	    successAlert.setHeaderText("Registrati Berhasil");
	    successAlert.setContentText("Registrasi Berhasil dilakukan!");
	    successAlert.showAndWait();

	    openLoginPage();

    }
    
    private void showRegistrationSuccessAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Registrati Berhasil");
        alert.setContentText("Registrasi Berhasil dilakukan!");
        alert.showAndWait();

 
        openLoginPage();
    }
    
    private void showRegistrationErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Registrasi Gagal ");
        alert.setContentText("Pastikan kembali data registrasi.");
        alert.showAndWait();
    }
    
    private void openLoginPage() {
        Stage currentStage = (Stage) borderPane.getScene().getWindow();
        currentStage.close();
        Login loginPage = new Login();
        loginPage.initialize();
        Scene scene = new Scene(loginPage.getRoot(), 900, 600);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("MovieSubscription");
        stage.show();
    }
    
    private String generateUserID() {
        String query = "SELECT MAX(userId) AS maxUserId FROM user";
        try {
            if (connector != null && connector.connection != null && !connector.connection.isClosed()) {
                ResultSet resultSet = connector.execQuery(query);
                if (resultSet.next()) {
                    String maxUserId = resultSet.getString("maxUserId");
                    if (maxUserId != null) {
                        int lastNumber = Integer.parseInt(maxUserId.substring(2));
                        int nextNumber = lastNumber + 1;
                        String formattedNumber = String.format("%03d", nextNumber);
                        return "US" + formattedNumber;
                    }
                }
            } 
        } catch (SQLException e) {
            e.printStackTrace();
            showRegistrationErrorAlert();
        } finally {
           
            if (connector != null && connector.connection != null) {
                try {
                    connector.connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return "US001";

    }


    public BorderPane getRoot() {
        return borderPane;
    }
}
