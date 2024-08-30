import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HalamanTidur {

	VBox vbtidur;
	
    private String userId;
    private Connect connect = Connect.getInstance();
    private TableView<Tidur> tableTidur = new TableView<>();
    HBox hboxhari;
    HBox hboxjam ;  
    HBox hbbtnt;
    
    Menu menutidur;
    MenuBar mbt;
    MenuItem mt1,mt2;
    TextField tfhari, tfjam;
    Label lblhari, lbljam;
    
    Button btnaddt,btndeletet;
    ObservableList<Tidur> datatidur = FXCollections.observableArrayList();
    
    private String tempId = null;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void inittable() {
   
    	
    	   menutidur = new Menu("Menu"); 
    	  

           mt1 = new MenuItem("Pelacakan Aktivitas Fisik"); 
           mt2 = new MenuItem("Log Out"); 
  

          menutidur.getItems().add(mt1); 
          menutidur.getItems().add(mt2); 
    

           mbt = new MenuBar(); 
    
       
          mbt.getMenus().add(menutidur); 
      
        tfhari = new TextField();
        lblhari = new Label("Hari");
        hboxhari = new HBox(10);
        hboxhari.getChildren().addAll(lblhari, tfhari);
        
        
        tfjam = new TextField();
        lbljam = new Label("Jumlah Jam Tidur");
        hboxjam = new HBox(10);
        hboxjam.getChildren().addAll(lbljam, tfjam);
        
       
        
        btnaddt = new Button("Add");
        btndeletet = new Button("Delete");
        
        hbbtnt = new HBox(5);
        
       hbbtnt.getChildren().addAll(btnaddt,btndeletet);
        
        btnaddt.setOnAction(e-> {
        	addData();
        });
        
        btndeletet.setOnAction(e -> {
        	deleteData();
        	
        });
        
        mt1.setOnAction(e-> {
        	openHalamanFisik(userId);
        });
        
        mt2.setOnAction(e-> {
        	openLoginPage();
        });
        
        setupTableTidur(datatidur);
    }
    
    private void openLoginPage() {
		// TODO Auto-generated method stub
    	  Stage currentStage = (Stage) vbtidur.getScene().getWindow(); 
     	 currentStage.close(); 
    	 Login loginpage = new Login();
    	 loginpage.initialize();
         Scene scene = new Scene(loginpage.getRoot(), 900, 600);
         Stage stage = new Stage();
         stage.setScene(scene);
         stage.setTitle("HealthyScanner");
         stage.show();
	}
    
    public void getData() {
    	datatidur.clear();
    	
     	String query = "SELECT pemantauId, userId, hari, jumlahjamtidur, kualitas FROM tidur WHERE userid = '" + userId + "'";
    	ResultSet resultSet = connect.execQuery(query);
    	
        try {
            

            while (resultSet.next()) {
                String pemantauId = resultSet.getString("pemantauId");
                String userId = resultSet.getString("userId");
                String hari = resultSet.getString("hari");
                Integer jumlahjamtidur = resultSet.getInt("jumlahjamtidur");
                String kualitas = resultSet.getString("kualitas");

                datatidur.add(new Tidur(pemantauId, userId, hari, jumlahjamtidur, kualitas));
            }

            
        } catch (SQLException e) {
            e.printStackTrace();
    
        }
        
    	
    }
    
    public void refreshTable() {
    	getData();
    	ObservableList<Tidur> tdrObs = FXCollections.observableArrayList(datatidur);
    	tableTidur.setItems(tdrObs);
    	
    	refreshField();
    }

    public void refreshField() {
    	tfhari.setText("");
    	tfjam.setText("");
    	
    	tempId = null;
    }
    
    private void setupTableTidur(ObservableList<Tidur> datatidur) {
        TableColumn<Tidur, String> hariCol = new TableColumn<>("Hari");
        hariCol.setCellValueFactory(new PropertyValueFactory<>("hari"));
        hariCol.setPrefWidth(300);

        TableColumn<Tidur, Integer> jamCol = new TableColumn<>("Jumlah Jam Tidur");
        jamCol.setCellValueFactory(new PropertyValueFactory<>("jumlahjamtidur"));
        jamCol.setPrefWidth(300);

        TableColumn<Tidur, String> kualitasCol = new TableColumn<>("Kualitas Tidur");
        kualitasCol.setCellValueFactory(new PropertyValueFactory<>("kualitas"));
        kualitasCol.setPrefWidth(300);

        tableTidur.getColumns().addAll(hariCol, jamCol, kualitasCol);
        tableTidur.setItems(datatidur);
        
        tableTidur.setOnMouseClicked(tableMouseEvent());
    }
    
    private String generateTidurID() {
	    Connect connect = Connect.getInstance();
	    String query = "SELECT pemantauId FROM tidur ORDER BY pemantauId DESC LIMIT 1";
	    try {
	        ResultSet resultSet = connect.execQuery(query);
	        if (resultSet.next()) {
	            String lastTidurID = resultSet.getString("pemantauId");
	            int lastNumber = Integer.parseInt(lastTidurID.substring(2));
	            int nextNumber = lastNumber + 1;
	            String formattedNumber = String.format("%03d", nextNumber);
	            return "TI" + formattedNumber;
	        } else {
	            return "TI001"; 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
   
    public void addData() {
    	Connect connect = Connect.getInstance();
    	 String hari = tfhari.getText();
    	 Integer jumlahjamtidur = Integer.valueOf(tfjam.getText());
    	 String pemantauId = generateTidurID();
    	 String kualitas = generateKualitas(jumlahjamtidur);
		Tidur newTidur = new Tidur(pemantauId, userId, hari, jumlahjamtidur, kualitas);
		connect.insertTidurData(pemantauId, userId, hari, jumlahjamtidur, kualitas);
		refreshTable();
    }
    
    public String generateKualitas(int jumlahjamtidur) {
        if (jumlahjamtidur >= 8) {
            return "Bagus";
        } else if (jumlahjamtidur >= 6 && jumlahjamtidur < 8) {
            return "Cukup";
        } else {
            return "Kurang";
        }
    }
   
    public void deleteData() {
    	Connect connect = Connect.getInstance();
   	 String hari = tfhari.getText();
   	 Integer jumlahjamtidur = Integer.valueOf(tfjam.getText());

   	 String query = String.format("DELETE FROM tidur WHERE pemantauId = '%s'", tempId);
   	 connect.execUpdate(query);
   	 refreshTable();
   	 
    }
    
    public EventHandler<MouseEvent> tableMouseEvent(){
    	return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				TableSelectionModel<Tidur> tableSelectionModel = tableTidur.getSelectionModel();
				
				tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
				
				Tidur haltidur = tableSelectionModel.getSelectedItem();
				
				if(haltidur != null) {
				tfhari.setText(haltidur.getHari());
				tfjam.setText(String.valueOf(haltidur.getJumlahjamtidur()));

				
				tempId = haltidur.getPemantauId();
				}
			}
    		
    	};
    	
    }
    
    private void openHalamanFisik(String userId) {
		// TODO Auto-generated method stub
    	Stage currentStage = (Stage) vbtidur.getScene().getWindow();
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
    
    public VBox getRoot() {
    	 
    	vbtidur = new VBox(5);
        vbtidur.getChildren().addAll(mbt,tableTidur,hboxhari,hboxjam,hbbtnt);
   
		return vbtidur; 
    }
    
}
