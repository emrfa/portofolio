import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Login implements EventHandler<ActionEvent> {
    private BorderPane borderPane;
    private GridPane gridPane;
    private HBox hboxLogin,hboxbtn;

    private Hyperlink registerLink;
    private Label lblusername, lblpassword, lbllogin;
    private PasswordField passwordfield;
    private TextField usernamefield;
    private Button btnlogin;

    private Alert AlertEmpty;

    public void initialize() {
        borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 50, 50, 50));

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        lbllogin = new Label("Login");
        lbllogin.setFont(Font.font("Roboto", FontWeight.BOLD, 15));
        hboxLogin = new HBox(lbllogin);
        hboxLogin.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(hboxLogin, 2);
        GridPane.setConstraints(hboxLogin, 0, 0,2,1);


        lblusername = new Label("Username");
        GridPane.setConstraints(lblusername, 0, 1);

        lblpassword = new Label("Password");
        GridPane.setConstraints(lblpassword, 0, 2);

        usernamefield = new TextField();
        usernamefield.setPromptText("Input your username here");
        usernamefield.setFocusTraversable(false);
        GridPane.setConstraints(usernamefield, 1, 1);

        passwordfield = new PasswordField();
        passwordfield.setPromptText("Input your password here");
        passwordfield.setFocusTraversable(false);
        GridPane.setConstraints(passwordfield, 1, 2);

        btnlogin = new Button("Login");
        btnlogin.setMaxSize(50, 300);
        hboxbtn = new HBox();
        hboxbtn.getChildren().add(btnlogin);
        hboxbtn.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(hboxbtn, 2); 
        GridPane.setConstraints(hboxbtn, 0, 3, 2, 1); 

 

        registerLink = new Hyperlink("Don't have an account yet? Register here!");
        registerLink.setTextFill(Color.CORNFLOWERBLUE);
        registerLink.setOnAction(e -> openRegisterPage());
        GridPane.setConstraints(registerLink, 0, 4, 2, 1);

        gridPane.getChildren().addAll(hboxLogin, lblusername, lblpassword, usernamefield, passwordfield, hboxbtn, registerLink);

        btnlogin.setOnAction(e -> {
            checkData();
        });

        borderPane.setCenter(gridPane);
    }

    private void openRegisterPage() {
        Stage currentStage = (Stage) borderPane.getScene().getWindow();
        currentStage.close();
        Register register = new Register();
        register.initialize();
        Scene scene = new Scene(register.getRoot(), 900, 500);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("MovieSubscription");
        stage.show();
    }

    public void checkData() {
        String username = usernamefield.getText();
        String password = passwordfield.getText();

        Connector connect = Connector.getInstance();
        String query = "SELECT * FROM user WHERE username = '" + username + "' AND password = '" + password + "'";

        ResultSet resultSet = connect.execQuery(query);
        try {
            if (resultSet.next()) {
                String userId = connect.getUserId(username);
                if (userId != null) {
                    openMovie(userId);
                }
            } else {
                initializeAlert();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void openMovie(String userId) {
        System.out.println("Opening MovieAppGUI for user: " + userId);

        Stage currentStage = (Stage) borderPane.getScene().getWindow();
        currentStage.close();

        MovieAppGUI movie = new MovieAppGUI();
        movie.setUserId(userId);
        movie.getData();
        movie.initialize();
        VBox movieLayout = movie.getRootPane(); 
        Scene scene = new Scene(movieLayout, 900, 500);
        
        

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Home");
        stage.show();
    }

    public void initializeAlert() {
        Alert loginAlert = new Alert(Alert.AlertType.ERROR);
        loginAlert.setHeaderText("Login Gagal");
        loginAlert.setContentText(" Username atau Password invalid");
        loginAlert.showAndWait();
    }

    @Override
    public void handle(ActionEvent event) {
    	 if (event.getSource() == btnlogin) {
    	        String username = usernamefield.getText();
    	        String password = passwordfield.getText();
    	        if (username.isEmpty() || password.isEmpty()) {
    	            initializeAlert();
    	        } else {
    	            checkData();
    	        }
    	    }
    }

    public BorderPane getRoot() {
        return borderPane;
    }
}
