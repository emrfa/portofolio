import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connector {
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String DATABASE = "Movie";
    private final String HOST = "localhost:3306";
    private final String CONNECTION =
            String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
    
    public ResultSet rs;
    
    

    Connection connection;
    
    private PreparedStatement preparedStatement;
    private static Connector instance;

    private Connector() {
        try {
            connection = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    public static Connector getInstance() {
        if (instance == null) {
            return new Connector();
        }
        return instance;
    }

    public ResultSet execQuery(String query) {
        try {
            preparedStatement = connection.prepareStatement(query);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing query: " + e.getMessage());
        }
        return null;
    }

    public void execUpdate(String query) {
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing update: " + e.getMessage());
        }
    }
    
    public void addUser(String userId, String username, String password) {
        String insertQuery = "INSERT INTO user (userId, username, password) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing update: " + e.getMessage());
        }
    }
    
    public String getUserId(String username) {
        String query = "SELECT userId FROM user WHERE username = '" + username + "'";
        try {
            ResultSet resultSet = execQuery(query);
            if (resultSet.next()) {
                return resultSet.getString("userId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    


	public void addMovie(String movieId, String title, String genre) {
		String insertQuery = "INSERT INTO movie (movieId, title, genre) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, movieId);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, genre);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing update: " + e.getMessage());
        }
		
		// TODO Auto-generated method stub
		
	}
    
}