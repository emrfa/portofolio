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

public class HalamanFisik {

	VBox vbfisik;
	
    private String userId;
    private Connect connect = Connect.getInstance();
    private TableView<Fisik> tableView = new TableView<>();
    HBox hboxJumlahLangkah;
    HBox hboxJarakTempuh ;
    HBox hboxDurasiLatihan;  
    HBox hbbtn;
    
    Menu m;
    MenuBar mb;
    MenuItem m1,m2;
    TextField tfJumlahLangkah,tfjaraktempuh,tfdurasilatihan;
    Label lblJumlahLangkah,lbljaraktempuh,lbldurasilatihan;
    
    Button btnadd,btndelete;
    ObservableList<Fisik> datafisik = FXCollections.observableArrayList();
    
    private String tempId = null;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void inittable() {
   
    	
    	   m = new Menu("Menu"); 
    	  

           m1 = new MenuItem("Pemantauan Pola Tidur"); 
           m2 = new MenuItem("Log Out"); 
  

          m.getItems().add(m1); 
          m.getItems().add(m2); 
    

           mb = new MenuBar(); 
    
       
          mb.getMenus().add(m); 
      
        tfJumlahLangkah = new TextField();
        lblJumlahLangkah = new Label("Jumlah Langkah");
        hboxJumlahLangkah = new HBox(10);
        hboxJumlahLangkah.getChildren().addAll(lblJumlahLangkah, tfJumlahLangkah);
        
        
        tfjaraktempuh = new TextField();
        lbljaraktempuh = new Label("Jarak Tempuh (Km)");
        hboxJarakTempuh = new HBox(10);
        hboxJarakTempuh.getChildren().addAll(lbljaraktempuh, tfjaraktempuh);
        
        tfdurasilatihan = new TextField();
        lbldurasilatihan = new Label("Durasi Latihan (Menit)");
        hboxDurasiLatihan = new HBox(10);
        hboxDurasiLatihan.getChildren().addAll(lbldurasilatihan, tfdurasilatihan);
        
        btnadd = new Button("add");
        btndelete = new Button("Delete");
        
        hbbtn = new HBox(5);
        
       hbbtn.getChildren().addAll(btnadd,btndelete);
        
        btnadd.setOnAction(e-> {
        	addData();
        });
        
        btndelete.setOnAction(e -> {
        	deleteData();
        	
        });
        
        m1.setOnAction(e-> {
        	openHalamanTidur(userId);
        });
        
        m2.setOnAction(e-> {
        	openLoginPage();
        });
        
        setupTableView(datafisik);
    }
    
    private void openLoginPage() {
		// TODO Auto-generated method stub
    	  Stage currentStage = (Stage) vbfisik.getScene().getWindow(); 
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
    	datafisik.clear();
    	
     	String query = "SELECT fisikId, userId, jumlahlangkah, jaraktempuh, durasilatihan FROM fisik WHERE userid = '" + userId + "'";
    	ResultSet resultSet = connect.execQuery(query);
    	
        try {
            

            while (resultSet.next()) {
                String fisikId = resultSet.getString("fisikId");
                String userId = resultSet.getString("userId");
                int jumlahLangkah = resultSet.getInt("jumlahlangkah");
                int jarakTempuh = resultSet.getInt("jaraktempuh");
                int durasiLatihan = resultSet.getInt("durasilatihan");

                datafisik.add(new Fisik(fisikId, userId, jumlahLangkah, jarakTempuh, durasiLatihan));
            }

            
        } catch (SQLException e) {
            e.printStackTrace();
    
        }
        
    	
    }
    
    public void refreshTable() {
    	getData();
    	ObservableList<Fisik> fisObs = FXCollections.observableArrayList(datafisik);
    	tableView.setItems(fisObs);
    	
    	refreshField();
    }

    public void refreshField() {
    	tfdurasilatihan.setText("");
    	tfjaraktempuh.setText("");
    	tfJumlahLangkah.setText("");
    	
    	tempId = null;
    }
    
