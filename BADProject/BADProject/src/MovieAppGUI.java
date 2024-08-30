import java.sql.ResultSet;
import java.sql.SQLException;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;

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

public class MovieAppGUI {

	VBox rootPane;
	
    private String userId;
    private Connector connect = Connector.getInstance();
    private TableView<Movie> tableView = new TableView<>();
    
    Label lbltitle;
    Label lblgenre;
    
    TextField titletf;
    TextField genretf;

    HBox hbtitle, hbgenre;
    
    
    Menu menu;
    MenuBar menubar;
    MenuItem menuitem;
    
    Button addbtn,deletebtn;
    ObservableList<Movie> datamovie = FXCollections.observableArrayList();
    
    private String tempId = null;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void initialize() {
   
    	
    		lbltitle = new Label("Title");
    		titletf = new TextField();
    		
    		lblgenre = new Label("Genre");
    		genretf = new TextField();
    		
    		hbtitle = new HBox(5);
    		hbgenre = new HBox(5);
    		
    		hbtitle.getChildren().addAll(lbltitle, titletf);
    		hbgenre.getChildren().addAll(lblgenre, genretf );
    		
    	   menu = new Menu("Menu"); 
    	  

           menuitem = new MenuItem("logout"); 
    

          menu.getItems().add(menuitem); 
     
    

           menubar = new MenuBar(); 
    
       
          menubar.getMenus().add(menu); 
  
        
        addbtn = new Button("Add Movie");
        deletebtn = new Button("Delete Movie");
        

        tableMovie(datamovie);
        
        addbtn.setOnAction(e-> {
        	addMovie();
        });
        
        deletebtn.setOnAction(e -> {
        	deleteMovie();
        	
        });
        
        menuitem.setOnAction(e-> {
        	openLogin();
        	
        });
    }
    
   
    
    public void getData() {
    	datamovie.clear();
    	
     	String query = "SELECT movieId,title,genre FROM movie" ;
    	ResultSet resultSet = connect.execQuery(query);
    	
        try {
            

            while (resultSet.next()) {
       
            
                String title = resultSet.getString("title");
                String genre = resultSet.getString("genre");

                String movieId = resultSet.getString("movieId");
				datamovie.add(new Movie ( title, genre, movieId));
            }

            
        } catch (SQLException e) {
            e.printStackTrace();
    
        }
        
    	
    }
    
    public void refreshTable() {
    	getData();
    	ObservableList<Movie> mov = FXCollections.observableArrayList(datamovie);
    	tableView.setItems(mov);
    	
    	refreshField();
    }
    
    private void openLogin() {
        Stage currentStage = (Stage) rootPane.getScene().getWindow();
        currentStage.close();
        Login loginPage = new Login();
        loginPage.initialize();
        Scene scene = new Scene(loginPage.getRoot(), 900, 500);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("MovieSubscription");
        stage.show();
    }
    

    public void refreshField() {
    	genretf.setText("");
    	titletf.setText("");
    	
    	tempId = null;
    }
    
    private void tableMovie(ObservableList<Movie> dataMovie) {
        TableColumn<Movie, String> titlecolumn = new TableColumn<>("Title");
        titlecolumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titlecolumn.setPrefWidth(450);

        TableColumn<Movie, String> genrecolumn = new TableColumn<>("Genre");
        genrecolumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        genrecolumn.setPrefWidth(450);

       

        tableView.getColumns().addAll(titlecolumn,genrecolumn);
        tableView.setItems(datamovie);
        
        tableView.setOnMouseClicked(tableMouseEvent());
    }
    
    private String generateMovieID() {
	    Connector connect = Connector.getInstance();
	    String query = "SELECT movieId FROM movie ORDER BY movieId DESC LIMIT 1";
	    try {
	        ResultSet resultSet = connect.execQuery(query);
	        if (resultSet.next()) {
	            String lastMovieID = resultSet.getString("movieId");
	            int lastNumber = Integer.parseInt(lastMovieID.substring(2));
	            int nextNumber = lastNumber + 1;
	            String formattedNumber = String.format("%03d", nextNumber);
	            return "MO" + formattedNumber;
	        } else {
	            return "MO001"; 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
    
    public void addMovie() {
    	Connector connect = Connector.getInstance();
   
    	 
    	String movieId = generateMovieID();
       	 String title = titletf.getText();
       	 String genre = genretf.getText();

		
		Movie movieNew = new Movie(movieId, title, genre);
		connect.addMovie(movieId, title, genre);
		refreshTable();
    }
    
    public void deleteMovie() {
    	Connector connect = Connector.getInstance();
  
   	 
   	 String title = titletf.getText();
   	 String genre = genretf.getText();

   	 String query = String.format("DELETE FROM movie WHERE movieId = '%s'", tempId);
   	 connect.execUpdate(query);
   	 refreshTable();
   	 
    }
    
    public EventHandler<MouseEvent> tableMouseEvent(){
    	return new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				TableSelectionModel<Movie> tableSelectionModel = tableView.getSelectionModel();
				
				tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);
				
				Movie movieselect = tableSelectionModel.getSelectedItem();
				
				if(movieselect != null) {
				genretf.setText(movieselect.getGenre());
				titletf.setText(movieselect.getTitle());
	
				
				tempId = movieselect.getMovieId();
				}
			}
    		
    	};
    	
    }
    
    

    
    public VBox getRootPane() {
    	 
    	rootPane = new VBox(5);
       rootPane.getChildren().addAll(menubar,tableView,hbtitle, hbgenre, addbtn,deletebtn);
   
		return rootPane; 
    }
    
}
