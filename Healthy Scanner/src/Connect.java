import java.sql.*;

public class Connect {
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    private final String DATABASE = "HealthyScanner";
    private final String HOST = "localhost:3306";
    private final String CONNECTION =
            String.format("jdbc:mysql://%s/%s", HOST, DATABASE);
    
    public ResultSet rs;
    
    

    private Connection con;
    private PreparedStatement preparedStatement;
    private static Connect instance;

    private Connect() {
        try {
            con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    public static Connect getInstance() {
        if (instance == null) {
            return new Connect();
        }
        return instance;
    }

    public ResultSet execQuery(String query) {
        try {
            preparedStatement = con.prepareStatement(query);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing query: " + e.getMessage());
        }
        return null;
    }

    public void execUpdate(String query) {
        try {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing update: " + e.getMessage());
        }
    }
    
    public void insertUserData(String userId, String username, String password, String email) {
        String insertQuery = "INSERT INTO users (userId, username, password, email) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, email);
     

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing update: " + e.getMessage());
        }
    }
    
    public void insertFisikData(String fisikid, String userId, int jumlahlangkah, int jaraktempuh,int durasilatihan) {
    	 String insertQuery = "INSERT INTO fisik (fisikid, userid, jumlahlangkah, jaraktempuh, durasilatihan) VALUES (?, ?, ?, ?, ?)";
         try {
             PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
             preparedStatement.setString(1, fisikid);
             preparedStatement.setString(2, userId);
             preparedStatement.setInt(3, jumlahlangkah);
             preparedStatement.setInt(4, jaraktempuh);
             preparedStatement.setInt(5, durasilatihan);
      

             preparedStatement.executeUpdate();
         } catch (SQLException e) {
             e.printStackTrace();
             System.err.println("Error executing update: " + e.getMessage());
         }
    }
    
    public void insertTidurData(String pemantauId, String userId, String hari, int jumlahjamtidur,String kualitas) {
   	 String insertQuery = "INSERT INTO tidur (pemantauId, userid, hari, jumlahjamtidur, kualitas) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(insertQuery);
            preparedStatement.setString(1, pemantauId);
            preparedStatement.setString(2, userId);
            preparedStatement.setString(3, hari);
            preparedStatement.setInt(4, jumlahjamtidur);
            preparedStatement.setString(5, kualitas);
     

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error executing update: " + e.getMessage());
        }
   }
    
    
    public String getUserIdFromUsername(String username) {
        String query = "SELECT userId FROM users WHERE username = '" + username + "'";
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
    
}