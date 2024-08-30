import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReservationPage {

    private String userId,reservationId;
    private VBox root;
    private TableView<Reservation> tableReservations;
    private ObservableList<Reservation> reservationData = FXCollections.observableArrayList();
    private Button cancelButton;
    
	Menu menuuser;
	MenuBar mbuser;
	MenuItem home,logout;
	Label reservasilbl;
	
    

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public VBox getRoot() {
        return root;
    }

    public void initialize() {
    	
    	reservasilbl = new Label("Reservasi anda");
    	reservasilbl.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
    	
    	cancelButton = new Button("Batalkan Reservasi");
    	cancelButton.setOnAction(event -> handleCancelReservation());

    	
    	menuuser = new Menu("Menu");
		mbuser = new MenuBar();
			
		home = new MenuItem("Home");
		logout = new MenuItem("Log Out");
		
		menuuser.getItems().add(home);
		menuuser.getItems().add(logout);
		mbuser.getMenus().add(menuuser);
    	
        root = new VBox(20);

        tableReservations = new TableView<>();
        TableColumn<Reservation, String> ruteCol = new TableColumn<>("Rute");
        TableColumn<Reservation, String> maskapaiCol = new TableColumn<>("Maskapai Penerbangan");
        TableColumn<Reservation, Date> tanggalCol = new TableColumn<>("Tanggal Keberangkatan");
        TableColumn<Reservation, String> layananCol = new TableColumn<>("Layanan Tambahan");

        ruteCol.setCellValueFactory(new PropertyValueFactory<>("rute"));
        maskapaiCol.setCellValueFactory(new PropertyValueFactory<>("maskapaiPenerbangan"));
        tanggalCol.setCellValueFactory(new PropertyValueFactory<>("tanggalKeberangkatan"));
        layananCol.setCellValueFactory(new PropertyValueFactory<>("layananTambahan"));

        tableReservations.getColumns().addAll(ruteCol, maskapaiCol, tanggalCol, layananCol);
        tableReservations.setItems(reservationData);

        
        getData();
        
        home.setOnAction(e-> {
        	openHomePage(userId);
        });
        
        logout.setOnAction(e-> {
        	
        	openLogin();
        });

        root.getChildren().addAll(mbuser, reservasilbl, tableReservations,cancelButton);
    }

    
    public void openLogin() {
    	Stage currentStage =(Stage) root.getScene().getWindow();
    	currentStage.close();
    	
    	Login loginpage = new Login();
        loginpage.initializeComponents();
        Scene scene = new Scene(loginpage.getRoot(), 800, 800);
    	
        Stage stage = new Stage();
        stage.setTitle("Travel");
        stage.setScene(scene);
        stage.show();
    }
    
    public void openHomePage(String userId){
	    Stage currentStage = (Stage) root.getScene().getWindow();
	    currentStage.close();
	    

	    HomePage homePage = new HomePage();
	    homePage.setUserId(userId);
	    homePage.initialize();
	    Scene scene = new Scene(homePage.getRoot(), 800, 800);
	    Stage stage = new Stage();
	    stage.setScene(scene);
	    stage.setTitle("Home Page");
	    stage.show();
    }
    
    private void getData() {
    	Connect connect = Connect.getInstance();
        String query = "SELECT reservationId, userId, rute, maskapaiPenerbangan, tanggalKeberangkatan, layananTambahan FROM reservation WHERE userId = ?";
        try {
            PreparedStatement preparedStatement = connect.con.prepareStatement(query);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            reservationData.clear();

            while (resultSet.next()) {
                String reservationId = resultSet.getString("reservationId");
                String rute = resultSet.getString("rute");
                String maskapaiPenerbangan = resultSet.getString("maskapaiPenerbangan");
                Date tanggalKeberangkatan = resultSet.getDate("tanggalKeberangkatan");
                String layananTambahan = resultSet.getString("layananTambahan");

                Reservation reservation = new Reservation(
                    reservationId,
                    userId, 
                    "", 
                    rute,
                    maskapaiPenerbangan,
                    tanggalKeberangkatan,
                    0,
                    layananTambahan
                );

                reservationData.add(reservation);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(AlertType.ERROR, "Failed to load reservations.", ButtonType.OK);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    private void handleCancelReservation() {
    	Reservation selectedReservation = tableReservations.getSelectionModel().getSelectedItem();
    	if (selectedReservation != null) {
    	     reservationId = selectedReservation.getReservationId();
    	      Alert confirmAlert = new Alert(AlertType.CONFIRMATION, "Apakah anda yakin ingin membatalkan reservasi ini?", ButtonType.YES, ButtonType.NO);
    	        confirmAlert.setTitle("Konfirmasi Pembatalan");
    	        confirmAlert.setHeaderText(null);
    	        confirmAlert.showAndWait().ifPresent(response -> {
    	            if (response == ButtonType.YES) {
    	                deleteReservation(selectedReservation);
    	            }
    	        });
    	   
    	} else {
    	   
    	    Alert alert = new Alert(AlertType.ERROR, "Tidak ada penerbangan yang dipilih.", ButtonType.OK);
    	    alert.setTitle("Error");
    	    alert.setHeaderText(null);
    	    alert.showAndWait();
    	}

  
    }

    private void deleteReservation(Reservation reservation) {
        Connect connect = Connect.getInstance();
        String query = "DELETE FROM reservation WHERE reservationId = ?";
        try {
            PreparedStatement preparedStatement = connect.con.prepareStatement(query);
            preparedStatement.setString(1, reservationId);
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                // Success popup
                Alert successAlert = new Alert(AlertType.INFORMATION, "Reservasi berhasil dibatalkan!", ButtonType.OK);
                successAlert.setTitle("Success");
                successAlert.setHeaderText(null);
                successAlert.showAndWait();

                reservationData.clear();
                getData(); 
            } else {
                // Failure popup
                Alert failureAlert = new Alert(AlertType.ERROR, "Gagal membatalkan reservasi.", ButtonType.OK);
                failureAlert.setTitle("Error");
                failureAlert.setHeaderText(null);
                failureAlert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert errorAlert = new Alert(AlertType.ERROR, "Gagal membatalkan reservasi.", ButtonType.OK);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText(null);
            errorAlert.showAndWait();
        }
    }
}
