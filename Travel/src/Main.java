
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		   Login loginpage = new Login();
	        loginpage.initializeComponents();
	        Scene scene = new Scene(loginpage.getRoot(), 800, 800);
	    	
	        primaryStage.setTitle("Travel");
	        primaryStage.setScene(scene);
	        primaryStage.show();
	}

}