    private void setupTableView(ObservableList<Fisik> datafisik) {
        TableColumn<Fisik, Integer> jumlahLangkahCol = new TableColumn<>("Jumlah Langkah");
        jumlahLangkahCol.setCellValueFactory(new PropertyValueFactory<>("jumlahLangkah"));
        jumlahLangkahCol.setPrefWidth(300);

        TableColumn<Fisik, Integer> jarakTempuhCol = new TableColumn<>("Jarak Tempuh");
        jarakTempuhCol.setCellValueFactory(new PropertyValueFactory<>("jarakTempuh"));
        jarakTempuhCol.setPrefWidth(300);

        TableColumn<Fisik, Integer> durasiLatihanCol = new TableColumn<>("Durasi Latihan");
        durasiLatihanCol.setCellValueFactory(new PropertyValueFactory<>("durasiLatihan"));
        durasiLatihanCol.setPrefWidth(300);

        tableView.getColumns().addAll(jumlahLangkahCol, jarakTempuhCol, durasiLatihanCol);
        tableView.setItems(datafisik);
        
        tableView.setOnMouseClicked(tableMouseEvent());
    }
    
    private String generateFisikID() {
	    Connect connect = Connect.getInstance();
	    String query = "SELECT fisikid FROM fisik ORDER BY fisikid DESC LIMIT 1";
	    try {
	        ResultSet resultSet = connect.execQuery(query);
	        if (resultSet.next()) {
	            String lastFisikID = resultSet.getString("fisikid");
	            int lastNumber = Integer.parseInt(lastFisikID.substring(2));
	            int nextNumber = lastNumber + 1;
	            String formattedNumber = String.format("%03d", nextNumber);
	            return "FI" + formattedNumber;
	        } else {
	            return "FI001"; 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
    
    public void addData() {
    	Connect connect = Connect.getInstance();
    	 Integer jumlahLangkah = Integer.valueOf(tfJumlahLangkah.getText());
    	 Integer jarakTempuh = Integer.valueOf(tfjaraktempuh.getText());
    	 Integer durasiLatihan = Integer.valueOf(tfdurasilatihan.getText());

    	 String fisikId = generateFisikID();
    	 
		Fisik newFisik = new Fisik(fisikId, userId, jumlahLangkah, jarakTempuh, durasiLatihan);
		connect.insertFisikData(fisikId, userId, jumlahLangkah, jarakTempuh, durasiLatihan);
		refreshTable();
    }
    
    public void deleteData() {
    	Connect connect = Connect.getInstance();
   	 Integer jumlahLangkah = Integer.valueOf(tfJumlahLangkah.getText());
   	 Integer jarakTempuh = Integer.valueOf(tfjaraktempuh.getText());
   	 Integer durasiLatihan = Integer.valueOf(tfdurasilatihan.getText());

   	 String query = String.format("DELETE FROM fisik WHERE fisikid = '%s'", tempId);
   	 connect.execUpdate(query);
   	 refreshTable();
   	 
    }
    
    public EventHandler<MouseEvent> tableMouseEvent(){
    	return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				TableSelectionModel<Fisik> tableSelectionModel = tableView.getSelectionModel();
				
				tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
				
				Fisik halfisik = tableSelectionModel.getSelectedItem();
				
				if(halfisik != null) {
				tfdurasilatihan.setText(String.valueOf(halfisik.getDurasiLatihan()));
				tfjaraktempuh.setText(String.valueOf(halfisik.getJarakTempuh()));
				tfJumlahLangkah.setText(String.valueOf(halfisik.getJumlahLangkah()));
				
				tempId = halfisik.getFisikId();
				}
			}
    		
    	};
    	
    }
    
    
    private void openHalamanTidur(String userId) {
  		// TODO Auto-generated method stub
      	Stage currentStage = (Stage) vbfisik.getScene().getWindow();
          currentStage.close();

          HalamanTidur tidur = new HalamanTidur();
          tidur.setUserId(userId); 
          tidur.getData();
          tidur.inittable();
          VBox tidurlayout = tidur.getRoot();
          Scene scene = new Scene(tidurlayout, 900, 600);

          Stage stage = new Stage();
          stage.setScene(scene);
          stage.setTitle("Halaman Pemantau Kualitas Tidur");
          stage.show();
           
  	}
    
    public VBox getRoot() {
    	 
    	vbfisik = new VBox(5);
        vbfisik.getChildren().addAll(mb,tableView,hboxJumlahLangkah,hboxJarakTempuh,hboxDurasiLatihan,hbbtn);
   
		return vbfisik; 
    }
    
}
