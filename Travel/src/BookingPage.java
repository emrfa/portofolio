import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;




public class BookingPage {
	
	VBox vbbooking;
	HBox hblayanan;
	
	 Label headerLbl, rutelbl, datelbl, maskapailbl, durasilbl, kursilbl, layananlbl, totalHargaLbl;
	    
	
	Penerbangan selectedPenerbangan;
    ComboBox<String> layananComboBox;
    
    Button checkoutButton;
    
    private String userId;
	
    
    public void initialize(Penerbangan penerbangan, String userId) {
        this.selectedPenerbangan = penerbangan;
        this.userId = userId;
        
//		Button
        checkoutButton = new Button("Checkout Penerbangan");
        checkoutButton.setOnAction(event -> saveBooking());
        
        
//		Scene
		vbbooking = new VBox(5);
		hblayanan = new HBox();
	
//		ComboBox
        layananComboBox = new ComboBox<>();
        layananComboBox.getItems().addAll("Tidak ada", "Extra Bagasi", "Extra Makanan");
        layananComboBox.setValue("Tidak ada");
        


		
//		Label
		
		 Font labelFont = Font.font("Verdana", 12);
		 
		 headerLbl = new Label("Penerbangan anda");
	     headerLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
	        
	     rutelbl = new Label("Rute: " + selectedPenerbangan.getRute());
	     rutelbl.setFont(labelFont);
	       
	        
	     datelbl = new Label("Tanggal Berangkat: " + selectedPenerbangan.getTanggalBerangkat().toString());
	     datelbl.setFont(labelFont);
	     maskapailbl = new Label("Maskapai: " + selectedPenerbangan.getMaskapaiPenerbangan());
	     maskapailbl.setFont(labelFont);
	     durasilbl = new Label("Durasi Penerbangan: " + selectedPenerbangan.getDurasiPenerbangan());
	     durasilbl.setFont(labelFont);
	        
	     kursilbl = new Label ("Kelas Kursi: " + selectedPenerbangan.getKelasPenerbangan());
	     kursilbl.setFont(labelFont);
	     
	     layananlbl = new Label("Layanan tambahan: ");
	     layananlbl.setFont(labelFont);
	     
	     totalHargaLbl = new Label("Total Harga: Rp." + String.format("%.2f", calculateTotalPrice()));
	     totalHargaLbl.setFont(labelFont);
	     
	     layananComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
	     totalHargaLbl.setText("Total Harga: Rp." + String.format("%.2f", calculateTotalPrice()));
	        });
	     
	     hblayanan.getChildren().addAll(layananlbl, layananComboBox);
	     vbbooking.getChildren().addAll(headerLbl,rutelbl,datelbl,maskapailbl,durasilbl, kursilbl,hblayanan,totalHargaLbl,checkoutButton);
	}
	
	  private double calculateTotalPrice() {
	        double basePrice = selectedPenerbangan.getPrice();
	        String selectedLayanan = layananComboBox.getValue();
	        
	        double additionalPrice = 0;
	        if ("Extra Bagasi".equals(selectedLayanan)) {
	            additionalPrice = 100000;
	        } else if ("Extra Makanan".equals(selectedLayanan)) {
	            additionalPrice = 50000;
	        }
	        
	        return basePrice + additionalPrice;
	    }
	  
	  private void saveBooking() {
		    Connect connect = Connect.getInstance();
		    String reservationId = generateReservationId(); 
		    String rute = selectedPenerbangan.getRute();
		    String maskapaiPenerbangan = selectedPenerbangan.getMaskapaiPenerbangan();
		    Date tanggalKeberangkatan = Date.valueOf(selectedPenerbangan.getTanggalBerangkat().toLocalDate());
		    double totalPrice = calculateTotalPrice();
		    String layananTambahan = layananComboBox.getValue();
		    
		    Reservation reservation = new Reservation(reservationId, userId, selectedPenerbangan.getPenerbanganId(),
		            rute, maskapaiPenerbangan, tanggalKeberangkatan, totalPrice,layananTambahan);
		      
		    
		    String query = "INSERT INTO reservation (reservationId, userId, penerbanganId, rute, maskapaiPenerbangan, tanggalKeberangkatan, totalPrice, layananTambahan) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		    try {
		        PreparedStatement preparedStatement = connect.con.prepareStatement(query);
		        preparedStatement.setString(1, reservationId);
		        preparedStatement.setString(2, userId);
		        preparedStatement.setString(3, selectedPenerbangan.getPenerbanganId());
		        preparedStatement.setString(4, rute);
		        preparedStatement.setString(5, maskapaiPenerbangan);
		        preparedStatement.setDate(6, tanggalKeberangkatan);
		        preparedStatement.setDouble(7, totalPrice);
		        preparedStatement.setString(8, layananTambahan);
		        
		        preparedStatement.executeUpdate();
		        Alert alert = new Alert(AlertType.INFORMATION, "Booking Berhasil Disimpan.", ButtonType.OK);
		        alert.setTitle("Success");
		        alert.setHeaderText(null);
		        alert.showAndWait();
		    } catch (SQLException e) {
		        e.printStackTrace();
		        Alert alert = new Alert(AlertType.ERROR, "Failed to save booking.", ButtonType.OK);
		        alert.setTitle("Error");
		        alert.setHeaderText(null);
		        alert.showAndWait();
		    }
		    
		    ((Stage) vbbooking.getScene().getWindow()).close();
	   
		    HomePage homePage = new HomePage();
		    homePage.setUserId(userId);
		    homePage.initialize();
		    Scene scene = new Scene(homePage.getRoot(), 800, 800);
		    Stage stage = new Stage();
		    stage.setScene(scene);
		    stage.setTitle("Home Page");
		    stage.show();
		    
		    
		}
	
	   private String generateReservationId() {
	        return "RE" + String.format("%03d", getNextReservationNumber());
	    }

	   private int getNextReservationNumber() {
		    int nextNumber = 1;
		    Connect connect = Connect.getInstance();
		    String query = "SELECT COUNT(*) AS totalReservations FROM reservation";
		    
		    try (PreparedStatement pstmt = connect.con.prepareStatement(query);
		         ResultSet rs = pstmt.executeQuery()) {
		        if (rs.next()) {
		            nextNumber = rs.getInt("totalReservations") + 1;
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    
		    return nextNumber;
		}
	  
	  
	public VBox getRoot() {
		return vbbooking;
	}
}
