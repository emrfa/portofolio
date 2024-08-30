import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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

public class HomePage {

	private String userId;
	
// DatePicker
	DatePicker datePicker;
	
	
//	Scene
	VBox vbhome,vbs1,informasiBox;
	HBox hb1,hbsearch,informasiAndBookingBox;
	
//	Menu
	Menu menuuser;
	MenuBar mbuser;
	MenuItem home,reservasi,logout;
	
//	Table
	TableView<Penerbangan> tablePenerbangan;
	 ObservableList<Penerbangan> dataPenerbangan = FXCollections.observableArrayList();
	
//	TextField
	 TextField searchbarRute;
	 TextField searchbarMaskapai;
	 
// ComboBox
	    ComboBox<String> comboBoxKelasPenerbangan;
	    
//	    Label
	    Label searchlbl, infolbl,detailInfoLbl,informasiPenerbanganLbl ;
	    
//	    Button 
	    Button bookingButton;
	 
	public void setUserId(String userId) {
        this.userId = userId;
	}


	public void initialize() {
		getData();
		
		 bookingButton = new Button("Booking Penerbangan");
	    bookingButton.setOnAction(event -> showBookingPage());
		
//		Label
		searchlbl = new Label("Cari Penerbangan");
		searchlbl.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		
		informasiPenerbanganLbl = new Label("Informasi Penerbangan");
		informasiPenerbanganLbl.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

		detailInfoLbl = new Label();
		detailInfoLbl.setFont(Font.font("Verdana", 12));
		detailInfoLbl.setWrapText(true); 

		
//		Date Picker
		datePicker = new DatePicker();
		
//		ComboBox
		comboBoxKelasPenerbangan = new ComboBox<>();
        comboBoxKelasPenerbangan.setPromptText("Pilih Kelas Penerbangan");
        comboBoxKelasPenerbangan.getItems().addAll("Semua", "Bisnis", "Ekonomi");
        comboBoxKelasPenerbangan.setValue("Semua");
		
//		SearchBar
		searchbarRute = new TextField();
		searchbarRute.setPromptText("Search Rute . . .");
		searchbarRute.setPrefWidth(200); 
		searchbarRute.setMinWidth(150);  
		searchbarRute.setMaxWidth(300);

		searchbarMaskapai = new TextField();
		searchbarMaskapai.setPromptText("Search Maskapai . . .");
		searchbarMaskapai.setPrefWidth(200);
		searchbarMaskapai.setMinWidth(150);
		searchbarMaskapai.setMaxWidth(300);
		
//		Scene
		vbhome = new VBox(50);
		hbsearch = new HBox();
		
		informasiAndBookingBox = new HBox(20); 

		
		
		informasiBox = new VBox(10); 
		

		vbs1 = new VBox(0);
		
		
//		menu	
		menuuser = new Menu("Menu");
		mbuser = new MenuBar();
			
		home = new MenuItem("Home");
		reservasi = new MenuItem("Reservasi");
		logout = new MenuItem("Log Out");
		
		menuuser.getItems().add(home);
		menuuser.getItems().add(reservasi);
		menuuser.getItems().add(logout);
		mbuser.getMenus().add(menuuser);
		
		
//		table
		tablePenerbangan = new TableView<>();

		
	    TableColumn<Penerbangan, String> rutecol = new TableColumn<>("Rute");
	    rutecol.setMinWidth(150);
	    rutecol.setCellValueFactory(new PropertyValueFactory<>("rute"));
	    
	    TableColumn<Penerbangan, Date> datecol = new TableColumn<>("Tanggal Keberangkatan");
	    datecol.setMinWidth(150);
	    datecol.setCellValueFactory(new PropertyValueFactory<>("tanggalBerangkat"));
	    
	    TableColumn<Penerbangan, String> maskapaicol = new TableColumn<>("Maskapai Penerbangan");
	    maskapaicol.setMinWidth(150);
	    maskapaicol.setCellValueFactory(new PropertyValueFactory<>("maskapaiPenerbangan"));
	   
	    TableColumn<Penerbangan, String> kelascol = new TableColumn<>("Kelas Penerbangan");
	    kelascol.setMinWidth(150);
	    kelascol.setCellValueFactory(new PropertyValueFactory<>("kelasPenerbangan"));
	    
	    
	    tablePenerbangan.getColumns().addAll(rutecol, datecol,maskapaicol,kelascol); 
	    tablePenerbangan.setMaxWidth(600);
	    tablePenerbangan.setItems(dataPenerbangan);
		
		
		FilteredList<Penerbangan> filteredData = new FilteredList<>(dataPenerbangan, b -> true);
		
		searchbarRute.textProperty().addListener((observable, oldValue, newValue) -> {
		    filteredData.setPredicate(penerbangan -> {
		        if (newValue == null || newValue.isEmpty()) {
		            return true;
		        }
		        String lowerCaseFilter = newValue.toLowerCase();
		        return penerbangan.getRute().toLowerCase().contains(lowerCaseFilter);
		    });
		});

	
		searchbarMaskapai.textProperty().addListener((observable, oldValue, newValue) -> {
		    filteredData.setPredicate(penerbangan -> {
		        if (newValue == null || newValue.isEmpty()) {
		            return true;
		        }
		        String lowerCaseFilter = newValue.toLowerCase();
		        return penerbangan.getMaskapaiPenerbangan().toLowerCase().contains(lowerCaseFilter);
		    });
		});
		
		comboBoxKelasPenerbangan.valueProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(penerbangan -> {
				if (newValue.equals("Semua")) {
					return true;
				} else {
					return penerbangan.getKelasPenerbangan().equals(newValue);
				}
			});
		});
		 
		 SortedList<Penerbangan> sortedData = new SortedList<>(filteredData);
	        sortedData.comparatorProperty().bind(tablePenerbangan.comparatorProperty());
	        tablePenerbangan.setItems(sortedData);
	        
	        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
	            filteredData.setPredicate(penerbangan -> {
	           
	                if (newValue == null) {
	                    return true;
	                }

	                return penerbangan.getTanggalBerangkat().toLocalDate().equals(newValue);
	            });
	        });
	        
	        tablePenerbangan.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	            if (newValue != null) {
	   
	                String detailText = String.format("Durasi Penerbangan: %s\nBandara Keberangkatan: %s\nBandara Kedatangan: %s\nJenis Pesawat: %s",
	                        newValue.getDurasiPenerbangan(),
	                        newValue.getBandaraKeberangkatan(),
	                        newValue.getBandaraKedatangan(),
	                        newValue.getJenisPesawat());
	                detailInfoLbl.setText(detailText);
	            } else {
	                detailInfoLbl.setText(""); 
	            }
	        });
	        
	        reservasi.setOnAction(e-> {
	        	openReservation(userId);
	        });
	        
	        logout.setOnAction(e-> {
	        	openLogin();
	        });
	        
	    vbs1.getChildren().addAll(searchlbl,searchbarRute);
	    hbsearch.getChildren().addAll(vbs1, searchbarMaskapai,datePicker,comboBoxKelasPenerbangan);
	    informasiBox.getChildren().addAll(informasiPenerbanganLbl, detailInfoLbl);
	    informasiAndBookingBox.getChildren().addAll(informasiBox, bookingButton);
		vbhome.getChildren().addAll(mbuser,searchlbl, hbsearch,tablePenerbangan,informasiAndBookingBox);	
	}
	
	 public void openLogin() {
	    	Stage currentStage =(Stage) vbhome.getScene().getWindow();
	    	currentStage.close();
	    	
	    	Login loginpage = new Login();
	        loginpage.initializeComponents();
	        Scene scene = new Scene(loginpage.getRoot(), 800, 800);
	    	
	        Stage stage = new Stage();
	        stage.setTitle("Travel");
	        stage.setScene(scene);
	        stage.show();
	    }
	
	public void openReservation(String userId) {
		Stage currentStage = (Stage) vbhome.getScene().getWindow();
	    currentStage.close();
	    

	    ReservationPage reservationPage = new ReservationPage();
	    reservationPage.setUserId(userId);
	    reservationPage.initialize();
	    Scene scene = new Scene(reservationPage.getRoot(), 800, 800);
	    Stage stage = new Stage();
	    stage.setScene(scene);
	    stage.setTitle("Home Page");
	    stage.show();
	}
	
	public void getData() {
    	dataPenerbangan.clear();
    	Connect connect = Connect.getInstance();
     	String query = "SELECT * from penerbangan ";
    	ResultSet resultSet = connect.execQuery(query);
    	
        try {
            

            while (resultSet.next()) {
            	
            	
                String penerbanganId = resultSet.getString("penerbanganId");
                String rute = resultSet.getString("rute");
                Date tanggalBerangkat = resultSet.getDate("tanggalBerangkat");
                String maskapaiPenerbangan = resultSet.getString("maskapaiPenerbangan");
                String kelasPenerbangan = resultSet.getString("kelasPenerbangan");
                String durasiPenerbangan = resultSet.getString("durasiPenerbangan");
                String bandaraKeberangkatan = resultSet.getString("bandaraKeberangkatan");
                String bandaraKedatangan = resultSet.getString("bandaraKedatangan");
                String jenisPesawat = resultSet.getString("jenisPesawat");
                int price = resultSet.getInt("price");
                

                dataPenerbangan.add(new Penerbangan(penerbanganId, rute, tanggalBerangkat,maskapaiPenerbangan,kelasPenerbangan,durasiPenerbangan,bandaraKeberangkatan,bandaraKedatangan,jenisPesawat,price));
            }

            
        } catch (SQLException e) {
            e.printStackTrace();
    
        }
        
    	
    }
	
	public void showBookingPage() {
		
		
		Penerbangan selectedPenerbangan = tablePenerbangan.getSelectionModel().getSelectedItem();
		 if (selectedPenerbangan == null) {
		 
		        Alert alert = new Alert(AlertType.ERROR, "Tidak ada penerbangan yang dipilih.", ButtonType.OK);
		        alert.setTitle("Error");
		        alert.setHeaderText(null);
		        alert.showAndWait();
		        return; 
		    }
		
	    Stage currentStage = (Stage) vbhome.getScene().getWindow();
	    currentStage.close();

	    
	    BookingPage bookingPage = new BookingPage();
	    bookingPage.initialize(selectedPenerbangan, userId);
	    Scene bookingScene = new Scene(bookingPage.getRoot(), 800, 800);
	    Stage bookingStage = new Stage();
	   
	    bookingStage.setScene(bookingScene);
	    bookingStage.setTitle("Booking Penerbangan");
	    bookingStage.show();
	}

	
	public VBox getRoot() {
		return vbhome;
	}

}
